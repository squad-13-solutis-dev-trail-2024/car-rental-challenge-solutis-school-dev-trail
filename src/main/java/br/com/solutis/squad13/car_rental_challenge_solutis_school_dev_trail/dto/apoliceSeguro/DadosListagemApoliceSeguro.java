package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

public record DadosListagemApoliceSeguro(
        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        BigDecimal valorFranquia,
        Boolean protecaoTerceiro,
        Boolean protecaoCausasNaturais,
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
