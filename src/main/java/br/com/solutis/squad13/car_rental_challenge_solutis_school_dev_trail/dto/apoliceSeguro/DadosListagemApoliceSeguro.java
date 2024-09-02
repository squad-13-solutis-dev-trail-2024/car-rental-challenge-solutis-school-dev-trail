package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro.calcularValorTotalApoliceSeguro;

@Schema(description = "Dados resumidos de uma apólice de seguro para listagem.")
public record DadosListagemApoliceSeguro(

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da franquia da apólice de seguro.")
        BigDecimal valorFranquia,

        @Schema(description = "Indica se a apólice cobre danos a terceiros.")
        Boolean protecaoTerceiro,

        @Schema(description = "Indica se a apólice cobre danos por causas naturais.")
        Boolean protecaoCausasNaturais,

        @Schema(description = "Indica se a apólice cobre roubo do veículo.")
        Boolean protecaoRoubo
) {
    public DadosListagemApoliceSeguro(ApoliceSeguro apoliceSeguro) {
        this(
                calcularValorTotalApoliceSeguro(
                        apoliceSeguro.getProtecaoTerceiro(),
                        apoliceSeguro.getProtecaoCausasNaturais(),
                        apoliceSeguro.getProtecaoRoubo()
                ),
                apoliceSeguro.getProtecaoTerceiro(),
                apoliceSeguro.getProtecaoCausasNaturais(),
                apoliceSeguro.getProtecaoRoubo()
        );
    }
}
