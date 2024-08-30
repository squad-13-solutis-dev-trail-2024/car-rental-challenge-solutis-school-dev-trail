package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.ADULTO;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.CNH;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Dados necessários para cadastrar um novo motorista.")
public record DadosCadastroMotorista(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome completo do motorista.", example = "João Silva")
        String nome,

        @NotNull(message = "Data de nascimento é obrigatória")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @ADULTO(message = "Você não é maior de idade")
        @Schema(description = "Data de nascimento do motorista.", example = "10/05/1990")
        LocalDate dataNascimento,

        @NotBlank(message = "CPF é obrigatório")
        @Column(unique = true)
        @CPF(message = "CPF inválido")
        @Schema(description = "CPF do motorista.", example = "123.456.789-00")
        String cpf,

        @NotBlank(message = "O email não pode ser nulo")
        @Email(message = "O email é inválido")
        @Column(unique = true)
        @Schema(description = "Endereço de email do motorista.", example = "joao.silva@example.com")
        String email,

        @NotBlank(message = "Número da CNH é obrigatório")
        @CNH(message = "Número da CNH inválido")
        @Column(unique = true)
        @Schema(description = "Número da CNH do motorista.", example = "12345678901")
        String numeroCNH,

        @NotNull(message = "Sexo é obrigatório")
        @Schema(description = "Sexo do motorista.", example = "MASCULINO")
        Sexo sexo,

        @CreationTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
        @Schema(description = "Data e hora de criação do registro do motorista.", example = "30/08/2024 15:40:05", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime dataCreated,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        @Schema(description = "Data e hora da última atualização do registro do motorista.", example = "31/08/2024 10:20:30", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime lastUpdated
) {
}
