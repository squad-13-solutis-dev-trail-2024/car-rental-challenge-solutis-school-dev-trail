package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarroServiceImpl implements CarroService {

    private static final Logger log = LoggerFactory.getLogger(CarroServiceImpl.class);

    private final CarroRepository carroRepository;

    public CarroServiceImpl(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @Transactional
    public Carro cadastrarCarro(@Valid DadosCadastroCarro dadosCadastroCarro) {
        log.info("Iniciando cadastro do carro: {}", dadosCadastroCarro);

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

    @Transactional
    @Override
    public Carro atualizarCarro(@Valid DadosAtualizarCarro dadosAtualizarCarro) {
        log.info("Atualizando carro com dados: {}", dadosAtualizarCarro);

        Carro carro = carroRepository.findById(dadosAtualizarCarro.id())
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado para atualização: {}", dadosAtualizarCarro.id());
                    return new EntityNotFoundException("Carro não encontrado");
                });

        if (!carro.getPlaca().equals(dadosAtualizarCarro.placa()) || !carro.getChassi().equals(dadosAtualizarCarro.chassi())) {
//            validarCamposDuplicados(dadosAtualizacaoCarro);
        }

        carro.setNome(dadosAtualizarCarro.nome());
        carro.setPlaca(dadosAtualizarCarro.placa());
        carro.setChassi(dadosAtualizarCarro.chassi());
        carro.setCor(dadosAtualizarCarro.cor());
        carro.setValorDiaria(dadosAtualizarCarro.valorDiario());
        carro.setModelo(dadosAtualizarCarro.modelo());

        carroRepository.save(carro);

        log.info("Carro atualizado com sucesso: {}", carro);
        return carro;
    }

    public Page<Carro> listar(Pageable paginacao) {
        log.info("Listando carros com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAll(paginacao);
        log.info("Carros listados com sucesso: {}", carros);
        return carros;
    }

    @Transactional
    public void deletarCarro(Long id) {
        log.info("Deletando carro com ID: {}", id);
        if (carroRepository.existsById(id)) {
            carroRepository.deleteById(id);
            log.info("Carro deletado com sucesso: {}", id);
        } else {
            log.warn("Tentativa de deletar carro não existente: {}", id);
            throw new EntityNotFoundException("Carro não encontrado com o ID: " + id);
        }
    }

    private void validarCamposDuplicados(DadosCadastroCarro dados) {
        List<String> errosDuplicados = new ArrayList<>();

        if (carroRepository.existsByPlaca(dados.placa())){
            log.warn("Tentativa de cadastro de Placa duplicada: {}", dados.placa());
            errosDuplicados.add("Já existe um carro cadastrado com esta placa");
        }

        if (carroRepository.existsByChassi(dados.chassi())){
            log.warn("Tentativa de cadastro do Chassi duplicada: {}", dados.chassi());
            errosDuplicados.add("Já existe um carro cadastrado com esta chassi");
        }
    }
}
