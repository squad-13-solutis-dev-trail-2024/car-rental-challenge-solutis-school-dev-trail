package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de pagamento disponíveis para os aluguéis.")
public enum TipoPagamento {
    @Schema(description = "Pagamento com cartão de crédito.")
    CARTAO_CREDITO,

    @Schema(description = "Pagamento com cartão de débito.")
    CARTAO_DEBITO,

    @Schema(description = "Pagamento em dinheiro.")
    DINHEIRO,

    @Schema(description = "Pagamento por boleto bancário.")
    BOLETO,

    @Schema(description = "Pagamento via Pix.")
    PIX
}