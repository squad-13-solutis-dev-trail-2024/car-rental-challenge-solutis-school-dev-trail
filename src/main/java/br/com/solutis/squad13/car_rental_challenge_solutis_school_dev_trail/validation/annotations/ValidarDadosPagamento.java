package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.DadosPagamentoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DadosPagamentoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidarDadosPagamento {
    String message() default "Dados de pagamento inválidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
