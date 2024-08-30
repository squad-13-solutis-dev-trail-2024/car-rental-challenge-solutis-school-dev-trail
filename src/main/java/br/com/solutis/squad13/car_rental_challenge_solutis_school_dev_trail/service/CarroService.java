package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

public interface CarroService {

    Carro cadastrarCarro(DadosCadastroCarro dadosCadastroCarro);

    Carro buscarPorId(Long id);

    @Transactional
    Carro atualizarCarro(@Valid DadosAtualizarCarro dadosAtualizarCarro);

    @Transactional
    void deletarCarro(Long id);

    //Page<DadosListagemMotorista> listar(Pageable paginacao);
}
