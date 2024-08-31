package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlacaValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Schema(description = "Valida se a placa informada é válida.")
public @interface PLACA {

    String message() default "Placa inválida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

@Schema(description = "Validador que verifica se a placa informada é válida.")
class PlacaValidator implements ConstraintValidator<PLACA, String> {

    @Override
    public void initialize(PLACA constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String placa, ConstraintValidatorContext constraintValidatorContext) {
        return false; // Implemente a lógica de validação da placa aqui
        // todo : implementar validação de chassi
    }
}
