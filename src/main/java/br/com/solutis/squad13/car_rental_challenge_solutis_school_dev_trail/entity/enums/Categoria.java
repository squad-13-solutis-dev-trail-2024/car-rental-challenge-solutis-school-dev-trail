package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categorias de carros disponíveis para aluguel.")
public enum Categoria {
    @Schema(description = "Carro Hatch Compacto.")
    HATCH_COMPACTO,

    @Schema(description = "Carro Hatch Médio.")
    HATCH_MEDIO,

    @Schema(description = "Carro Sedan Compacto.")
    SEDAN_COMPACTO,

    @Schema(description = "Carro Sedan Médio.")
    SEDAN_MEDIO,

    @Schema(description = "Carro Sedan Grande.")
    SEDAN_GRANDE,

    @Schema(description = "Minivan.")
    MINIVAN,

    @Schema(description = "Carro Esportivo.")
    ESPORTIVO,

    @Schema(description = "Utilitário Comercial.")
    UTILITARIO_COMERCIAL
}