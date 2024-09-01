package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosDetalhamentoCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Interface que define os serviços relacionados a carros.")
public interface CarroService {

    @Transactional
    Carro cadastrarCarro(DadosCadastroCarro dadosCadastroCarro);

    Carro buscarPorId(Long id);

    @Transactional
    Carro atualizarCarro(@Valid DadosAtualizarCarro dadosAtualizarCarro);

    @Transactional
    void excluirCarro(Long id);

    @Transactional
    void disponibilizarCarroAluguel(Long id);

    @Transactional
    void bloquearCarroAluguel(Long id);

    Page<DadosListagemCarro> listar(Pageable paginacao);

    Page<DadosListagemCarro> listarCarrosDisponiveis(Pageable paginacao);

    Page<DadosListagemCarro> listarCarrosAlugados(Pageable paginacao);

    Page<DadosDetalhamentoCarro> pesquisarCarros(
            String nome,
            String placa,
            String chassi,
            String cor,
            Boolean disponivel,
            BigDecimal valorDiariaMin,
            BigDecimal valorDiariaMax,
            String modeloDescricao,
            String fabricanteNome,
            String categoriaNome,
            List<String> acessoriosNomes,
            Pageable paginacao
    );
}
