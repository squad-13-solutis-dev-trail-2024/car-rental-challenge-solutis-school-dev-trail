package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarroServiceImpl implements CarroService {

    private final CarroRepository carroRepository;

    @Autowired
    public CarroServiceImpl(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @Override
    public Carro findById(Long id){
        return carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado com o ID: " + id));
    }
}
