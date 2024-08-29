package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.acessorios;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;

import static java.lang.String.valueOf;

public record DadosListagemAcessorios(
        String nome
) {
    public DadosListagemAcessorios(Acessorio acessorio) {
        this(valueOf(acessorio.getDescricao()));
    }
}