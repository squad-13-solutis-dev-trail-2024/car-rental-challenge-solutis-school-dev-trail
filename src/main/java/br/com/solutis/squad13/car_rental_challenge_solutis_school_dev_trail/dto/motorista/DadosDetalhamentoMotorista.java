package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DadosDetalhamentoMotorista(

        Long id,
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
        String sexo,

        @JsonFormat(
                pattern = "dd/MM/yyyy HH:mm:ss",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        LocalDateTime dataCreated,

        @JsonFormat(
                pattern = "dd/MM/yyyy HH:mm:ss",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East",
                with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        )
        LocalDateTime lastUpdated,

        boolean ativo,

        List<DadosListagemAluguel> alugueis
) {
    public DadosDetalhamentoMotorista(Motorista motorista) {
        this(
                motorista.getId(),
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getCpf(),
                motorista.getNumeroCNH(),
                motorista.getDataNascimento(),
                motorista.getSexo().name(),
                motorista.getDataCreated(),
                motorista.getLastUpdated(),
                motorista.getAtivo(),
                motorista.getAlugueis()
                        .stream()
                        .map(DadosListagemAluguel::new)
                        .collect(Collectors.toList())
        );
    }
}
