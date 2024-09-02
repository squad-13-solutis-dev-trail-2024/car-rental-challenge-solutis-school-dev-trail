package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarrinhoAluguelRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.CarrinhoAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarrinhoAluguelService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service("CarrinhoAluguelService")
@Schema(description = "Serviço que implementa as operações do carrinho de aluguel")
public class CarrinhoAluguelServiceImpl implements CarrinhoAluguelService {

    private static final Logger log = getLogger(CarrinhoAluguelServiceImpl.class);

    private final CarrinhoAluguelRepository carrinhoAluguelRepository;
    private final MotoristaRepository motoristaRepository;
    private final CarroRepository carroRepository;

    public CarrinhoAluguelServiceImpl(CarrinhoAluguelRepository carrinhoAluguelRepository,
                                      MotoristaRepository motoristaRepository,
                                      CarroRepository carroRepository) {
        this.carrinhoAluguelRepository = carrinhoAluguelRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
    }

    @Override
    public CarrinhoAluguel buscarPorId(Long id) {
        log.info("Buscando carrinho de aluguel por ID: {}", id);

        return carrinhoAluguelRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Carrinho de aluguel não encontrado com o ID: {}", id);
                    return new EntityNotFoundException("Carrinho de aluguel não encontrado com o ID: " + id);
                });
    }

    @Override
    public CarrinhoAluguel buscarPorMotoristaId(Long motoristaId) {
        log.info("Buscando carrinho de aluguel por ID do motorista: {}", motoristaId);
        return carrinhoAluguelRepository.findByMotoristaId(motoristaId)
                .orElseThrow(() -> {
                    log.warn("Carrinho de aluguel não encontrado para o motorista com ID: {}", motoristaId);
                    return new EntityNotFoundException("Carrinho de aluguel não encontrado para o motorista com ID: " + motoristaId);
                });
    }

    @Override
    @Transactional
    public CarrinhoAluguel adicionarCarroAoCarrinho(Long motoristaId, Long carroId) {
        log.info("Adicionando carro com ID {} ao carrinho do motorista com ID {}", carroId, motoristaId);

        Motorista motorista = motoristaRepository.findById(motoristaId)
                .orElseThrow(() -> {
                    log.warn("Motorista não encontrado com o ID: {}", motoristaId);
                    return new EntityNotFoundException("Motorista não encontrado com o ID: " + motoristaId);
                });

        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado com o ID: {}", carroId);
                    return new EntityNotFoundException("Carro não encontrado com o ID: " + carroId);
                });

        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelRepository.findByMotoristaId(motoristaId)
                .orElseGet(() -> {
                    log.info("Carrinho de aluguel não encontrado para o motorista. Criando um novo carrinho.");
                    CarrinhoAluguel novoCarrinho = new CarrinhoAluguel();
                    novoCarrinho.setMotorista(motorista);
                    return novoCarrinho;
                });

        carrinhoAluguel.adicionarCarro(carro);
        log.info("Carro adicionado ao carrinho com sucesso. ID do carrinho: {}", carrinhoAluguel.getId());
        return carrinhoAluguelRepository.save(carrinhoAluguel);
    }

    @Override
    @Transactional
    public void removerCarroDoCarrinho(Long motoristaId, Long carroId) {
        log.info("Removendo carro com ID {} do carrinho do motorista com ID {}", carroId, motoristaId);

        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelRepository.findByMotoristaId(motoristaId)
                .orElseThrow(() -> {
                    log.warn("Carrinho de aluguel não encontrado para o motorista com ID: {}", motoristaId);
                    return new EntityNotFoundException("Carrinho de aluguel não encontrado para o motorista com ID: " + motoristaId);
                });

        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado com o ID: {}", carroId);
                    return new EntityNotFoundException("Carro não encontrado com o ID: " + carroId);
                });

        carrinhoAluguel.retirarCarro(carro);
        carrinhoAluguelRepository.save(carrinhoAluguel);
        log.info("Carro removido do carrinho com sucesso.");
    }

    @Override
    public Page<Carro> listarCarrosNoCarrinho(Long motoristaId, Pageable pageable) {
        log.info("Listando carros no carrinho do motorista com ID {} e paginação: {}", motoristaId, pageable);

        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelRepository.findByMotoristaId(motoristaId)
                .orElseThrow(() -> {
                    log.warn("Carrinho de aluguel não encontrado para o motorista com ID: {}", motoristaId);
                    return new EntityNotFoundException("Carrinho de aluguel não encontrado para o motorista com ID: " + motoristaId);
                });

        // Cria uma Page a partir da lista de carros no carrinho
        List<Carro> carros = carrinhoAluguel.getVeiculos().stream().toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), carros.size());
        return new PageImpl<>(carros.subList(start, end), pageable, carros.size());
    }
}
