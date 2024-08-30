package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Schema(description = "Interface que define os serviços relacionados a motoristas.")
public interface MotoristaService {

    @Transactional
    Motorista cadastrarMotorista(@Valid DadosCadastroMotorista motorista);

    Motorista buscarPorId(Long id);

    @Transactional
    Motorista atualizarMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista);

    @Transactional
    void deletarMotorista(Long id);

    @Transactional
    void desativarMotorista(Long id);

    Page<DadosListagemMotorista> listar(Pageable paginacao);
}
