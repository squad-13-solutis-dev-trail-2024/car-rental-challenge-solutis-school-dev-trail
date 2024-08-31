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

        @Schema(description = "ID do carro.", example = "1")
        Long id,

        @Schema(description = "Nome do modelo do carro.", example = "Corolla")
        String nome,

        @Schema(description = "Placa do carro.", example = "ABC-1234")
        String placa,

        @Schema(description = "Chassi do carro.", example = "1HGBH41JXMN109186")
        String chassi,

        @Schema(description = "Cor do carro.", example = "Preto")
        String cor,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da diária do aluguel do carro.", example = "150.00")
        BigDecimal valorDiaria,

        @Schema(description = "Nome do modelo do carro.", example = "Sedan Compacto")
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
