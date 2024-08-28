package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto;


import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Sexo;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public record MotoristaDTO(Long id,
                           String nome,
                           String email,
                           Date dataNascimento,
                           String cpf,
                           Sexo Sexo,
                           String numeroCNH,
                           Aluguel aluguel) {

}
