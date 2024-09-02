package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.Placa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Schema(description = "Validador que verifica se a placa informada é válida.")
public class PlacaValidator implements ConstraintValidator<Placa, String> {

    public PlacaValidator() {}

    @Override
    public void initialize(Placa constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String placa, ConstraintValidatorContext constraintValidatorContext) {
        return true;
        // todo : implementar validação de chassi
    }
}
