package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.Chassi;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Schema(description = "Validador que verifica se o chassi informado é válido.")
public class ChassiValidator implements ConstraintValidator<Chassi, String> {

    public ChassiValidator() {}

    @Override
    public void initialize(Chassi constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return true;
        // todo : implementar validação de chassi
    }
}
