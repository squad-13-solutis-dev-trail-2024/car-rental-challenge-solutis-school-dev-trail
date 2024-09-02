package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception.DuplicateEntryException;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.MotoristaService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.join;
import static org.slf4j.LoggerFactory.getLogger;

@Service("motoristaService")
@Schema(description = "Serviço que implementa as operações relacionadas a motoristas.")
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
        log.info("Campos únicos validados com sucesso");

        Motorista motorista = new Motorista(dadosCadastroMotorista);
        motoristaRepository.save(motorista);

        log.info("Motorista cadastrado com sucesso: {}", motorista);
        return motorista;
    }

    @Override
    public Motorista buscarPorId(Long id) {
        log.info("Buscando motorista por ID: {}", id);
        Motorista motorista = existeMotoristaPeloId(id);
        log.info("Motorista encontrado: {}", motorista);
        return motorista;
    }

    @Override
    @Transactional
    public Motorista atualizarMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        log.info("Atualizando motorista com dados: {}", dadosAtualizacaoMotorista);

        Motorista motorista = existeMotoristaPeloIdNoDto(dadosAtualizacaoMotorista);
        log.info("Motorista encontrado para atualização: {}", motorista);

        validarAtualizacaoComDadosUnicos(dadosAtualizacaoMotorista, motorista);
        log.info("Dados únicos validados com sucesso");

        motorista.atualizarInformacoes(dadosAtualizacaoMotorista);
        motoristaRepository.save(motorista);

        log.info("Motorista atualizado com sucesso: {}", motorista);
        return motorista;
    }

    @Override
    @Transactional
    public void deletarMotorista(Long id) {
        log.info("Deletando motorista com ID: {}", id);
        Motorista motorista = existeMotoristaPeloId(id); // Busca o motorista pelo ID

        log.info("Motorista encontrado para deleção: {}", motorista);
        motoristaRepository.delete(motorista); // Usa delete(motorista) para deletar o objeto
        log.info("Motorista deletado com sucesso: {}", id);
    }

    @Override
    @Transactional
    public void desativarMotorista(Long id) {
        log.info("Desativando motorista com ID: {}", id);

        Motorista motorista = existeMotoristaPeloId(id);
        log.info("Motorista encontrado para desativação: {}", motorista);

        motorista.desativar();

        motoristaRepository.save(motorista);
        log.info("Motorista desativado com sucesso: {}", motorista);
    }

    @Override
    public Page<DadosListagemMotorista> listar(Pageable paginacao) {
        log.info("Listando motoristas com paginação: {}", paginacao);
        Page<Motorista> motoristas = motoristaRepository.findAllByAtivoTrue(paginacao);
        log.info("Motoristas listados com sucesso: {}", motoristas);
        return motoristas.map(DadosListagemMotorista::new);
    }

    private Motorista existeMotoristaPeloId(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Motorista não encontrado para desativação: {}", id);
                    return new EntityNotFoundException("Motorista não encontrado");
                });
    }

    private Motorista existeMotoristaPeloIdNoDto(DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        return motoristaRepository.findById(dadosAtualizacaoMotorista.id())
                .orElseThrow(() -> {
                    log.warn("Motorista não encontrado para atualização: {}", dadosAtualizacaoMotorista.id());
                    return new EntityNotFoundException("Motorista não encontrado");
                });
    }

    private void validarAtualizacaoComDadosUnicos(DadosAtualizacaoMotorista dados, Motorista motoristaAtual) {
        List<String> errosDuplicidade = new ArrayList<>();

        if (dados.cpf() != null && !dados.cpf().equals(motoristaAtual.getCpf()))
            verificarDuplicidade(dados.cpf(), motoristaRepository.existsByCpf(dados.cpf()), "CPF", errosDuplicidade);

        if (dados.email() != null && !dados.email().equals(motoristaAtual.getEmail()))
            verificarDuplicidade(dados.email(), motoristaRepository.existsByEmail(dados.email()), "e-mail", errosDuplicidade);

        if (dados.numeroCNH() != null && !dados.numeroCNH().equals(motoristaAtual.getNumeroCNH()))
            verificarDuplicidade(dados.numeroCNH(), motoristaRepository.existsByNumeroCNH(dados.numeroCNH()), "número da CNH", errosDuplicidade);

        // Se houver duplicidades, lançar exceção com todos os erros.
        if (!errosDuplicidade.isEmpty()) {
            String mensagemErro = join(", ", errosDuplicidade);
            log.warn("Tentativa de atualização com dados duplicados: {}", mensagemErro);
            throw new DuplicateEntryException("Já existem motoristas cadastrados com os seguintes campos duplicados: " + mensagemErro);
        }
    }

    private void verificarDuplicidade(String valor, boolean existe, String campo, List<String> errosDuplicidade) {
        if (existe) {
            log.warn("Tentativa de atualização com {} duplicado: {}", campo, valor);
            errosDuplicidade.add(campo + ": " + valor);
        }
    }

    private void validarCamposDuplicados(@Valid DadosCadastroMotorista dados) {
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
            String mensagemErro = join("\n", errosDuplicados); // Junta as mensagens de erro com quebra de linha
            throw new DuplicateEntryException(mensagemErro);
        }
    }
}