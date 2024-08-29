package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.test;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class TesteDadosCadastroMotorista {

    public static void main(String[] args) {
        // Criar uma instância do DTO
        DadosCadastroMotorista motorista = new DadosCadastroMotorista(
                "254.204.230-71",
                LocalDate.of(4000, 5, 15),
                "Pedrão69",
                "@example.com",
                "12345678900",
                Sexo.MASCULINO,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Criar um validador
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Validar o DTO
        Set<ConstraintViolation<DadosCadastroMotorista>> violations = validator.validate(motorista);

        // Exibir os resultados da validação
        if (violations.isEmpty()) {
            System.out.println("Dados do motorista estão válidos.");
        } else {
            System.out.println("Dados do motorista possuem erros:");
            for (ConstraintViolation<DadosCadastroMotorista> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }
}
