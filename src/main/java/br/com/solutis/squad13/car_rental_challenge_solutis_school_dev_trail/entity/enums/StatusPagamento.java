package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do pagamento do aluguel.")
public enum StatusPagamento {
    @Schema(description = "Pagamento criado, aguardando confirmação.")
    PENDENTE,

    @Schema(description = "Pagamento confirmado.")
    CONFIRMADO,

    @Schema(description = "Pagamento cancelado.")
    CANCELADO
}