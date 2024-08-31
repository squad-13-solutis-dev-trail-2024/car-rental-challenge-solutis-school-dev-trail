package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class CarRentalChallengeSolutisSchoolDevTrailApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalChallengeSolutisSchoolDevTrailApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            // Verifica se o arquivo de flag existe
            if (!new File("src/main/resources/.data_initialized").exists()) {
                try {
                    String filePath = "C:\\Users\\Pichau\\Desktop\\car-rental-challenge-solutis-school-dev-trail\\z_documents\\database\\seed\\script_car_rental_solutis_seed.sql";
                    String sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));

                    String[] sqlCommands = sqlScript.split(";");

                    for (String command : sqlCommands) {
                        if (!command.trim().isEmpty()) {
                            jdbcTemplate.execute(command);
                        }
                    }

                    // Cria o arquivo de flag após a execução bem-sucedida
                    new File("src/main/resources/.data_initialized").createNewFile();

                    System.out.println("Script SQL executado e banco de dados inicializado.");
                } catch (Exception e) {
                    System.err.println("Erro ao executar o script SQL: " + e.getMessage());
                }
            } else {
                System.out.println("Banco de dados já inicializado. Pulando execução do script SQL.");
            }
        };
    }
}