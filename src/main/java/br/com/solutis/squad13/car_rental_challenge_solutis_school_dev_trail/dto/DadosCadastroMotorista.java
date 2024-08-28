package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record DadosCadastroMotorista(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser no passado")
        @JsonFormat(
                pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        LocalDate dataNascimento,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        @Column(unique = true)
        String cpf,

        @Email(message = "O email é inválido")
        @NotBlank(message = "O email não pode ser nulo")
        @Column(unique = true)
        String email,

        @NotBlank(message = "Número da CNH é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "Número da CNH inválido")
        @Column(unique = true)
        String numeroCNH,

        @NotNull(message = "Sexo é obrigatório")
        Sexo sexo
) {
}
