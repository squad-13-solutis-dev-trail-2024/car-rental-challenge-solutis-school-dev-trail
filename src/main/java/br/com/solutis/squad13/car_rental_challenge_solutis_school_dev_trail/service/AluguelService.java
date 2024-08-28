package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;

public interface AluguelService {
    Aluguel findByid(Long id);
    Aluguel alugar(Aluguel aluguel, String emailMotorista, Long idCarro);
}
