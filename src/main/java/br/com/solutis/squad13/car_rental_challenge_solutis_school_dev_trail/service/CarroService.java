package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Schema(description = "Interface que define os serviços relacionados a carros.")
public interface CarroService {

    @Transactional
    Carro cadastrarCarro(DadosCadastroCarro dadosCadastroCarro);

    Carro buscarPorId(Long id);

    @Transactional
    Carro atualizarCarro(@Valid DadosAtualizarCarro dadosAtualizarCarro);

    @Transactional
    void deletarCarro(Long id);

    @Transactional
    void desativarCarro(Long id);

    Page<DadosListagemCarro> listar(Pageable paginacao);
}
