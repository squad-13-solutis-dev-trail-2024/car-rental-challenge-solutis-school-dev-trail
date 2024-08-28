package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Sexo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public record PessoaDTO (Long id, String nome, String email, Date dataNascimento,
                         String cpf, Sexo Sexo){
}
