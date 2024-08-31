package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultoValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Schema(description = "Valida se a data de nascimento indica que a pessoa é maior de idade.")
public @interface ADULTO {

    String message() default "A data de nascimento informada não corresponde a uma pessoa maior de idade.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

@Schema(description = "Validador que verifica se a data de nascimento corresponde a uma pessoa maior de idade.")
class AdultoValidator implements ConstraintValidator<ADULTO, LocalDate> {

    public AdultoValidator() {
    }

    @Override
    public void initialize(ADULTO constraintAnnotation) {
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