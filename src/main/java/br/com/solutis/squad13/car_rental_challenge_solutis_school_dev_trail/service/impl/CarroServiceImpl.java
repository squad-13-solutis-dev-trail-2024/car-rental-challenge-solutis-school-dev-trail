package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@Schema(description = "Serviço que implementa as operações relacionadas a carros.")
public class CarroServiceImpl implements CarroService {

    private static final Logger log = getLogger(CarroServiceImpl.class);

    private final CarroRepository carroRepository;

    public CarroServiceImpl(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @Override
    @Transactional
    public Carro cadastrarCarro(@Valid DadosCadastroCarro dadosCadastroCarro) {
        log.info("Iniciando cadastro do carro: {}", dadosCadastroCarro);
        validarCamposDuplicados(dadosCadastroCarro);
        log.info("Campos validados com sucesso");

        Carro carro = new Carro(dadosCadastroCarro);
        carroRepository.save(carro);

        log.info("Carro cadastrado com sucesso: {}", carro);
        return carro;
    }

    @Override
    public Carro buscarPorId(Long id) {
        log.info("Buscando carro por ID: {}", id);
        Carro carro = existeCarroPeloId(id);
        log.info("Carro encontrado: {}", carro);
        return carro;
    }

    @Override
    @Transactional
    public Carro atualizarCarro(@Valid DadosAtualizarCarro dadosAtualizarCarro) {
        log.info("Atualizando carro com dados: {}", dadosAtualizarCarro);
        Carro carro = existeCarroPeloId(dadosAtualizarCarro.id());
        log.info("Carro encontrado para atualização: {}", carro);

        validarCamposDuplicadosNoDtoAtualizacao(dadosAtualizarCarro, carro); // Verifica se há campos para atualização que não permitem duplicação

        carro.atualizar(dadosAtualizarCarro);
        carroRepository.save(carro);
        log.info("Carro atualizado com sucesso: {}", carro);
        return carro;
    }

    @Override
    @Transactional
    public void bloquearCarroAluguel(Long id) {
        log.info("Desativando carro com ID: {}", id);
        Carro carro = existeCarroPeloId(id);
        carro.bloquearAluguel();
        carroRepository.save(carro);
        log.info("Carro desativado com sucesso: {}", carro);

    }

    @Override
    @Transactional
    public void disponibilizarCarroAluguel(Long id) {
        log.info("Ativando carro com ID: {}", id);
        Carro carro = existeCarroPeloId(id);
        carro.disponibilizarAluguel();
        carroRepository.save(carro);
        log.info("Carro ativado com sucesso: {}", carro);
    }

    @Override
    @Transactional
    public void excluirCarro(Long id) {
        log.info("Deletando carro com ID: {}", id);
        existeCarroPeloId(id);
        carroRepository.deleteById(id);
        log.info("Carro deletado com sucesso: {}", id);
    }

    @Override
    public Page<DadosListagemCarro> listar(Pageable paginacao) {
        log.info("Listando carros com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAllByDisponivelTrue(paginacao);
        log.info("Carros encontrados: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    @Override
    public Page<DadosListagemCarro> listarCarrosDisponiveis(Pageable paginacao) {
        log.info("Listando carros disponíveis com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAllByDisponivelTrue(paginacao);
        log.info("Carros disponíveis encontrados: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    @Override
    public Page<DadosListagemCarro> listarCarrosAlugados(Pageable paginacao) {
        log.info("Listando carros alugados com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAllByDisponivelFalse(paginacao);
        log.info("Carros alugados encontrados: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    private void validarCamposDuplicadosNoDtoAtualizacao(@Valid DadosAtualizarCarro dadosAtualizarCarro, Carro carroAtual) {
        List<String> erroDuplicados = new ArrayList<>();

        if (dadosAtualizarCarro.placa() != null && !dadosAtualizarCarro.placa().equals(carroAtual.getPlaca()))
            verificarDuplicidade(dadosAtualizarCarro.placa(), carroRepository.existsByPlaca(dadosAtualizarCarro.placa()), "Placa", erroDuplicados);

        if (dadosAtualizarCarro.chassi() != null && !dadosAtualizarCarro.chassi().equals(carroAtual.getChassi()))
            verificarDuplicidade(dadosAtualizarCarro.chassi(), carroRepository.existsByChassi(dadosAtualizarCarro.chassi()), "Chassi", erroDuplicados);

        if (!erroDuplicados.isEmpty()) {
            log.warn("Campos duplicados ao atualizar: {}", erroDuplicados);
            throw new EntityExistsException("Campos duplicados: " + String.join(", ", erroDuplicados));
        }
    }

    private void validarCamposDuplicados(@Valid DadosCadastroCarro dadosCadastroCarro) {
        List<String> erroDuplicados = new ArrayList<>();

        verificarDuplicidade(dadosCadastroCarro.placa(), carroRepository.existsByPlaca(dadosCadastroCarro.placa()), "Placa", erroDuplicados);
        verificarDuplicidade(dadosCadastroCarro.chassi(), carroRepository.existsByChassi(dadosCadastroCarro.chassi()), "Chassi", erroDuplicados);

        if (!erroDuplicados.isEmpty()) {
            log.warn("Campos duplicados ao cadastrar: {}", erroDuplicados);
            throw new EntityExistsException("Campos duplicados: " + String.join(", ", erroDuplicados));
        }
    }

    private void verificarDuplicidade(String valor,
                                      boolean existe,
                                      String campo,
                                      List<String> erros) {
        if (existe) {
            log.warn("{} já cadastrado: {}", campo, valor);
            erros.add(campo + " já cadastrado");
        }
    }

    private Carro existeCarroPeloId(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado: {}", id);
                    return new EntityNotFoundException("Carro não encontrado");
                });
    }
}
