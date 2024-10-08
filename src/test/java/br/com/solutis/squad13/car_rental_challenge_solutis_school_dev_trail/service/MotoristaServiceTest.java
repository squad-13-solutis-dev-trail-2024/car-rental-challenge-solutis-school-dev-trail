package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl.MotoristaServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo.MASCULINO;
import static java.time.LocalDateTime.now;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ActiveProfiles("MotoristaServiceTest")
public class MotoristaServiceTest {

    private MotoristaService motoristaService;
    private MotoristaRepository motoristaRepository;
    private static Validator validator;

    @BeforeEach
    public void setup() {
        motoristaRepository = mock(MotoristaRepository.class);
        motoristaService = new MotoristaServiceImpl(motoristaRepository);

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Deve lançar ValidationException ao tentar cadastrar um motorista menor de idade")
    public void givenMenorDeIdade_whenCadastrarMotorista_thenThrowsValidationException() {
        DadosCadastroMotorista dados = new DadosCadastroMotorista(
                "Vinícius dos Santos Andrade",
                LocalDate.now().minusYears(17), // Menor de idade
                "447.841.608-76",
                "vinicius_andrade2010@hotmail.com",
                "07493612633",
                MASCULINO,
                now(),
                now()
        );

        // Validação dos dados para verificar se existe alguma violação
        Set<ConstraintViolation<DadosCadastroMotorista>> violations = validator.validate(dados);

        // Verifica que há violação da regra de validação (menor de idade)
        assertFalse(violations.isEmpty());

        // Certifica-se de que a violação específica de maioridade foi capturada
        boolean isMenorDeIdadeViolationPresent = violations.stream()
                .anyMatch(violation -> "Você não é maior de idade".equals(violation.getMessage()));

        assertTrue(isMenorDeIdadeViolationPresent);
    }

    @Test
    @DisplayName("Deve lançar ValidationException ao tentar cadastrar um motorista com CPF inválido")
    public void givenCpfInvalido_whenCadastrarMotorista_thenThrowsValidationException() {
        DadosCadastroMotorista dados = new DadosCadastroMotorista(
                "Vinícius dos Santos Andrade",
                LocalDate.now().minusYears(30), // Idade válida
                "123.456.789-00", // CPF inválido
                "vinicius_andrade2010hotmail.com",
                "07493612633",
                Sexo.MASCULINO,
                now(),
                now()
        );

        Set<ConstraintViolation<DadosCadastroMotorista>> violations = validator.validate(dados);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(violation -> violation.getMessage().contains("CPF inválido")));
    }

    @Test
    @DisplayName("Deve lançar ValidationException ao tentar cadastrar um motorista com CNH inválida")
    public void givenCnhInvalida_whenCadastrarMotorista_thenThrowsValidationException() {
        DadosCadastroMotorista dados = new DadosCadastroMotorista(
                "Vinícius dos Santos Andrade",
                LocalDate.now().minusYears(30), // Idade válida
                "447.841.608-76", // CPF válido
                "vinicius_andrade2010@hotmail.com",
                "00987864941", // CNH com 11 dígitos, mas inválida
                MASCULINO,
                now(),
                now()
        );

        Set<ConstraintViolation<DadosCadastroMotorista>> violations = validator.validate(dados);

        violations.forEach(v -> System.out.println("Mensagem de erro: " + v.getMessage()));

        assertFalse(violations.isEmpty(), "Espera-se que a validação falhe para uma CNH inválida");
        assertTrue(violations.stream()
                        .anyMatch(violation -> violation.getMessage().contains("Número da CNH inválido")),
                "A mensagem de erro esperada não foi encontrada.");
    }

