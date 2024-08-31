package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro.DadosListagemApoliceSeguro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Dados resumidos de um aluguel para a listagem de carros.")
public record DadosListagemAluguelDeCarro(

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data do pedido do aluguel.", example = "01/08/2024")
        LocalDate dataPedido,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de entrega do carro.", example = "11/08/2024")
        LocalDate dataEntrega,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data prevista para devolução do carro.", example = "16/08/2024")
        LocalDate dataDevolucaoPrevista,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor total final do aluguel, formatado como moeda brasileira (R$).", example = "R$ 1.500,00")
        BigDecimal valor,

        @Schema(description = "Dados resumidos da apólice de seguro associada ao aluguel.")
        DadosListagemApoliceSeguro apolice
){

    public DadosListagemAluguelDeCarro(Aluguel aluguel) {
        this(
                aluguel.getDataPedido(),
                aluguel.getDataEntrega(),
                aluguel.getDataDevolucaoPrevista(),
                aluguel.getValorTotalFinal(),
                new DadosListagemApoliceSeguro(aluguel.getApoliceSeguro())
        );
    }
}
