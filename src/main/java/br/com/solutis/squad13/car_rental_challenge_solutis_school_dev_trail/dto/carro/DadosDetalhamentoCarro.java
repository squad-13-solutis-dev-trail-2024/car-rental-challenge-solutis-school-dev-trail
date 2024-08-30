package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record DadosDetalhamentoCarro(
        Long id,
        String nome,
        String placa,
        String chassi,
        String cor,
        BigDecimal valorDiaria,
        String modeloCarro,
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
