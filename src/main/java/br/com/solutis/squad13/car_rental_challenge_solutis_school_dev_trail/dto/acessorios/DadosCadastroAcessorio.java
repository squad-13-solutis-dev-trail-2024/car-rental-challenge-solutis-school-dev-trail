package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.acessorios;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.DescricaoAcessorio;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para cadastro de um novo acessório.")
public record DadosCadastroAcessorio(

        @NotNull(message = "A descrição do acessório é obrigatória.")
        @Schema(description = "Descrição do acessório.", example = "AR_CONDICIONADO")
        DescricaoAcessorio descricaoAcessorio
) {
}