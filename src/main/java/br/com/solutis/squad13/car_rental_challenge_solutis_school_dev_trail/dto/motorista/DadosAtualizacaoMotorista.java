package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record DadosAtualizacaoMotorista(

        @NotNull(message = "ID é obrigatório")
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Data de nascimento é obrigatória")
        @PastOrPresent(message = "A data de nascimento deve ser no passado ou presente")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Data de nascimento inválida")
        LocalDate dataNascimento,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        @Column(unique = true)
        String cpf,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
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