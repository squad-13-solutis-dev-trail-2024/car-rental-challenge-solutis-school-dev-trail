package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

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
            try {
                String filePath = "C:\\Users\\Pichau\\Desktop\\car-rental-challenge-solutis-school-dev-trail\\z_documents\\database\\script_car_rental_solutis_seed.sql";
                String sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));

                // Divide o script em comandos individuais
                String[] sqlCommands = sqlScript.split(";");

                // Executa cada comando separadamente
                for (String command : sqlCommands) {
                    if (!command.trim().isEmpty()) {
                        jdbcTemplate.execute(command);
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao executar o script SQL: " + e.getMessage());
            }
        };
    }
}