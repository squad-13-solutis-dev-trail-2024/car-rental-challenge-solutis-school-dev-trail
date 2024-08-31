package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro.DadosCadastroApolice;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Dados mínimos para alugar um veículo.")
public record DadosCadastroAluguel(

        @NotNull(message = "A data de retirada é obrigatória.")
        @FutureOrPresent(message = "A data de retirada deve ser hoje ou uma data futura.")
        @Schema(description = "Data de retirada do veículo.", example = "01/09/2024")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDate dataRetirada,

        @NotNull(message = "A data de devolução prevista é obrigatória.")
        @Future(message = "A data de devolução prevista deve ser uma data futura.")
        @Schema(description = "Data de devolução prevista do veículo.", example = "10/09/2024")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDate dataDevolucaoPrevista,

        @Valid
        @NotNull(message = "Os dados da apólice de seguro são obrigatórios.")
        @Schema(description = "Dados da apólice de seguro do aluguel.")
        DadosCadastroApolice apoliceSeguro,

        @NotBlank(message = "O email do motorista é obrigatório.")
        @Email(message = "Email inválido.")
        @Schema(description = "Endereço de email do motorista.", example = "joao.silva@example.com")
        String emailMotorista,

        @NotNull(message = "O ID do carro é obrigatório.")
        @Schema(description = "ID do carro a ser alugado.", example = "1")
        Long idCarro
) {
}