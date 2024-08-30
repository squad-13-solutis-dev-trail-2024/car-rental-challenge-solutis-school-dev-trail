package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosAlugarCarro(
        LocalDate dataPedido,
        LocalDate dataEntrega,
        LocalDate dataDevolucao,
        BigDecimal valor,
        ApoliceSeguro apoliceSeguro,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String emailMotorista,

        Long idCarro
) {
}