    @Test
    @DisplayName("Deve salvar um motorista")
    public void givenMotorista_whenSave_thenMotoristaIsSaved() {
        //given - precondition or setup
        DadosCadastroMotorista dados = new DadosCadastroMotorista(
                "Vinícius dos Santos Andrade",
                LocalDate.of(2001, 12, 6),
                "447.841.608-76",
                "vinicius_andrade2010@hotmail.com",
                "07493612633",
                MASCULINO,
                now(),
                now()
        );

        //when - action or the behaviour that we arre testing
        Motorista motoristaSalvo = motoristaService.cadastrarMotorista(dados);

        //then - verify the output
        assertNotNull(motoristaSalvo);

        assertThat(motoristaSalvo.getNome()).isEqualTo("Vinícius dos Santos Andrade");
        assertThat(motoristaSalvo.getDataNascimento()).isEqualTo(LocalDate.of(2001, 12, 6));
        assertThat(motoristaSalvo.getCpf()).isEqualTo("447.841.608-76");
        assertThat(motoristaSalvo.getEmail()).isEqualTo("vinicius_andrade2010@hotmail.com");
        assertThat(motoristaSalvo.getNumeroCNH()).isEqualTo("07493612633");
        assertThat(motoristaSalvo.getSexo()).isEqualTo(MASCULINO);
        assertThat(motoristaSalvo.getAlugueis().isEmpty()).isTrue();

        // O que isso verifica?
        verify(motoristaRepository, times(1)).save(motoristaSalvo);
    }

