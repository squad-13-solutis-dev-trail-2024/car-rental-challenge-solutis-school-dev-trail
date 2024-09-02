package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.TipoPagamento;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.ValidarDadosPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;


@Schema(description = "Informações de pagamento referentes ao aluguel.")
@ValidarDadosPagamento(message = "Os dados fornecidos para o pagamento são inválidos.")
public record DadosPagamento(

        @NotNull(message = "O tipo de pagamento é obrigatório.")
        TipoPagamento tipoPagamento,

        @CreditCardNumber(message = "O número do cartão fornecido é inválido.")
        String numeroCartao,

        @Pattern(regexp = "^(0[1-9]|1[0-2])/?([0-9]{2})$", message = "A data de validade do cartão deve estar no formato MM/YY.")
        String validadeCartao,

        @Size(min = 3, max = 3, message = "O CVV deve conter exatamente 3 dígitos.")
        String cvv,

        @Size(min = 3, max = 100, message = "O nome do titular deve conter entre 3 e 100 caracteres.")
        String nomeTitular,

        @CPF(message = "O CPF fornecido para o titular é inválido.")
        String cpfTitular
) {
}