package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public record CarroDTO(Long id, String placa,
         String chassi,
        String cor,
        BigDecimal valorDiaria,
         boolean disponivelParaAluguel,
        MotoristaDTO motorista,
         CarroDTO carro) {

}
