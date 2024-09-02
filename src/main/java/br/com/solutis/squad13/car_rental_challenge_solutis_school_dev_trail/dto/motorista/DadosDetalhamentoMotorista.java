package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados detalhados de um motorista, incluindo histórico de aluguéis e timestamps.")
public record DadosDetalhamentoMotorista(

        @Schema(description = "ID do motorista.")
        Long id,

        @Schema(description = "Nome completo do motorista." )
        String nome,

        @Schema(description = "Endereço de email do motorista.")
        String email,

        @JsonFormat(pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "CPF do motorista, formatado com pontos e hífen.")
        String cpf,

        @JsonFormat(pattern = "\\d{11}", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Número da CNH do motorista, sem formatação.")
        String numeroCNH,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de nascimento do motorista.")
        LocalDate dataNascimento,

        @Schema(description = "Sexo do motorista.", example = "MASCULINO")
        String sexo,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data e hora de criação do registro do motorista.")
        LocalDateTime dataCreated,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data e hora da última atualização do registro do motorista.")
        LocalDateTime lastUpdated,

        @Schema(description = "Indica se o motorista está ativo (true) ou inativo (false).")
        boolean ativo,

        @Schema(description = "Lista de aluguéis realizados pelo motorista.")
        List<DadosListagemAluguel> alugueis
){
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
