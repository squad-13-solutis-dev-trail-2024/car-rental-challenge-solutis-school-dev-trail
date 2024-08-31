package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChassiValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Schema(description = "Valida se o chassi informado é válido.")
public @interface CHASSI {

    String message() default "Chassi inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


@Schema(description = "Validador que verifica se o chassi informado é válido.")
class ChassiValidator implements ConstraintValidator<CHASSI, String> {

    @Override
    public void initialize(CHASSI constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
        // todo : implementar validação de chassi
    }
}
