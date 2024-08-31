package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.fabricante;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cadastro de um novo fabricante de carros.")
public record DadosCadastroFabricante(

        @NotBlank(message = "O nome do fabricante é obrigatório.")
        @Schema(description = "Nome do fabricante do carro.", example = "Toyota")
        String nome
) {
}