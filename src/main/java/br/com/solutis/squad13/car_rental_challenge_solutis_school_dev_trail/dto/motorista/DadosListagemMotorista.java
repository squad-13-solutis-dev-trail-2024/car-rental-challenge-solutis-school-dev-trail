package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;

public record DadosListagemMotorista(
        String nome,
        String email,
        String cpf,
        String numeroCNH
) {

    public DadosListagemMotorista(Motorista motorista) {
        this(
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getCpf(),
                motorista.getNumeroCNH()
        );
    }
}
