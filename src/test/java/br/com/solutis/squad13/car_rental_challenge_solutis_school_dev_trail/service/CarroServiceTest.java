package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl.CarroServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        // given
        DadosCadastroCarro dados = new DadosCadastroCarro("ABC-1234", "9BWZZZ377VT004251",
                "VW Golf", "Preto", BigDecimal.valueOf(200.00), null, null);

        Carro carroEsperado = new Carro(dados);

        when(carroRepository.existsByPlaca(dados.placa())).thenReturn(false);
        when(carroRepository.existsByChassi(dados.chassi())).thenReturn(false);
        when(carroRepository.save(Mockito.any(Carro.class))).thenReturn(carroEsperado);

        // when
        Carro carroSalvo = carroService.cadastrarCarro(dados);

        // then
        assertNotNull(carroSalvo);
        assertEquals("ABC-1234", carroSalvo.getPlaca());
        assertEquals("9BWZZZ377VT004251", carroSalvo.getChassi());
        assertEquals("VW Golf", carroSalvo.getModelo().getDescricao());
        assertEquals("Preto", carroSalvo.getCor());

        verify(carroRepository, times(1)).save(carroEsperado);
    }

    @Test
    @DisplayName("Deve lançar EntityExistsException ao tentar cadastrar um carro com placa duplicada")
    public void givenInvalidId_whenBuscarPorId_thenThrowsEntityNotFoundException() {
        // given
        Long id = 1L;
        when(carroRepository.findById(id)).thenReturn(Optional.empty());

        // when
        EntityNotFoundException thrown = Assertions.assertThrows(
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
        // given
        DadosAtualizarCarro dadosAtualizar = new DadosAtualizarCarro(1L, "DEF-5678", "9BWZZZ377VT004252",
                "VW Jetta", "Branco",
                BigDecimal.valueOf(200.00), null, null);

        Carro carroExistente = new Carro(
                1L,
                "ABC-1234",
                "9BWZZZ377VT004251",
                "VW Golf",
                "Preto",
                true,
                BigDecimal.valueOf(200.00),
                null,
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
    @DisplayName("Deve deletar um carro existente com sucesso")
    public void givenValidId_whenBloquearCarro_thenCarroAluguelIsDeleted() {

        // given
        Long id = 1L;
        when(carroRepository.existsById(id)).thenReturn(true);
        doNothing().when(carroRepository).deleteById(id);

        //when
        carroService.bloquearCarroAluguel(id);

        //then
        verify(carroRepository, times(1)).deleteById(id);
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

        Carro carro1 = new Carro(
                1L,
                "ABC-1234",
                "9BWZZZ377VT004251",
                "VW Golf",
                "Preto",
                true,
                BigDecimal.valueOf(200.00),
                null,
                null,
                null,
                null,
                null
        );

        Carro carro2 = new Carro(
                2L,
                "ABC-1235",
                "9BWZZZ377VT004251",
                "Gol",
                "Branco",
                true,
                BigDecimal.valueOf(200.00),
                null,
                null,
                null,
                null,
                null
        );

        List<Carro> carros = Arrays.asList(carro1, carro2);
        Page<Carro> carroPage = new PageImpl<>(carros, paginacao, carros.size());

        when(carroRepository.findAllByDisponivelTrue(paginacao)).thenReturn(carroPage);

        // when
        Page<DadosListagemCarro> result = carroService.listar(paginacao);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Corolla", result.getContent().get(0).nome());
        assertEquals("Civic", result.getContent().get(1).nome());
        verify(carroRepository, times(1)).findAllByDisponivelTrue(paginacao);
    }
}
