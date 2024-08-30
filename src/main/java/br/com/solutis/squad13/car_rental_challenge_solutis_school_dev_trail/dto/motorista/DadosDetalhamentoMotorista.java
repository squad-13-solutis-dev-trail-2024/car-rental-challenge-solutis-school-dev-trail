package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Dados detalhados de um motorista, incluindo histórico de aluguéis e timestamps.")
public record DadosDetalhamentoMotorista(
        @Schema(description = "ID do motorista.", example = "1")
        Long id,

        @Schema(description = "Nome completo do motorista.", example = "João Silva")
        String nome,

        @Schema(description = "Endereço de email do motorista.", example = "joao.silva@example.com")
        String email,

        @JsonFormat(pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "CPF do motorista, formatado com pontos e hífen.", example = "123.456.789-00")
        String cpf,

        @JsonFormat(pattern = "\\d{11}", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Número da CNH do motorista, sem formatação.", example = "12345678901")
        String numeroCNH,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de nascimento do motorista.", example = "10/05/1990")
        LocalDate dataNascimento,

        @Schema(description = "Sexo do motorista.", example = "MASCULINO")
        String sexo,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data e hora de criação do registro do motorista.", example = "30/08/2024 15:40:05")
        LocalDateTime dataCreated,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data e hora da última atualização do registro do motorista.", example = "31/08/2024 10:20:30")
        LocalDateTime lastUpdated,

        @Schema(description = "Indica se o motorista está ativo (true) ou inativo (false).", example = "true")
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
