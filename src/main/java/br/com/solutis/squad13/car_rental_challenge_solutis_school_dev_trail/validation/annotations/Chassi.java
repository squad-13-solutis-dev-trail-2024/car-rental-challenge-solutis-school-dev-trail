package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.ChassiValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChassiValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Schema(description = "Valida se o chassi informado é válido.")
public @interface Chassi {

    String message() default "Chassi inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

