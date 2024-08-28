package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.MotoristaDTO;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.AluguelRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final MotoristaRepository motoristaRepository;

    public UserServiceImpl(MotoristaRepository motoristaRepository, AluguelRepository aluguelRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @Override
    public Motorista findByEmail(String email) {
        Motorista motorista = motoristaRepository.findByEmail(email);
        if (motorista == null) {
            log.warn("Motorista não encontrado");
        }
        return motorista;
    }

    @Override
    public Motorista create(Motorista motorista) {
        Motorista existMotorista = motoristaRepository.findByEmail(motorista.getEmail());
        if (existMotorista == null) {
            return motoristaRepository.save(motorista);
        } else {
            throw new RuntimeException("Email já cadastrado.");
        }
    }

    @Override
    public MotoristaDTO convertToDTO(Motorista motorista) {
        return new MotoristaDTO(motorista.getId(), motorista.getNome(), motorista.getEmail(), motorista.getDataNascimento(),
                motorista.getCpf(), motorista.getSexo(), motorista.getNumeroCNH(), (Aluguel) motorista.getAlugueis());
    }
}