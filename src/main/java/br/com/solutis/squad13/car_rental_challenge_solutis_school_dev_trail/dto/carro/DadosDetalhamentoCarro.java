package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Dados detalhados de um carro, incluindo histórico de aluguéis.")
public record DadosDetalhamentoCarro(

        @Schema(description = "ID do carro.")
        Long id,

        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @Schema(description = "Placa do carro.")
        String placa,

        @Schema(description = "Chassi do carro.")
        String chassi,

        @Schema(description = "Cor do carro.")
        String cor,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiaria,

        @Schema(description = "Nome do modelo do carro.")
        String modeloCarro,

        @Schema(description = "Lista de aluguéis realizados com este carro.")
        List<DadosListagemAluguelDeCarro> alugueis
) {
    public DadosDetalhamentoCarro(Carro carro) {
        this(
                carro.getId(),
                carro.getNome(),
                carro.getPlaca(),
                carro.getChassi(),
                carro.getCor(),
                carro.getValorDiaria(),
                carro.getModelo().getDescricao(),
                carro.getAlugueis()
                        .stream()
                        .map(DadosListagemAluguelDeCarro::new)
                        .collect(Collectors.toList())
        );
    }
}
