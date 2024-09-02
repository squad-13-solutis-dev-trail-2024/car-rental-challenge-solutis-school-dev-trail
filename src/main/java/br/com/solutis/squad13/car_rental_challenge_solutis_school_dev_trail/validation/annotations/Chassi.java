package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.ChassiValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
@Constraint(validatedBy = ChassiValidator.class)
@Schema(description = "Valida se o chassi informado é válido.")
public @interface Chassi {

    String message() default "Chassi inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

