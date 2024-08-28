package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Sexo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record MotoristaDTO(
        Long id,
        String nome,

        @Email(message = "E-mail inválido")
        String email,

        @DateTimeFormat(pattern = "dd/MM/yyyy")
        Date dataNascimento,

        @CPF(message = "CPF inválido")
        String cpf,

        @Valid
        Sexo Sexo,

        String numeroCNH,

        @Valid
        Aluguel aluguel
) {
}
