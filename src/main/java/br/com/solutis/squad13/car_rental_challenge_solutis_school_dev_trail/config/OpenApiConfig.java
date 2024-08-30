package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Rental API")
                        .version("1.0.0")
                        .description("API para gerenciamento de carros, alugueis e motoristas")
                        .contact(new Contact()
                                .name("Solutis")
                                .email("solutis@gmail.com.br")
                                .url("https://solutis.com.br"))
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .packagesToScan("br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller")
                .build();
    }
}
