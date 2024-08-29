package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.test;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class TesteDadosAtualizacaoMotorista {

    public static void main(String[] args) {
        // Criar uma instância do DTO
        DadosAtualizacaoMotorista motorista = new DadosAtualizacaoMotorista(
                1L,
                "Ana Costa",
                LocalDate.of(1990, 8, 12),
                "987.654.321-00",
                "ana.costa@example.com",
                "12345678900",
                Sexo.FEMININO,
                LocalDateTime.now()
        );

        // Criar um validador
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Validar o DTO
        Set<ConstraintViolation<DadosAtualizacaoMotorista>> violations = validator.validate(motorista);

        // Exibir os resultados da validação
        if (violations.isEmpty()) {
            System.out.println("Dados de atualização do motorista estão válidos.");
        } else {
            System.out.println("Dados de atualização do motorista possuem erros:");
            for (ConstraintViolation<DadosAtualizacaoMotorista> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }
}
