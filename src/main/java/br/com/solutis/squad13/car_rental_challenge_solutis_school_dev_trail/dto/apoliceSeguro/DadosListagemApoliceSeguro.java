package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados resumidos de uma apólice de seguro para listagem.")
public record DadosListagemApoliceSeguro(

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da franquia da apólice de seguro.", example = "R$ 500,00")
        BigDecimal valorFranquia,

        @Schema(description = "Indica se a apólice cobre danos a terceiros.", example = "true")
        Boolean protecaoTerceiro,

        @Schema(description = "Indica se a apólice cobre danos por causas naturais.", example = "false")
        Boolean protecaoCausasNaturais,

        @Schema(description = "Indica se a apólice cobre roubo do veículo.", example = "true")
        Boolean protecaoRoubo
) {
    public DadosListagemApoliceSeguro(ApoliceSeguro apoliceSeguro) {
        this(
                apoliceSeguro.getValorFranquia(),
                apoliceSeguro.getProtecaoTerceiro(),
                apoliceSeguro.getProtecaoCausasNaturais(),
                apoliceSeguro.getProtecaoRoubo()
        );
    }
}
