package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.Period;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultoValidator.class)
public @interface ADULTO {
    String message() default "A data de nascimento informada não corresponde a uma pessoa maior de idade.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class AdultoValidator implements ConstraintValidator<ADULTO, LocalDate> {

    @Override
    public void initialize(ADULTO constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        if (dataNascimento == null) return true;
        return isMaiorDeIdade(dataNascimento);
    }

    // Metodo para verificar se a data de nascimento corresponde a uma pessoa maior de idade
    private boolean isMaiorDeIdade(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears() >= 18;
    }
}