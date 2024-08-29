package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record DadosListagemMotorista(
        String nome,
        String email,

        @JsonFormat(
                pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        String cpf,

        @JsonFormat(
                pattern = "\\d{11}",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        String numeroCNH,

        @JsonFormat(
                pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        LocalDate dataNascimento,

        boolean ativo
) {

    public DadosListagemMotorista(Motorista motorista) {
        this(
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getCpf(),
                motorista.getNumeroCNH(),
                motorista.getDataNascimento(),
                motorista.getAtivo()
        );
    }
}
