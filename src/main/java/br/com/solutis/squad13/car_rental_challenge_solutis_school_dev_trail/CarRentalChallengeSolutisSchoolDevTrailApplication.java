package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = "br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail")
public class CarRentalChallengeSolutisSchoolDevTrailApplication {

    public static void main(String[] args) {
        run(CarRentalChallengeSolutisSchoolDevTrailApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            // Verifica se o arquivo de flag existe
            File flagFile = new File("src/main/resources/.data_initialized");
            if (!flagFile.exists()) {
                try {
                    String filePath = "C:\\Users\\vinic\\OneDrive\\Área de Trabalho\\car-rental-challenge-solutis-school-dev-trail\\z_documents\\database\\seed\\script_car_rental_solutis_seed.sql";
                    String sqlScript = new String(readAllBytes(get(filePath)));

                    String[] sqlCommands = sqlScript.split(";");

                    for (String command : sqlCommands) {
                        if (!command.trim().isEmpty()) {
                            jdbcTemplate.execute(command);
                        }
                    }

                    // Cria o arquivo de flag após a execução bem-sucedida
                    boolean fileCreated = flagFile.createNewFile();
                    if (fileCreated) {
                        System.out.println("Arquivo de flag criado: banco de dados inicializado.");
                    } else {
                        System.out.println("Arquivo de flag já existia.");
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao executar o script SQL: " + e.getMessage());
                }
            } else {
                System.out.println("Banco de dados já inicializado. Pulando execução do script SQL.");
            }
        };
    }
}
