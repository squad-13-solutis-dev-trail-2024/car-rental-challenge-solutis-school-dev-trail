package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.ADULTO;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.CNH;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosCadastroMotorista(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        String nome,

        @NotNull(message = "Data de nascimento é obrigatória")
        @JsonFormat(
                pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        @ADULTO(message = "Você não é maior de idade")
        LocalDate dataNascimento,

        @NotBlank(message = "CPF é obrigatório")
        @Column(unique = true)
        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "O email não pode ser nulo")
        @Email(message = "O email é inválido")
        @Column(unique = true)
        String email,

        @NotBlank(message = "Número da CNH é obrigatório")
        @CNH(message = "Número da CNH inválido")
        @Column(unique = true)
        String numeroCNH,

        @NotNull(message = "Sexo é obrigatório")
        Sexo sexo,

        @CreationTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
                updatable = false)
        LocalDateTime dataCreated,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        LocalDateTime lastUpdated
) {
    // Construtor para validar eventuais dados duplicados no cadastro e na atualização
    public DadosCadastroMotorista(String cpf, String email, String numeroCNH) {
        this(null, null, cpf, email, numeroCNH, null, null, null);
    }
}
