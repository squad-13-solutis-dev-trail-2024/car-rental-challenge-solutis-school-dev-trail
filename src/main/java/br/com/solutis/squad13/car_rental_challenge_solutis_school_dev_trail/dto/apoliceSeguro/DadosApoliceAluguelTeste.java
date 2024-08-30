package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema
public record DadosApoliceAluguelTeste(

        @Schema(description = "Valor da franquia da apólice de seguro.", example = "500.00")
        BigDecimal valorFranquia,

        @Schema(description = "Indica se a apólice inclui proteção contra danos a terceiros.", example = "true")
        Boolean protecaoTerceiros,

        @Schema(description = "Indica se a apólice inclui proteção contra danos causados por causas naturais.", example = "false")
        Boolean protecaoCausasNaturais,

        @Schema(description = "Indica se a apólice inclui proteção contra roubo.", example = "true")
        Boolean protecaoRoubo
) {
}