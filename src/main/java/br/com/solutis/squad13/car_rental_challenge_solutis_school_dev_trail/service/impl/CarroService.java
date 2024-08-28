package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
@Log4j2
public class CarroService {


    @Autowired
    private CarroRepository carroRepository;

    public Carro findById(Long id){
        return carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado com o ID: " + id));
    }

    public List<Carro> buscarCarrosDisponiveis() {
        return carroRepository.findByDisponivelParaAluguel(true);
    }

}
