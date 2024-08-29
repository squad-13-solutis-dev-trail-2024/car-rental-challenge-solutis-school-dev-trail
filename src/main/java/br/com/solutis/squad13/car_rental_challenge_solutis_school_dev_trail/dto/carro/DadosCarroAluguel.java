package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;

public record DadosCarroAluguel(
        String nome,
        String placa,
        String modeloCarro // Descrição do modelo do carro
) {
    public DadosCarroAluguel(Carro carro) {
        this(
                carro.getNome(),
                carro.getPlaca(),
                carro.getModelo().getDescricao()
        );
    }
}