    @Test
    @DisplayName("Deve retornar um motorista quando o ID é válido")
    public void givenValidId_whenBuscarPorId_thenReturnsMotorista() {
        // given - pré-condição ou setup
        Long id = 1L;
        Motorista motoristaEsperado = criarMotoristaEsperado();
        when(motoristaRepository.findById(id)).thenReturn(Optional.of(motoristaEsperado));

        // when - ação ou comportamento a ser testado
        Motorista motoristaEncontrado = motoristaService.buscarPorId(id);

        // then - verificação do resultado
        assertNotNull(motoristaEncontrado, "O motorista encontrado não deve ser nulo.");
        assertMotoristaEquals(motoristaEsperado, motoristaEncontrado);

        verify(motoristaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando o ID não é válido")
    public void givenInvalidId_whenBuscarPorId_thenThrowsEntityNotFoundException() {
        // given - pré-condição ou setup
        Long id = 1L;
        when(motoristaRepository.findById(id)).thenReturn(empty());

        // when - ação ou comportamento a ser testado
        EntityNotFoundException thrown = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> motoristaService.buscarPorId(id),
                "Esperava-se lançar EntityNotFoundException"
        );

        // then - verificação do resultado
        assertEquals("Motorista não encontrado", thrown.getMessage());
        verify(motoristaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve atualizar um motorista com sucesso")
    public void givenValidMotorista_whenAtualizarMotorista_thenMotoristaIsUpdated() {
        // given
        DadosAtualizacaoMotorista dadosAtualizacao = new DadosAtualizacaoMotorista(
                1L,
                "Vinícius dos Santos Andrade",
                LocalDate.of(2001, 12, 6),
                "447.841.608-76",
                "vinicius_andrade2010@hotmail.com",
                "123456789",
                MASCULINO,
                now()
        );

        Motorista motoristaExistente = criarMotoristaEsperado();
        motoristaExistente.ativar();

        when(motoristaRepository.findById(dadosAtualizacao.id())).thenReturn(Optional.of(motoristaExistente));
        when(motoristaRepository.save(motoristaExistente)).thenReturn(motoristaExistente);

        // when
        Motorista motoristaAtualizado = motoristaService.atualizarMotorista(dadosAtualizacao);

        // then
        assertMotoristaEquals(motoristaExistente, motoristaAtualizado);
        assertThat(motoristaAtualizado.getNome()).isEqualTo("Vinícius dos Santos Andrade");
        assertThat(motoristaAtualizado.getCpf()).isEqualTo("447.841.608-76");
        assertThat(motoristaAtualizado.getEmail()).isEqualTo("vinicius_andrade2010@hotmail.com");
        assertThat(motoristaAtualizado.getNumeroCNH()).isEqualTo("123456789");
        assertThat(motoristaAtualizado.getSexo()).isEqualTo(MASCULINO);
        verify(motoristaRepository, times(1)).save(motoristaExistente);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar atualizar um motorista inexistente")
    public void givenInvalidId_whenAtualizarMotorista_thenThrowsEntityNotFoundException() {
        // given
        DadosAtualizacaoMotorista dadosAtualizacao = new DadosAtualizacaoMotorista(
                99L,
                "Vinícius dos Santos Andrade",
                LocalDate.of(2001, 12, 6),
                "447.841.608-76",
                "vinicius_andrade2010@hotmail.com",
                "123456789",
                MASCULINO,
                now()
        );

        when(motoristaRepository.findById(dadosAtualizacao.id())).thenReturn(empty());

        // when
        EntityNotFoundException thrown = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> motoristaService.atualizarMotorista(dadosAtualizacao),
                "Esperava-se lançar EntityNotFoundException"
        );

        // then
        assertEquals("Motorista não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve deletar um motorista existente com sucesso")
    public void givenValidId_whenDeletarMotorista_thenMotoristaIsDeleted() {
        // given
        Long id = 1L;
        Motorista motorista = new Motorista(); // Crie um objeto Motorista para teste
        motorista.setId(id); // Defina o ID do motorista

        when(motoristaRepository.findById(id)).thenReturn(Optional.of(motorista)); // Retorne um Optional contendo o motorista
        doNothing().when(motoristaRepository).delete(motorista); // Use delete(motorista) para deletar o objeto

        // when
        motoristaService.deletarMotorista(id);

        // then
        verify(motoristaRepository, times(1)).delete(motorista); // Verifique se o método delete foi chamado com o objeto motorista
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar deletar um motorista inexistente")
    public void givenInvalidId_whenDeletarMotorista_thenThrowsEntityNotFoundException() {
        // given
        Long id = 99L;
        when(motoristaRepository.findById(id)).thenReturn(empty()); // Retorne um Optional vazio para simular motorista inexistente

        // when / then
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> motoristaService.deletarMotorista(id),
                "Esperava-se lançar EntityNotFoundException"
        );

        verify(motoristaRepository, never()).delete(any(Motorista.class)); // Verifique se delete nunca foi chamado
    }

    @Test
    @DisplayName("Deve listar motoristas com paginação")
    public void givenPageable_whenListar_thenReturnsPagedMotoristas() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Motorista motorista = criarMotoristaEsperado();
        motorista.ativar();
        List<Motorista> motoristas = List.of(motorista);
        Page<Motorista> pagedMotoristas = new PageImpl<>(motoristas, pageable, motoristas.size());

        when(motoristaRepository.findAllByAtivoTrue(pageable)).thenReturn(pagedMotoristas);

        // when
        Page<DadosListagemMotorista> resultado = motoristaService.listar(pageable);

        // then
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        verify(motoristaRepository, times(1)).findAllByAtivoTrue(pageable);
    }

    private Motorista criarMotoristaEsperado() {
        Motorista motorista = new Motorista();
        motorista.setId(1L);
        motorista.setNome("Vinícius dos Santos Andrade");
        motorista.setDataNascimento(LocalDate.of(2001, 12, 6));
        motorista.setCpf("447.841.608-76");
        motorista.setEmail("vinicius_andrade2010@hotmail.com");
        motorista.setNumeroCNH("07493612633");
        motorista.setSexo(MASCULINO);
        motorista.ativar();

        motorista.adicionarListaAlugueis(new ArrayList<>());
        return motorista;
    }

    private void assertMotoristaEquals(Motorista esperado, Motorista atual) {
        assertThat(atual.getId()).isEqualTo(esperado.getId());
        assertThat(atual.getNome()).isEqualTo(esperado.getNome());
        assertThat(atual.getDataNascimento()).isEqualTo(esperado.getDataNascimento());
        assertThat(atual.getCpf()).isEqualTo(esperado.getCpf());
        assertThat(atual.getEmail()).isEqualTo(esperado.getEmail());
        assertThat(atual.getNumeroCNH()).isEqualTo(esperado.getNumeroCNH());
        assertThat(atual.getSexo()).isEqualTo(esperado.getSexo());
        assertThat(atual.getAlugueis().isEmpty()).isTrue();
    }
}
