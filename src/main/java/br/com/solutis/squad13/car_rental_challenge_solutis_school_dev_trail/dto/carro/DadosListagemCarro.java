package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.acessorios.DadosListagemAcessorios;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Dados resumidos de um carro para listagem.")
public record DadosListagemCarro(

        @Schema(description = "Nome do modelo do carro.", example = "Corolla")
        String nome,

        @Schema(description = "Placa do carro.", example = "ABC-1234")
        String placa,

        @Schema(description = "Nome do modelo do carro.", example = "Sedan Compacto")
        String modeloCarro,

        boolean disponivel,

        @Schema(description = "Lista de nomes dos acessórios do carro.", example = "[\"Ar Condicionado\", \"GPS\", \"Câmera de Ré\"]")
        List<String> acessorios
){
    public DadosListagemCarro(Carro carro) {
        this(
                carro.getNome(),
                carro.getPlaca(),
                carro.getModelo().getDescricao(),
                carro.isDisponivel(),
                carro.getAcessorios()
                        .stream()
                        .map(DadosListagemAcessorios::new)
                        .map(DadosListagemAcessorios::nome)
                        .collect(Collectors.toList())
        );
    }
}