package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.ValidarDadosPagamento;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DadosPagamentoValidator implements ConstraintValidator<ValidarDadosPagamento, DadosPagamento> {

    @Override
    public void initialize(ValidarDadosPagamento constraintAnnotation) {}

    @Override
    public boolean isValid(DadosPagamento dadosPagamento, ConstraintValidatorContext context) {
        if (dadosPagamento.tipoPagamento() == null) {
            return true; // Tipo de pagamento pode ser null, a validação do tipo é feita separadamente
        }

        boolean isValid = true;

        switch (dadosPagamento.tipoPagamento()) {
            case CARTAO_CREDITO:
            case CARTAO_DEBITO:
                isValid = dadosPagamento.numeroCartao() != null
                        && dadosPagamento.validadeCartao() != null
                        && dadosPagamento.cvv() != null
                        && dadosPagamento.nomeTitular() != null
                        && dadosPagamento.cpfTitular() != null;
                break;
            case DINHEIRO:
            case BOLETO:
            case PIX:
                // Esses tipos de pagamento não exigem os dados do cartão
                break;
        }

        if (!isValid) {
            context.buildConstraintViolationWithTemplate("Campos obrigatórios não preenchidos para o tipo de pagamento " + dadosPagamento.tipoPagamento())
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
