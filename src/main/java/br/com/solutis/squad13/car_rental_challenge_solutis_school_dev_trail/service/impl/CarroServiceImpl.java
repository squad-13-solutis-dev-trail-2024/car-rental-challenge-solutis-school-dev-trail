package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CarroServiceImpl implements CarroService {

    private final CarroRepository carroRepository;

    public CarroServiceImpl(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @Override
    public Carro findById(Long id){
        return carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado com o ID: " + id));
    }

    @Override
    public Page<DadosListagemCarro> listar(Pageable pageable){
        log.info("Listando motoristas com paginação: {}", pageable);
        Page<Carro> carro = carroRepository.findAllByAtivoTrue(pageable);
        log.info("Motoristas listados com sucesso: {}", carro);
        return carro.map(DadosListagemCarro::new);
    }

    /// Eu acho que daqui para baixo da ruim man
    @Override
    public Page<DadosListagemCarro> listarCarroCategoria(Categoria categoria, Pageable pageable) {
        log.info("Listando carros pela categoria: {}", categoria);
        Page<Carro> carros = carroRepository.findAllByCategoria(categoria, pageable);
        log.info("Carros listados com sucesso: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    public Page<DadosListagemCarro> listarCarroAcessorio(Acessorio acessorio){
        log.info("Listando carros pela categoria: {}", acessorio);
        Page<Carro> carros = carroRepository.findAllByAcessorios(acessorio);
        log.info("Carros listados com sucesso: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }
}
