package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.modeloCarro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.fabricante.DadosCadastroFabricante;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para cadastro de um novo modelo de carro.")
public record DadosCadastroModeloCarro(

        @NotBlank(message = "A descrição do modelo é obrigatória.")
        @Schema(description = "Descrição do modelo do carro.", example = "Sedan")
        String descricao,

        @NotNull(message = "A categoria do modelo é obrigatória.")
        @Schema(description = "Categoria do modelo do carro.", example = "SEDAN")
        Categoria categoria,

        @Valid
        @NotNull(message = "Os dados do fabricante são obrigatórios.")
        @Schema(description = "Dados do fabricante do modelo.")
        DadosCadastroFabricante fabricante
) {
}
