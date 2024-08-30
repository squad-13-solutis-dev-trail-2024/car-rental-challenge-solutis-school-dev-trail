package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Interface que define os serviços relacionados a Alguel.")
public interface AluguelService {

    Aluguel buscarPorId(Long id);

    @Transactional
    Aluguel alugar(@Valid DadosCadastroAluguel aluguel);

    Page<DadosListagemAluguel> listar(Pageable paginacao);

    Page<DadosListagemAluguel> listarAlugueisPorCliente(Long idCliente, Pageable paginacao);

    BigDecimal calcularCustoTotal(Long idCarro, Long idApoliceSeguro, Aluguel aluguel);

//    @Transactional
//    Aluguel confirmarAluguel(Long idAluguel, DadosPagamento dadosPagamento);

    void cancelarAluguel(Long idAluguel);
}