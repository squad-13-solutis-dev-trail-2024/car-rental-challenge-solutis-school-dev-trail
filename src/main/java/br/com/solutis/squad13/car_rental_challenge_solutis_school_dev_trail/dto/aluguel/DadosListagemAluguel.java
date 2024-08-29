package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro.DadosApoliceSeguro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCarroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosListagemAluguel(
        @JsonFormat(
                pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        LocalDate dataPedido,
        @JsonFormat(
                pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        LocalDate dataEntrega,
        @JsonFormat(
                pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        LocalDate dataDevolucaoPrevista,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        BigDecimal valor,
        DadosCarroAluguel carro,
        DadosApoliceSeguro apolice
) {

    public DadosListagemAluguel(Aluguel aluguel) {
        this(
                aluguel.getDataPedido(),
                aluguel.getDataEntrega(),
                aluguel.getDataDevolucaoPrevista(),
                aluguel.getValor(), // BigDecimal valor agora está na posição correta
                new DadosCarroAluguel(aluguel.getCarro()),
                new DadosApoliceSeguro(aluguel.getApoliceSeguro())
        );
    }
}