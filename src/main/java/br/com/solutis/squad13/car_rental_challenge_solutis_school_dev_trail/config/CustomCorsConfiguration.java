package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

/**
 * Configura o CORS (Cross-Origin Resource Sharing) para a aplicação.
 * <p>
 * Permite requisições de origens específicas, como "<a href="http://localhost:3000">...</a>" e "<a href="http://localhost:8080">...</a>",
 * com métodos HTTP específicos (GET, POST, PUT, DELETE, OPTIONS), headers e credenciais.
 *
 * @see CorsConfiguration
 * @see UrlBasedCorsConfigurationSource
 * @see CorsFilter
 */
@Configuration("CustomCorsConfiguration")
@Schema(description = "Configuração global do CORS para a aplicação.")
public class CustomCorsConfiguration {

    /**
     * Cria um bean `CorsFilter` com as configurações de CORS definidas.
     *
     * @return Um novo filtro CORS com as configurações especificadas.
     */
    @Bean
    @Schema(description = "Cria e configura o filtro CORS.")
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}