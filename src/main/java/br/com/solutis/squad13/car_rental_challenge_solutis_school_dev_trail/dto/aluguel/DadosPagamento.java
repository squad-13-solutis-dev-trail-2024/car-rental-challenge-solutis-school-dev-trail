package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.TipoPagamento;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;

public record DadosPagamento(

        @NotNull(message = "O tipo de pagamento é obrigatório.")
        TipoPagamento tipoPagamento,

        @NotBlank(message = "A chave Pix é obrigatória para pagamento por Pix.")
        @Pattern(regexp = "^[0-9a-zA-Z\\-+./:=@_]*$", message = "Chave Pix inválida.")
        String chavePix, // Para pagamento por Pix

        @NotBlank(message = "O número do cartão é obrigatório para pagamento por cartão.")
        @CreditCardNumber(message = "Número do cartão inválido.", ignoreNonDigitCharacters = true)
        String numeroCartao,

        @NotBlank(message = "A validade do cartão é obrigatória para pagamento por cartão.")
        @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Validade do cartão inválida (MM/AA).")
        String validadeCartao,

        @NotBlank(message = "O CVV é obrigatório para pagamento por cartão.")
        @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV inválido.") // Exemplo para 3 ou 4 dígitos
        String cvv,

        @NotBlank(message = "O nome do titular é obrigatório para pagamento por cartão.")
        String nomeTitular,

        @NotBlank(message = "O CPF do titular é obrigatório para pagamento por cartão.")
        @CPF(message = "CPF inválido.")
        String cpfTitular,

        @NotBlank(message = "O código de barras do boleto é obrigatório para pagamento por boleto.")
        String codigoBarrasBoleto // Para pagamento por Boleto
) {
}