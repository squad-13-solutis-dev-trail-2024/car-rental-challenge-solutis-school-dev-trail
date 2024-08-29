package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.ADULTO;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.CNH;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosAtualizacaoMotorista(

        @NotNull(message = "ID é obrigatório")
        Long id,

        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        String nome,

        @JsonFormat(
                pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        @ADULTO(message = "Você não é maior de idade")
        LocalDate dataNascimento,

        @CPF(message = "CPF inválido")
        @Column(unique = true)
        String cpf,

        @Email(message = "E-mail inválido")
        @Column(unique = true)
        String email,

        @CNH(message = "Número da CNH inválido")
        @Column(unique = true)
        String numeroCNH,

        Sexo sexo,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
        LocalDateTime lastUpdated
) {
}