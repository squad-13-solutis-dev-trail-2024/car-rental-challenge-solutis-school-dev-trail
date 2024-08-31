package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.PlacaValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlacaValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Schema(description = "Valida se a placa informada é válida.")
public @interface Placa {

    String message() default "Placa inválida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

