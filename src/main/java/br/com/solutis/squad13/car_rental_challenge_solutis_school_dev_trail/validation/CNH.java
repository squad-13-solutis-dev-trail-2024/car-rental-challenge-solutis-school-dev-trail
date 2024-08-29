package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNH {
    String message() default "CNH inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class CNHValidator implements ConstraintValidator<CNH, String>{

    @Override
    public void initialize(CNH constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cnh, ConstraintValidatorContext context) {
        return cnh != null && !cnh.isEmpty();

        //Todo: validador de CNH
    }
}