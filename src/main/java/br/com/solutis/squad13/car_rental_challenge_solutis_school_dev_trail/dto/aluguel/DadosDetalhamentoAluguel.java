package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro.DadosListagemApoliceSeguro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados detalhados de um aluguel.")
public record DadosDetalhamentoAluguel(

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data do pedido do aluguel.")
        LocalDate dataPedido,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de entrega do carro.")
        LocalDate dataEntrega,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data prevista para devolução do carro.")
        LocalDate dataDevolucaoPrevista,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data efetiva de devolução do carro.")
        LocalDate dataDevolucaoEfetiva,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor total inicial do aluguel, formatado como moeda brasileira (R$).")
        @Column(name = "valor_total_inicial", precision = 10, scale = 2)
        BigDecimal valorTotalInicial,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor total final do aluguel, formatado como moeda brasileira (R$).")
        BigDecimal valorTotalFinal,

        @Schema(description = "Dados resumidos do carro alugado.")
        DadosListagemCarro carro,

        @Schema(description = "Dados resumidos da apólice de seguro do aluguel.")
        DadosListagemApoliceSeguro apolice
) {
    public DadosDetalhamentoAluguel(Aluguel aluguel) {
        this(
                aluguel.getDataPedido(),
                aluguel.getDataRetirada(),
                aluguel.getDataDevolucaoPrevista(),
                aluguel.getDataDevolucaoEfetiva(),
                aluguel.getValorTotalInicial(),
                aluguel.getValorTotalFinal(),
                new DadosListagemCarro(aluguel.getCarro()),
                new DadosListagemApoliceSeguro(aluguel.getApoliceSeguro())
        );
    }
}