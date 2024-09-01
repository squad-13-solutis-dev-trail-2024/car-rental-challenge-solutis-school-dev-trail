package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Schema(description = "Dados da apólice de seguro do aluguel.")
public record DadosCadastroApolice(

        @Schema(description = "Valor da franquia da apólice de seguro.")
        @ColumnDefault("0.00")
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