package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.MotoristaDTO;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;


public interface UserService {
    Motorista findByEmail(String email);
    Motorista create(Motorista motorista);
    MotoristaDTO convertToDTO(Motorista motorista);
}