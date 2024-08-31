package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.ADULTO;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.CNH;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Schema(description = "Dados para atualizar um motorista existente.")
public record DadosAtualizacaoMotorista(

        @NotNull(message = "ID é obrigatório")
        @Schema(description = "ID do motorista a ser atualizado.", example = "1")
        Long id,

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome completo do motorista.", example = "João Silva")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
        @ADULTO(message = "Você não é maior de idade")
        @Schema(description = "Data de nascimento do motorista.", example = "10/05/1990")
        LocalDate dataNascimento,

        @NotBlank(message = "O CPF é obrigatório")
        @CPF(message = "CPF inválido")
        @Schema(description = "CPF do motorista.", example = "123.456.789-00")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Schema(description = "Endereço de email do motorista.", example = "joao.silva@example.com")
        String email,

        @NotBlank(message = "O número da CNH é obrigatório")
        @CNH(message = "Número da CNH inválido")
        @Schema(description = "Número da CNH do motorista.", example = "12345678901")
        String numeroCNH,

        @NotNull(message = "O sexo é obrigatório")
        @Schema(description = "Sexo do motorista.", example = "MASCULINO")
        Sexo sexo
) {
}