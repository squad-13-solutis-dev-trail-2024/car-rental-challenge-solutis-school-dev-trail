package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro.DadosListagemApoliceSeguro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.math.BigDecimal.ZERO;
import static java.time.LocalDate.now;

@Schema(description = "Dados resumidos de um aluguel para listagem.")
public record DadosListagemAluguel(

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data do pedido do aluguel.")
        LocalDate dataPedido,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de entrega do carro.")
        LocalDate dataEntrega,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data prevista para devolução do carro.")
        LocalDate dataDevolucaoPrevista,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)

        @Schema(description = "Valor total final do aluguel, formatado como moeda brasileira (R$).", example = "R$ 1.500,00")
        BigDecimal valorTotalPrevisto,
        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor total final do aluguel, formatado como moeda brasileira (R$).", example = "R$ 1.500,00")
        BigDecimal valorTotalPago,

        @Schema(description = "Dados resumidos do carro alugado.")
        DadosListagemCarro carro,

        @Schema(description = "Dados resumidos da apólice de seguro do aluguel.")
        DadosListagemApoliceSeguro apolice
) {

    public DadosListagemAluguel(Aluguel aluguel) {
        this(
                aluguel.getDataPedido(),
                aluguel.getDataRetirada(),
                aluguel.getDataDevolucaoPrevista(),
                aluguel.getValorTotalInicial(),
                aluguel.getValorTotalFinal(),
                new DadosListagemCarro(aluguel.getCarro()),
                new DadosListagemApoliceSeguro(aluguel.getApoliceSeguro())
        );
    }

    public DadosListagemAluguel(String oUsuarioNaoPossuiLocacoes) {
        this(
                now(),
                now(),
                now(),
                ZERO,
                ZERO,
                new DadosListagemCarro(""),
                new DadosListagemApoliceSeguro("")
        );
    }
}