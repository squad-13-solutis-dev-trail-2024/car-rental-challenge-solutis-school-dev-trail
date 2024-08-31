package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Fabricante;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.DescricaoAcessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl.CarroServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria.SEDAN_COMPACTO;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.stream.LongStream.range;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("CarroServiceTest")
public class CarroServiceTest {

    private CarroService carroService;
    private CarroRepository carroRepository;

    @BeforeEach
    public void setup() {
        carroRepository = mock(CarroRepository.class);
        carroService = new CarroServiceImpl(carroRepository);
    }

    @Test
    @DisplayName("Deve cadastrar um carro com sucesso")
    public void givenDadosCadastroCarro_whenCadastrarCarro_thenCarroIsSaved() {
        // Given
        Fabricante fabricante = new Fabricante(1L, "Volkswagen", null);
        ModeloCarro modelo = new ModeloCarro(1L, "VW Golf", fabricante, SEDAN_COMPACTO, null);

        List<Acessorio> acessorios = range(0, DescricaoAcessorio.values().length)
                .mapToObj(i -> new Acessorio(i + 1, DescricaoAcessorio.values()[(int) i], null))
                .collect(Collectors.toList());

        DadosCadastroCarro dados = new DadosCadastroCarro(
                "Corolla",
                "ABC-1234",
                "9BWZZZ377VT004251",
                "Preto",
                BigDecimal.valueOf(200.00),
                acessorios,
                modelo,
                now(),
                now()
        );

        Carro carroEsperado = new Carro(dados);

        when(carroRepository.existsByPlaca(dados.placa())).thenReturn(false);
        when(carroRepository.existsByChassi(dados.chassi())).thenReturn(false);

        // When
        Carro carroSalvo = carroService.cadastrarCarro(dados);

        // Then
        assertNotNull(carroSalvo);

        ArgumentCaptor<Carro> carroCaptor = ArgumentCaptor.forClass(Carro.class);
        verify(carroRepository, times(1)).save(carroCaptor.capture());

        Carro carroSalvoNoRepositorio = carroCaptor.getValue();

        assertAll(
                () -> assertEquals(carroEsperado.getPlaca(), carroSalvoNoRepositorio.getPlaca()),
                () -> assertEquals(carroEsperado.getChassi(), carroSalvoNoRepositorio.getChassi()),
                () -> assertEquals(carroEsperado.getCor(), carroSalvoNoRepositorio.getCor()),
                () -> assertEquals(carroEsperado.getValorDiaria(), carroSalvoNoRepositorio.getValorDiaria()),
                () -> assertEquals(carroEsperado.getModelo(), carroSalvoNoRepositorio.getModelo()),
                () -> assertEquals(carroEsperado.getAcessorios(), carroSalvoNoRepositorio.getAcessorios())
        );
    }

    @Test
    @DisplayName("Deve lançar EntityExistsException ao tentar cadastrar um carro com placa duplicada")
    public void givenInvalidId_whenBuscarPorId_thenThrowsEntityNotFoundException() {
        // given
        Long id = 1L;
        when(carroRepository.findById(id)).thenReturn(empty());

        // when
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> carroService.buscarPorId(id),
                "Esperava-se lançar EntityNotFoundException"
        );

