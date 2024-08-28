package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosListagemMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception.DuplicateEntryException;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.MotoristaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MotoristaServiceImpl implements MotoristaService {

    private final MotoristaRepository motoristaRepository;

    public MotoristaServiceImpl(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @Override
    @Transactional
    public Motorista cadastrarMotorista(@Valid DadosCadastroMotorista dadosCadastroMotorista) {

        if (motoristaRepository.existsByCpf(dadosCadastroMotorista.cpf()))
            throw new DuplicateEntryException("Já existe um motorista cadastrado com esse CPF");

        if (motoristaRepository.existsByEmail(dadosCadastroMotorista.email()))
            throw new DuplicateEntryException("Já existe um motorista cadastrado com esse e-mail");

        Motorista motorista = new Motorista(dadosCadastroMotorista);
        motoristaRepository.save(motorista);

        return motorista;
    }

    @Override
    public Motorista buscarPorId(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado"));
    }

    @Override
    @Transactional
    public Motorista atualizarMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        Motorista motorista = motoristaRepository.findById(dadosAtualizacaoMotorista.id())
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado"));

        motorista.atualizarInformacoes(dadosAtualizacaoMotorista);
        motoristaRepository.save(motorista);
        return motorista;
    }

    @Override
    @Transactional
    public void deletarMotorista(Long id) {
        if (motoristaRepository.existsById(id)) {
            motoristaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Motorista não encontrado");
        }
    }

    @Override
    public Page<DadosListagemMotorista> listar(Pageable paginacao) {
        Page<Motorista> motoristas = motoristaRepository.findAllByAtivoTrue(paginacao);
        return motoristas.map(DadosListagemMotorista::new);
    }
}
