package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Dados resumidos de um motorista para listagem.")
public record DadosListagemMotorista(

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

        @Schema(description = "Indica se o motorista está ativo (true) ou inativo (false).", example = "true")
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
