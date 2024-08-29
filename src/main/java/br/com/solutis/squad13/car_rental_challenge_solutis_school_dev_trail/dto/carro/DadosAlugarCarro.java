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
    // O construtor padrão do record já faz a validação das anotações @NotBlank e @Email
    // portanto, não é necessário incluir a validação explícita aqui.

    // Caso precise de lógica adicional, você pode adicionar métodos ao record, mas
    // não é possível adicionar setters, pois records são imutáveis.
}