package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.CNHValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CNHValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Schema(description = "Valida se um número de CNH é válido de acordo com o algoritmo de verificação brasileiro.")
public @interface CNH {

    String message() default "CNH inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

