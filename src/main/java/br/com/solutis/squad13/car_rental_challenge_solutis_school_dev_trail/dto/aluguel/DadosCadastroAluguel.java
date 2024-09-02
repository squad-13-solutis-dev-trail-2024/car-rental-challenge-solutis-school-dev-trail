package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados mínimos para alugar um veículo.")
public record DadosCadastroAluguel(

        @Email(message = "O e-mail do motorista é inválido.")
        String emailMotorista,

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

        @NotNull(message = "O ID do motorista é obrigatório.")
        @Schema(description = "ID do motorista que está alugando os carros.")
        Long motoristaId,

        @NotEmpty(message = "A lista de carros é obrigatória.")
        @Schema(description = "Lista de IDs dos carros a serem alugados.")
        List<Long> carrosIds
) {
}