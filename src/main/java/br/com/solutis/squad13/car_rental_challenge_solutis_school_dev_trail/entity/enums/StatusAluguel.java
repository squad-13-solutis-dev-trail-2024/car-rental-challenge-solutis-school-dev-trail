package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do aluguel do veículo.")
public enum StatusAluguel {
    @Schema(description = "Aluguel criado, aguardando confirmação de pagamento.")
    INCOMPLETO,

    @Schema(description = "Aluguel em andamento, veículo entregue ao cliente.")
    EM_ANDAMENTO,

    @Schema(description = "Aluguel finalizado, veículo devolvido pelo cliente.")
    FINALIZADO,

    @Schema(description = "Aluguel cancelado pelo cliente ou pela locadora.")
    CANCELADO
}