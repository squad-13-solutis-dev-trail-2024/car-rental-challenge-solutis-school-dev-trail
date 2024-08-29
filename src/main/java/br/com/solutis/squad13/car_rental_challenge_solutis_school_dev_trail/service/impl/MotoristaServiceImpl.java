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
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MotoristaServiceImpl implements MotoristaService {

    private static final Logger log = getLogger(MotoristaServiceImpl.class);

    private final MotoristaRepository motoristaRepository;

    public MotoristaServiceImpl(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @Override
    @Transactional
    public Motorista cadastrarMotorista(@Valid DadosCadastroMotorista dadosCadastroMotorista) {
        log.info("Iniciando cadastro do motorista: {}", dadosCadastroMotorista);
        validarCamposDuplicados(dadosCadastroMotorista);

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
                    log.warn("Motorista não encontrado para ID: {}", id);
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

        log.info("Motorista encontrado para atualização: {}", motorista);
        log.info("Verificando se há campos para atualização que não permitem duplicação");
        if (dadosAtualizacaoMotorista.cpf() != null ||
                dadosAtualizacaoMotorista.email() != null ||
                dadosAtualizacaoMotorista.numeroCNH() != null) {

            log.info("Se chegou aqui é pq o usuário decidiu atualizar ou CPF, ou EMAIL ou numeroCNH");
            DadosCadastroMotorista dadosParaValidacao = new DadosCadastroMotorista(
                    motorista.getCpf(),
                    motorista.getEmail(),
                    motorista.getNumeroCNH()
            );

            validarCamposDuplicados(dadosParaValidacao); // Valide os campos duplicados
        }

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
            throw new EntityNotFoundException("Motorista não encontrado com o ID: " + id);
        }
    }

    @Override
    public Page<DadosListagemMotorista> listar(Pageable paginacao) {
        log.info("Listando motoristas com paginação: {}", paginacao);
        Page<Motorista> motoristas = motoristaRepository.findAllByAtivoTrue(paginacao);
        log.info("Motoristas listados com sucesso: {}", motoristas);
        return motoristas.map(DadosListagemMotorista::new);
    }

    private void validarCamposDuplicados(DadosCadastroMotorista dados) {
        List<String> errosDuplicados = new ArrayList<>();

        if (motoristaRepository.existsByCpf(dados.cpf())) {
            log.warn("Tentativa de cadastro com CPF duplicado: {}", dados.cpf());
            errosDuplicados.add("Já existe um motorista cadastrado com esse CPF");
        }

        if (motoristaRepository.existsByEmail(dados.email())) {
            log.warn("Tentativa de cadastro com e-mail duplicado: {}", dados.email());
            errosDuplicados.add("Já existe um motorista cadastrado com esse e-mail");
        }

        if (motoristaRepository.existsByNumeroCNH(dados.numeroCNH())) {
            log.warn("Tentativa de cadastro com número da CNH duplicado: {}", dados.numeroCNH());
            errosDuplicados.add("Já existe um motorista cadastrado com esse número da CNH");
        }

        if (!errosDuplicados.isEmpty()) {
            String mensagemErro = String.join("\n", errosDuplicados); // Junta as mensagens de erro com quebra de linha
            throw new DuplicateEntryException(mensagemErro);
        }
    }
}