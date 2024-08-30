package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.test;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.apoliceSeguro.DadosApoliceAluguelTeste;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteDadosAlugarCarro {
    public static void main(String[] args) {
        // Configuração do objeto ApoliceSeguro
        ApoliceSeguro apoliceSeguro = new ApoliceSeguro(
                1L, // ID fictício
                BigDecimal.valueOf(5000.00), // Valor da franquia
                true,  // Proteção a terceiros
                true,  // Proteção contra causas naturais
                false, // Proteção contra roubo
                null   // Aluguel pode ser null para este exemplo
        );

        // Criação do objeto DadosAlugarCarro
        DadosCadastroAluguel dados = new DadosCadastroAluguel(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 10),
                LocalDate.of(2024, 8, 12),
                LocalDate.of(2024, 8, 12),
                BigDecimal.valueOf(300.50),
                BigDecimal.valueOf(900.50),
                new DadosApoliceAluguelTeste(BigDecimal.valueOf(300.50),true,
                        true,true),
                "test@example.com",
                1L
        );

        // Verificação dos valores dos campos
        boolean testPassed = dados.dataPedido().equals(LocalDate.of(2024, 8, 1)) &&
                dados.dataEntrega().equals(LocalDate.of(2024, 8, 10)) &&
                dados.dataDevolucaoPrevista().equals(LocalDate.of(2024, 8, 12)) &&
                dados.valorTotalInicial().equals(BigDecimal.valueOf(300.50)) &&
                dados.valorTotalfinal().equals(BigDecimal.valueOf(900.50)) &&
                dados.apoliceSeguro().equals(apoliceSeguro) &&
                dados.emailMotorista().equals("test@example.com") &&
                dados.idCarro().equals(1L);

        // Resultado do teste
        if (testPassed) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed!");
        }

    }

}
