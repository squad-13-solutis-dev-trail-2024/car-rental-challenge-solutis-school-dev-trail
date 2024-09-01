package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Schema(description = "Interface que define os serviços relacionados a Alguel.")
public interface AluguelService {

    Aluguel buscarPorId(Long id);

    @Transactional
    Aluguel reservarCarro(@Valid DadosCadastroAluguel aluguel);

    @Transactional
    Aluguel confirmarAluguel(Long idAluguel, DadosPagamento dadosPagamento);

    @Transactional
    Aluguel finalizarAluguel(Long idAluguel, LocalDate dataDevolucao);
    @Transactional
    Aluguel trocarCarro(@Valid Long idAluguel, Long idCarro);

    @Transactional
    Aluguel cancelarAluguel(Long idAluguel);

    Page<DadosListagemAluguel> listarAlugueis(Pageable paginacao);
}