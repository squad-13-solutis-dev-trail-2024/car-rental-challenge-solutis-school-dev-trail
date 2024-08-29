package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.acessorios.DadosListagemAcessorios;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;

import java.util.List;
import java.util.stream.Collectors;

public record DadosListagemCarro(
        String nome,
        String placa,
        String modeloCarro,
        List<String> acessorios
) {
    public DadosListagemCarro(Carro carro) {
        this(
                carro.getNome(),
                carro.getPlaca(),
                carro.getModelo().getDescricao(),
                carro.getAcessorios()
                        .stream()
                        .map(DadosListagemAcessorios::new)
                        .map(DadosListagemAcessorios::nome)
                        .collect(Collectors.toList())
        );
    }
}