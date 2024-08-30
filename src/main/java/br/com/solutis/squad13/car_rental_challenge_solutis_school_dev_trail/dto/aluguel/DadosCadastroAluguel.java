package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro.DadosApoliceAluguelTeste;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Dados mínimo para alugar o veículo")
public record DadosCadastroAluguel(

        @JsonFormat(pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East")
        @Column(nullable = false)
        LocalDate dataPedido,

        @JsonFormat(pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East")
        @Column(nullable = false)
        LocalDate dataEntrega,

        @JsonFormat(pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East")
        @Column(nullable = false)
        LocalDate dataDevolucaoPrevista,

        @JsonFormat(pattern = "dd/MM/yyyy",
                shape = JsonFormat.Shape.STRING,
                locale = "pt-BR",
                timezone = "Brazil/East")
        @Column(nullable = false)
        LocalDate dataDevolucaoEfetiva,

        @DecimalMin(value = "0.0",
                inclusive = false,
                message = "O valor total inicial não pode ser menor que zero")
        BigDecimal valorTotalInicial,

        @DecimalMin(value = "0.0",
                inclusive = false,
                message = "O valor diário deve ser maior que zero")
        BigDecimal valorTotalfinal,

        DadosApoliceAluguelTeste apoliceSeguro,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String emailMotorista,

        @NotNull
        Long idCarro
) {
}