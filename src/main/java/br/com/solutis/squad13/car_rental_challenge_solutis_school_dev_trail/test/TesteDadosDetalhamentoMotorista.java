package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.test;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosDetalhamentoMotorista;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class TesteDadosDetalhamentoMotorista {
    public static void main(String[] args) {
        /*
                motorista.getId(),
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getCpf(),
                motorista.getNumeroCNH(),
                motorista.getDataNascimento().toString(),
                motorista.getSexo().name(),
                motorista.getDataCreated(),
                motorista.getLastUpdated()
         */

        // use os eexemplso do construtor acima para criar um motorista

        // Dados de Detalhamento do Motorista
        DadosDetalhamentoMotorista motorista1 = new DadosDetalhamentoMotorista(
                1L, // ID fictício
                "João da Silva",
                "joao.silva@example.com",
                "123.456.789-00",
                "12345678900", // Número da CNH
                LocalDate.of(1990, 1, 1), // Data de Nascimento
                "MASCULINO", // Sexo
                LocalDateTime.now(), // Data de Criação
                LocalDateTime.now(), // Data da Última Atualização,
                true,
                null
        );

        DadosDetalhamentoMotorista motorista2 = new DadosDetalhamentoMotorista(
                2L, // ID fictício
                "Maria Souza",
                "maria.souza@example.com",
                "987.654.321-99",
                "98765432199", // Número da CNH
                LocalDate.of(1990, 1, 1), // Data de Nascimento
                "FEMININO", // Sexo
                LocalDateTime.now(), // Data de Criação
                LocalDateTime.now(),
                true,
                null
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<DadosDetalhamentoMotorista>> violations = validator.validate(motorista1);

        if (violations.isEmpty()) {
            System.out.println("Detalhamento do motorista está válido.");
        } else {
            System.out.println("Detalhamento do motorista possui erros:");
            for (ConstraintViolation<DadosDetalhamentoMotorista> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }
}

