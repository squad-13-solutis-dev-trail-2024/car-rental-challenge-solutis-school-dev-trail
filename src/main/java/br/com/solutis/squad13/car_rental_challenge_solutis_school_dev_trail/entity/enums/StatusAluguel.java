package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums;

public enum StatusAluguel {
    PENDENTE,      // Aluguel criado, aguardando confirmação de pagamento
    CONFIRMADO,    // Aluguel confirmado, pagamento realizado
    EM_ANDAMENTO,  // Aluguel em andamento, veículo entregue ao cliente
    FINALIZADO,    // Aluguel finalizado, veículo devolvido pelo cliente
    CANCELADO      // Aluguel cancelado pelo cliente ou pela locadora
}