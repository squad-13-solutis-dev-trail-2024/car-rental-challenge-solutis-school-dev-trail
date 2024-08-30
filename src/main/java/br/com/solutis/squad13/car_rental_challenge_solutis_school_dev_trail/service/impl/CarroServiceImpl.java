package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;

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

        Carro carro = new Carro(dadosCadastroCarro);
        carroRepository.save(carro);

        log.info("Carro cadastrado com sucesso: {}", carro);
        return carro;
    }

    @Override
    public Carro buscarPorId(Long id) {
        log.info("Buscando carro por ID: {}", id);
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado para ID: {}", id);
                    return new EntityNotFoundException("Carro não encontrado");
                });

        log.info("Carro encontrado: {}", carro);
        return carro;
    }

    @Override
    @Transactional
    public Carro atualizarCarro(@Valid DadosAtualizarCarro dadosAtualizarCarro) {
        log.info("Atualizando carro com dados: {}", dadosAtualizarCarro);
        Carro carro = carroRepository.findById(dadosAtualizarCarro.id())
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado para atualização: {}", dadosAtualizarCarro.id());
                    return new EntityNotFoundException("Carro não encontrado");
                });

        log.info("Carro encontrado para atualização: {}", carro);
        log.info("Verificando se há campos para atualização que não permitem duplicação");

        //Chassi e placa
        if (dadosAtualizarCarro.chassi() != null ||
                dadosAtualizarCarro.placa() != null) {

            log.info("Verificando se o chassi e a placa já estão cadastrados");
            if (dadosAtualizarCarro.chassi() != null) {
                if (!carro.getChassi().equals(dadosAtualizarCarro.chassi())) {
                    if (carroRepository.existsByChassi(dadosAtualizarCarro.chassi())) {
                        log.warn("Tentativa de atualização de chassi para um já cadastrado: {}", dadosAtualizarCarro.chassi());
                        throw new EntityExistsException("Chassi já cadastrado");
                    }
                }
            }

            if (dadosAtualizarCarro.placa() != null) {
                if (!carro.getPlaca().equals(dadosAtualizarCarro.placa())) {
                    if (carroRepository.existsByPlaca(dadosAtualizarCarro.placa())) {
                        log.warn("Tentativa de atualização de placa para uma já cadastrada: {}", dadosAtualizarCarro.placa());
                        throw new EntityExistsException("Placa já cadastrada");
                    }
                }
            }
        }

        carro.atualizar(dadosAtualizarCarro);
        carroRepository.save(carro);
        log.info("Carro atualizado com sucesso: {}", carro);
        return carro;
    }

    @Override
    public void deletarCarro(Long id) {
        log.info("Deletando carro com ID: {}", id);
        if (!carroRepository.existsById(id)) {
            log.warn("Carro não encontrado para deletar: {}", id);
            throw new EntityNotFoundException("Carro não encontrado");
        } else {
            carroRepository.deleteById(id);
            log.info("Carro deletado com sucesso: {}", id);
        }
    }

    @Override
    @Transactional
    public void desativarCarro(Long id) {
        log.info("Desativando carro com ID: {}", id);
        Carro carro = carroRepository.getReferenceById(id);

        carro.bloquearAluguel();
        carroRepository.save(carro);
        log.info("Carro desativado com sucesso: {}", carro);
    }

    @Override
    public Page<DadosListagemCarro> listar(Pageable paginacao) {
        log.info("Listando carros com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAllByAtivoTrue(paginacao);
        log.info("Carros encontrados: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    private void validarCamposDuplicados(@Valid DadosCadastroCarro dadosCadastroCarro) {
        List<String> erroDuplicados = new ArrayList<>();

        if (carroRepository.existsByPlaca(dadosCadastroCarro.placa())) {
            log.warn("Placa já cadastrada: {}", dadosCadastroCarro.placa());
            erroDuplicados.add("Placa já cadastrada");
        }

        if (carroRepository.existsByChassi(dadosCadastroCarro.chassi())) {
            log.warn("Chassi já cadastrado: {}", dadosCadastroCarro.chassi());
            erroDuplicados.add("Chassi já cadastrado");
        }

        if (!erroDuplicados.isEmpty()) {
            log.warn("Campos duplicados: {}", erroDuplicados);
            throw new EntityExistsException("Campos duplicados");
        }
    }
}