        // then
        assertEquals("Carro não encontrado", thrown.getMessage());
        verify(carroRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve atualizar um carro com sucesso")
    public void givenValidDadosAtualizarCarro_whenAtualizarCarro_thenCarroIsUpdated() {

        // Given
        Fabricante fabricante = new Fabricante(1L, "Volkswagen", null);
        ModeloCarro modelo = new ModeloCarro(1L, "VW Golf", fabricante, SEDAN_COMPACTO, null);

        List<Acessorio> acessorios = new ArrayList<>(range(0, DescricaoAcessorio.values().length)
                .mapToObj(i -> new Acessorio(i + 1, DescricaoAcessorio.values()[(int) i], null))
                .toList());
        // given
        DadosAtualizarCarro dadosAtualizar = new DadosAtualizarCarro(
                1L,
                "DEF-5678",
                "9BWZZZ377VT004252",
                "VW Jetta",
                "Branco",
                BigDecimal.valueOf(200.00),
                acessorios,
                modelo
        );

        Carro carroExistente = new Carro(
                1L,
                "ABC-1234",
                "9BWZZZ377VT004251",
                "VW Golf",
                "Preto",
                true,
                BigDecimal.valueOf(200.00),
                acessorios,
                null,
                null,
                null,
                null
        );
        when(carroRepository.findById(dadosAtualizar.id())).thenReturn(Optional.of(carroExistente));
        when(carroRepository.existsByPlaca(dadosAtualizar.placa())).thenReturn(false);
        when(carroRepository.existsByChassi(dadosAtualizar.chassi())).thenReturn(false);
        when(carroRepository.save(carroExistente)).thenReturn(carroExistente);

        // when
        Carro carroAtualizado = carroService.atualizarCarro(dadosAtualizar);

        // then
        assertNotNull(carroAtualizado);
        assertEquals(dadosAtualizar.placa(), carroAtualizado.getPlaca());
        assertEquals(dadosAtualizar.chassi(), carroAtualizado.getChassi());
        verify(carroRepository, times(1)).save(carroExistente);
    }

    @Test
    @DisplayName("Deve bloquear um carro existente com sucesso")
    public void givenValidId_whenBloquearCarro_thenCarroAluguelIsBlocked() {
        // Given
        Long id = 1L;
        Carro carro = new Carro(); // Crie um objeto Carro para o teste
        carro.setId(id);

        when(carroRepository.findById(id)).thenReturn(Optional.of(carro)); // Mock findById() para retornar o carro

        // When
        carroService.bloquearCarroAluguel(id);

        // Then
        assertFalse(carro.isDisponivel()); // Verifique se o carro foi bloqueado
        verify(carroRepository, times(1)).save(carro); // Verifique se o carro foi salvo
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar deletar um carro inexistente")
    public void givenInvalidId_whenBloquearCarro_Aluguel_thenThrowsEntityNotFoundException() {
        // given
        Long id = 99L;
        when(carroRepository.existsById(id)).thenReturn(false);

        // when
        EntityNotFoundException thrown = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> carroService.bloquearCarroAluguel(id),
                "Esperava-se lançar EntityNotFoundException"
        );

        // then
        assertEquals("Carro não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma página de DadosListagemCarro ao listar carros disponíveis")
    public void givenValidPageable_whenListar_thenReturnsPageOfDadosListagemCarro() {
        // given
        Pageable paginacao = PageRequest.of(0, 10);

        // Crie um objeto ModeloCarro
        Fabricante fabricante = new Fabricante(1L, "Volkswagen", null);
        ModeloCarro modeloCarro = new ModeloCarro(1L, "VW Golf", fabricante, Categoria.SEDAN_COMPACTO, null);

        List<Acessorio> acessorios = new ArrayList<>(range(0, DescricaoAcessorio.values().length)
                .mapToObj(i -> new Acessorio(i + 1, DescricaoAcessorio.values()[(int) i], null))
                .toList());

        Carro carro1 = new Carro(
                1L,
                "Corolla", // Nome do carro 1
                "ABC-1234",
                "9BWZZZ377VT004251",
                "Preto",
                true,
                BigDecimal.valueOf(200.00),
                acessorios,
                modeloCarro,
                null,
                null,
                null
        );

        Carro carro2 = new Carro(
                2L,
                "Gol", // Nome do carro 2
                "ABC-1235",
                "9BWZZZ377VT004252",
                "Branco",
                true,
                BigDecimal.valueOf(200.00),
                acessorios,
                modeloCarro,
                null,
                null,
                null
        );

        List<Carro> carros = asList(carro1, carro2);
        Page<Carro> carroPage = new PageImpl<>(carros, paginacao, carros.size());

        when(carroRepository.findAllByDisponivelTrue(paginacao)).thenReturn(carroPage);

        // when
        Page<DadosListagemCarro> result = carroService.listar(paginacao);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        // Asserções corrigidas
        assertEquals(carro1.getNome(), result.getContent().get(0).nome());
        assertEquals(carro2.getNome(), result.getContent().get(1).nome());
        assertEquals(modeloCarro.getDescricao(), result.getContent().get(0).modeloCarro());
        assertEquals(modeloCarro.getDescricao(), result.getContent().get(1).modeloCarro());

        verify(carroRepository, times(1)).findAllByDisponivelTrue(paginacao);
    }
}
