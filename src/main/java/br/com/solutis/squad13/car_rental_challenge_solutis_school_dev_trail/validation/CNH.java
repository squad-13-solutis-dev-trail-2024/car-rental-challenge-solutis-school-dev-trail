package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CNHValidator.class)
@Schema(description = "Valida se um número de CNH é válido de acordo com o algoritmo de verificação brasileiro.")
public @interface CNH {

    String message() default "CNH inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

@Schema(description = "Validador que verifica a validade de um número de CNH.")
class CNHValidator implements ConstraintValidator<CNH, String> {

    @Override
    public void initialize(CNH constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cnh, ConstraintValidatorContext context) {
        if (cnh == null) return true; // Permite CNH nula (caso de atualização, por exemplo)

        // Passo 1: Calcular o primeiro dígito verificador
        int soma1 = 0;
        for (int i = 0; i < 9; i++)
            soma1 += Character.getNumericValue(cnh.charAt(i)) * (9 - i);
        int primeiroDigito = soma1 % 11;
        if (primeiroDigito == 10) primeiroDigito = 0;

        // Passo 2: Calcular o segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 9; i++) soma2 += Character.getNumericValue(cnh.charAt(i)) * (1 + i);

        int segundoDigito = soma2 % 11;
        if (segundoDigito == 10) segundoDigito = 0;

        // Passo 3: Verificar se os dígitos calculados correspondem aos dígitos verificadores
        return primeiroDigito == Character.getNumericValue(cnh.charAt(9)) &&
                segundoDigito == Character.getNumericValue(cnh.charAt(10));
    }
}