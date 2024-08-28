package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.AluguelRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.AluguelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Log4j2
public class AluguelServiceImpl implements AluguelService {

    private final AluguelRepository aluguelRepository;
    private final MotoristaRepository motoristaRepository;
    private final CarroRepository carroRepository;

    public AluguelServiceImpl(AluguelRepository aluguelRepository,
                              MotoristaRepository motoristaRepository,
                              CarroRepository carroRepository) {
        this.aluguelRepository = aluguelRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
    }

    @Override
    public Aluguel findByid(Long id){
        Optional<Aluguel> aluguel = aluguelRepository.findById(id);
        if(aluguel.isEmpty()){
            log.warn("Informações do aluguel não encontrado!");
        }
        return aluguel.orElse(null);
    }

    @Override
    public Aluguel alugar(Aluguel aluguel, String emailMotorista, Long idCarro){
        Motorista motorista = motoristaRepository.findByEmail(emailMotorista);

        Carro carro = carroRepository.findById(idCarro)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado com o ID: " + idCarro));
        if(carro.isDisponivelParaAluguel()){
            carro.bloquearAluguel();
            carroRepository.save(carro);
            aluguel.setMotorista(motorista);
            aluguel.setCarro(carro);
            aluguel.setDataPedido(LocalDate.from(Instant.now()));
            return aluguelRepository.save(aluguel);
        } else{
            throw new RuntimeException("Carro indisponivel para aluguel");
        }
    }
}