package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.DadosPagamentoValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DadosPagamentoValidator.class)
@Schema(description = "Valida se os dados de pagamento são válidos.")
public @interface ValidarDadosPagamento {

    String message() default "Dados de pagamento inválidos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
