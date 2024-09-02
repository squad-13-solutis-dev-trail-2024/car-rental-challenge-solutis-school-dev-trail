package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.Adulto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

@Schema(description = "Validador que verifica se a data de nascimento corresponde a uma pessoa maior de idade.")
public class AdultoValidator implements ConstraintValidator<Adulto, LocalDate> {

    public AdultoValidator() {}

    @Override
    public void initialize(Adulto constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        if (dataNascimento == null) return true;
        return isMaiorDeIdade(dataNascimento);
    }

    private boolean isMaiorDeIdade(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears() >= 18;
    }
}