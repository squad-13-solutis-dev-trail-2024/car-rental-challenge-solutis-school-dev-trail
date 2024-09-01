package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;

@Schema(description = "Dados mínimos para alugar um veículo.")
public record DadosCadastroAluguel(

        @NotNull(message = "A data de retirada é obrigatória.")
        @FutureOrPresent(message = "A data de retirada deve ser hoje ou uma data futura.")
        @Schema(description = "Data de retirada do veículo.")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDate dataRetirada,

        @NotNull(message = "A data de devolução prevista é obrigatória.")
        @Future(message = "A data de devolução prevista deve ser uma data futura.")
        @Schema(description = "Data de devolução prevista do veículo.")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDate dataDevolucaoPrevista,

        @NotNull(message = "Os dados da apólice de seguro são obrigatórios.")
        @Schema(description = "Dados da apólice de seguro do aluguel.")
        ApoliceSeguro apoliceSeguro,

        @NotBlank(message = "O email do motorista é obrigatório.")
        @Email(message = "Email inválido.")
        @Schema(description = "Endereço de email do motorista.")
        String emailMotorista,

        @NotNull(message = "O ID do carro é obrigatório.")
        @Schema(description = "ID do carro a ser alugado.")
        Long idCarro,

        @CreationTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
        @Schema(description = "Data e hora de criação do registro do motorista.", accessMode = READ_ONLY)
        LocalDateTime dataCreated,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        @Schema(description = "Data e hora da última atualização do registro do motorista.", accessMode = READ_WRITE)
        LocalDateTime lastUpdated
) {
}