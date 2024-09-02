package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.TipoPagamento;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.ValidarDadosPagamento;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;

@ValidarDadosPagamento(message = "Dados de pagamento inválidos.")
public record DadosPagamento(
        @NotNull(message = "O tipo de pagamento é obrigatório.")
        TipoPagamento tipoPagamento,
        String numeroCartao,
        String validadeCartao,
        String cvv,
        String nomeTitular,
        String cpfTitular
) {
}