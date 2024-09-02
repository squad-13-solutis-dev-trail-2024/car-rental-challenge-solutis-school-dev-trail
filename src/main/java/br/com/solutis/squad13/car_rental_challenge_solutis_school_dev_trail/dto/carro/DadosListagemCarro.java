package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.acessorios.DadosListagemAcessorios;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

@Schema(description = "Dados resumidos de um carro para listagem.")
public record DadosListagemCarro(

        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @Schema(description = "Placa do carro.")
        String placa,

        @Schema(description = "Nome do modelo do carro.")
        String modeloCarro,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiaria,

        @Schema(description = "Indica se o carro está disponível para aluguel.")
        boolean disponivel,

        @Schema(description = "Lista de nomes dos acessórios do carro.")
        List<String> acessorios
) {
    public DadosListagemCarro(Carro carro) {
        this(
                carro.getNome(),
                carro.getPlaca(),
                carro.getModelo() != null ? carro.getModelo().getDescricao() : null,
                carro.getValorDiaria(),
                carro.isDisponivel(),
                carro.getAcessorios()
                        .stream()
                        .map(DadosListagemAcessorios::new)
                        .map(DadosListagemAcessorios::nome)
                        .collect(toList())
        );
    }

    public DadosListagemCarro(String s) {
        this(
                "O carro não possui acessórios",
                "O carro não possui acessórios",
                "O carro não possui acessórios",
                ZERO,
                false,
                List.of("O carro não possui acessórios")
        );
    }
}