package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("OpenApiConfig")
@Schema(description = "Configuração global do OpenAPI para a aplicação.")
public class OpenApiConfig {

    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API para Gerenciamento de Aluguel de Veículos")
                        .description("Solutis School Dev Trail - Nivelamento - 2024")
                        .version(apiVersion)
                        .license(new License().name("Licença").url("https://github.com/squad-13-solutis-dev-trail-2024/car-rental-challenge-solutis-school-dev-trail/blob/main/LICENSE"))
                        .contact(new Contact()
                                .name("Membros do SQUAD 13")
                                .url("https://github.com/orgs/squad-13-solutis-dev-trail-2024/people")
                        )
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}