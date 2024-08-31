package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.AdultoValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Schema(description = "Valida se a data de nascimento indica que a pessoa é maior de idade.")
public @interface Adulto {

    String message() default "A data de nascimento informada não corresponde a uma pessoa maior de idade.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

