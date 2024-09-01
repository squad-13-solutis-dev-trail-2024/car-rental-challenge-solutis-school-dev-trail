USE db_car_rental_solutis;

DROP TRIGGER IF EXISTS calcular_valor_inicial_before_insert;
DROP TRIGGER IF EXISTS calcular_valor_final_before_update;
DROP TRIGGER IF EXISTS atualizar_disponibilidade_after_insert;

DELIMITER //

-- Trigger para calcular o valorTotalParcial total inicial antes da inserção
--
-- Este trigger é acionado antes da inserção de um novo aluguel na tabela tb_aluguel.
-- Ele calcula o valorTotalParcial total inicial do aluguel utilizando a função
-- calcular_valor_total_inicial e atribui o resultado ao campo valorTotalInicial da nova linha.
CREATE TRIGGER IF NOT EXISTS calcular_valor_inicial_before_insert
    BEFORE INSERT
    ON tb_aluguel
    FOR EACH ROW
BEGIN
    SET NEW.valor_total_inicial =
            calcular_valor_total_inicial(NEW.carro_id,
                                         NEW.data_entrega,
                                         NEW.data_devolucao_prevista);
END//

-- Trigger para calcular o valorTotalParcial total final antes da atualização
--
-- Este trigger é acionado antes da atualização de um aluguel na tabela tb_aluguel.
-- Ele calcula o valorTotalParcial total final do aluguel utilizando a função
-- calcular_valor_total_final e atribui o resultado ao campo valorTotalFinal da linha atualizada.
CREATE TRIGGER IF NOT EXISTS calcular_valor_final_before_update
    BEFORE UPDATE
    ON tb_aluguel
    FOR EACH ROW
BEGIN
    SET NEW.valor_total_final = calcular_valor_total_final(NEW.carro_id,
                                                           NEW.data_entrega,
                                                           NEW.data_devolucao_efetiva,
                                                           NEW.data_devolucao_prevista);
END//
DELIMITER ;


DELIMITER //
-- Trigger para atualizar a disponibilidade do carro após a inserção de um aluguel
--
-- Este trigger é acionado após a inserção de um novo aluguel na tabela tb_aluguel.
-- Ele chama a stored procedure atualizar_disponibilidade_carro para definir o carro como
-- indisponível (ativo = FALSE).
CREATE TRIGGER atualizar_disponibilidade_after_insert
    AFTER INSERT
    ON tb_aluguel
    FOR EACH ROW
BEGIN
    -- Define o carro como indisponível (ativo = FALSE)
    CALL atualizar_disponibilidade_carro(NEW.carro_id, FALSE);
END //
DELIMITER ;

