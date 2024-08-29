package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception.DuplicateEntryException;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.MotoristaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Log4j2
@Service
public class MotoristaServiceImpl implements MotoristaService {

    private final MotoristaRepository motoristaRepository;

    public MotoristaServiceImpl(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @Override
    @Transactional
    public Motorista cadastrarMotorista(@Valid DadosCadastroMotorista dadosCadastroMotorista) {
        log.info("Iniciando cadastro do motorista: {}", dadosCadastroMotorista);

        if (motoristaRepository.existsByCpf(dadosCadastroMotorista.cpf())) {
            log.warn("Tentativa de cadastro com CPF duplicado: {}", dadosCadastroMotorista.cpf());
            throw new DuplicateEntryException("Já existe um motorista cadastrado com esse CPF");
        }

        if (motoristaRepository.existsByEmail(dadosCadastroMotorista.email())) {
            log.warn("Tentativa de cadastro com e-mail duplicado: {}", dadosCadastroMotorista.email());
            throw new DuplicateEntryException("Já existe um motorista cadastrado com esse e-mail");
        }

        // Validate age (must be 18 or older)
        LocalDate birthDate = dadosCadastroMotorista.dataNascimento().atStartOfDay()
                .atZone(ZoneId.systemDefault()) // Specify time zone
                .toLocalDate();

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        if (age < 18) {
            log.warn("Tentativa de cadastro de motorista menor de idade: {}", dadosCadastroMotorista);
            throw new IllegalArgumentException("O motorista deve ter pelo menos 18 anos de idade.");
        }

        Motorista motorista = new Motorista(dadosCadastroMotorista);
        motoristaRepository.save(motorista);

        log.info("Motorista cadastrado com sucesso: {}", motorista);
        return motorista;
    }

    @Override
    public Motorista buscarPorId(Long id) {
        log.info("Buscando motorista por ID: {}", id);
        Motorista motorista = motoristaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Motorista não encontrado para o ID: {}", id);
                    return new EntityNotFoundException("Motorista não encontrado");
                });
        log.info("Motorista encontrado: {}", motorista);
        return motorista;
    }

    @Override
    @Transactional
    public Motorista atualizarMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        log.info("Atualizando motorista com dados: {}", dadosAtualizacaoMotorista);
        Motorista motorista = motoristaRepository.findById(dadosAtualizacaoMotorista.id())
                .orElseThrow(() -> {
                    log.warn("Motorista não encontrado para atualização: {}", dadosAtualizacaoMotorista.id());
                    return new EntityNotFoundException("Motorista não encontrado");
                });

        motorista.atualizarInformacoes(dadosAtualizacaoMotorista);
        motoristaRepository.save(motorista);
        log.info("Motorista atualizado com sucesso: {}", motorista);
        return motorista;
    }

    @Override
    @Transactional
    public void deletarMotorista(Long id) {
        log.info("Deletando motorista com ID: {}", id);
        if (motoristaRepository.existsById(id)) {
            motoristaRepository.deleteById(id);
            log.info("Motorista deletado com sucesso: {}", id);
        } else {
            log.warn("Tentativa de deletar motorista não existente: {}", id);
            throw new IllegalArgumentException("Motorista não encontrado");
        }
    }

    @Override
    public Page<DadosListagemMotorista> listar(Pageable paginacao) {
        log.info("Listando motoristas com paginação: {}", paginacao);
        Page<Motorista> motoristas = motoristaRepository.findAllByAtivoTrue(paginacao);
        log.info("Motoristas listados com sucesso: {}", motoristas);
        return motoristas.map(DadosListagemMotorista::new);
    }
}