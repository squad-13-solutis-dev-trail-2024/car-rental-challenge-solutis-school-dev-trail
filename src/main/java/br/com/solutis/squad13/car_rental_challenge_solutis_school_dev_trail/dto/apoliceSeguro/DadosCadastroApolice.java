package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Dados da apólice de seguro do aluguel.")
public record DadosCadastroApolice(

        @NotNull(message = "O valor da franquia é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valor da franquia deve ser maior que zero.")
        @Schema(description = "Valor da franquia da apólice de seguro.", example = "500.00")
        BigDecimal valorFranquia,

        @NotNull(message = "A informação sobre proteção contra danos a terceiros é obrigatória.")
        @Schema(description = "Indica se a apólice inclui proteção contra danos a terceiros.", example = "true")
        Boolean protecaoTerceiros,

        @NotNull(message = "A informação sobre proteção contra danos causados por causas naturais é obrigatória.")
        @Schema(description = "Indica se a apólice inclui proteção contra danos causados por causas naturais.", example = "false")
        Boolean protecaoCausasNaturais,

        @NotNull(message = "A informação sobre proteção contra roubo é obrigatória.")
        @Schema(description = "Indica se a apólice inclui proteção contra roubo.", example = "true")
        Boolean protecaoRoubo
) {
}