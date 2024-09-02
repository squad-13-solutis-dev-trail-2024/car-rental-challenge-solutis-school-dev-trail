DROP DATABASE IF EXISTS db_car_rental_solutis;
CREATE DATABASE IF NOT EXISTS db_car_rental_solutis;
USE db_car_rental_solutis;

SELECT * FROM tb_motorista;

DROP FUNCTION IF EXISTS calcular_valor_total_inicial;
DROP FUNCTION IF EXISTS calcular_valor_total_final;
DROP TRIGGER IF EXISTS calcular_valor_inicial_before_insert;
DROP TRIGGER IF EXISTS calcular_valor_final_before_update;
DROP TRIGGER IF EXISTS atualizar_disponibilidade_after_insert;
DROP PROCEDURE IF EXISTS atualizar_disponibilidade_carro;

DELIMITER //

-- Stored Procedure para atualizar a disponibilidade do carro
--
-- Esta stored procedure atualiza o estado de disponibilidade de um carro na tabela tb_carro.
-- Ela recebe o ID do carro e o novo estado de disponibilidade como parâmetros.
CREATE PROCEDURE IF NOT EXISTS atualizar_disponibilidade_carro(IN carro_id BIGINT UNSIGNED, IN novo_estado BOOLEAN)
BEGIN
    -- Atualiza o campo 'ativo' do carro na tabela 'tb_carro'.
    UPDATE tb_carro
    SET disponivel = novo_estado
    WHERE id = carro_id;
END //

DELIMITER ;

DELIMITER //
-- Função para calcular o valorTotalParcial total inicial
CREATE FUNCTION IF NOT EXISTS calcular_valor_total_inicial(carro_id BIGINT UNSIGNED,
                                                           data_entrega DATE,
                                                           data_devolucao_prevista DATE)
    RETURNS DECIMAL(10, 2)
    DETERMINISTIC
BEGIN
    DECLARE valor_diaria DECIMAL(10, 2);
    DECLARE quantidade_dias INT;

    SELECT c.valor_diaria
    INTO valor_diaria
    FROM tb_carro c
    WHERE c.id = carro_id;

    SELECT DATEDIFF(data_devolucao_prevista, data_entrega) + 1
    INTO quantidade_dias;

    RETURN valor_diaria * quantidade_dias;
END //

-- Função para calcular o valorTotalParcial total final
CREATE FUNCTION IF NOT EXISTS calcular_valor_total_final(carro_id BIGINT UNSIGNED,
                                                         data_entrega DATE,
                                                         data_devolucao_efetiva DATE,
                                                         data_devolucao_prevista DATE)
    RETURNS DECIMAL(10, 2)
    DETERMINISTIC
BEGIN
    DECLARE valor_diaria DECIMAL(10, 2);
    DECLARE quantidade_dias INT;

    SELECT c.valor_diaria
    INTO valor_diaria
    FROM tb_carro c
    WHERE c.id = carro_id;

    -- Se data_devolucao_efetiva for NULL, usa data_devolucao_prevista
    SELECT DATEDIFF(IFNULL(data_devolucao_efetiva, data_devolucao_prevista), data_entrega) + 1
    INTO quantidade_dias;

    RETURN valor_diaria * quantidade_dias;
END //

DELIMITER ;

DELIMITER //

-- Trigger para calcular o valorTotalParcial total inicial antes da inserção
CREATE TRIGGER IF NOT EXISTS calcular_valor_inicial_before_insert
    BEFORE INSERT
    ON tb_aluguel
    FOR EACH ROW
BEGIN
    SET NEW.valor_total_inicial =
            calcular_valor_total_inicial(NEW.carro_id,
                                         NEW.data_retirada,
                                         NEW.data_devolucao_prevista);
END//

-- Trigger para calcular o valorTotalParcial total final antes da atualização
CREATE TRIGGER IF NOT EXISTS calcular_valor_final_before_update
    BEFORE UPDATE
    ON tb_aluguel
    FOR EACH ROW
BEGIN
    SET NEW.valor_total_final = calcular_valor_total_final(NEW.carro_id,
                                                           NEW.data_retirada,
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
CREATE TRIGGER IF NOT EXISTS atualizar_disponibilidade_after_insert
    AFTER INSERT
    ON tb_aluguel
    FOR EACH ROW
BEGIN
    -- Define o carro como indisponível (ativo = FALSE)
    CALL atualizar_disponibilidade_carro(NEW.carro_id, FALSE);
END //
DELIMITER ;



INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-01', '2024-08-11', '2024-08-16', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-02', '2024-08-12', '2024-08-18', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 2, 2, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-03', '2024-08-13', '2024-08-20', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 3, 3, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-04', '2024-08-14', '2024-08-22', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 4, 4, 4);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-05', '2024-08-15', '2024-08-24', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 5, 5, 5);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-06', '2024-08-16', '2024-08-26', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 6, 6, 6);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-07', '2024-08-17', '2024-08-18', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 7, 7, 7);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-08', '2024-08-18', '2024-08-19', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 8, 8, 8);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-09', '2024-08-19', '2024-08-22', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 9, 9, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-10', '2024-08-20', '2024-08-24', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 10, 10, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-11', '2024-08-18', '2024-08-28', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 11, 11, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-12', '2024-08-17', '2024-08-27', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 12, 15, 4);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-13', '2024-08-22', '2024-09-01', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 13, 18, 5);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-14', '2024-08-17', '2024-08-27', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 14, 22, 6);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-15', '2024-08-21', '2024-08-31', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 15, 25, 7);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-16', '2024-08-26', '2024-09-01', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 16, 28, 8);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-17', '2024-08-21', '2024-08-31', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 17, 31, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-18', '2024-08-26', '2024-09-05', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 18, 34, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-19', '2024-08-31', '2024-09-01', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 19, 2, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-20', '2024-08-22', '2024-08-25', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 20, 6, 4);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-21', '2024-08-28', '2024-09-07', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 1, 9, 5);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-22', '2024-08-27', '2024-09-06', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 2, 12, 6);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-23', '2024-09-01', '2024-09-10', '2024-09-01', NULL, NULL, 'FINALIZADO', 'CARTAO_CREDITO', 'CONFIRMADO', '2024-08-25', NULL, 3, 16, 7);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-24', '2024-08-27', '2024-09-06', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 4, 20, 8);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-25', '2024-08-31', '2024-09-10', '2024-08-31', NULL, NULL, 'FINALIZADO', 'BOLETO', 'CONFIRMADO', '2024-08-27', NULL, 5, 23, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-26', '2024-09-05', '2024-09-10', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 6, 27, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-27', '2024-08-31', '2024-09-10', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 7, 30, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-28', '2024-09-05', '2024-09-16', '2024-09-05', NULL, NULL, 'FINALIZADO', 'PIX', 'CONFIRMADO', '2024-08-30', NULL, 8, 33, 4);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-29', '2024-09-10', '2024-09-16', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 9, 4, 5);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-08-30', '2024-09-01', '2024-09-10', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 10, 7, 6);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-01', '2024-09-05', '2024-09-15', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 11, 12, 7);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-02', '2024-09-08', '2024-09-18', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 12, 16, 8);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-03', '2024-09-10', '2024-09-20', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 13, 19, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-04', '2024-09-12', '2024-09-22', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 14, 23, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-05', '2024-09-15', '2024-09-25', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 15, 26, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-06', '2024-09-18', '2024-09-28', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 16, 29, 4);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-07', '2024-09-20', '2024-09-30', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 17, 32, 5);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-08', '2024-09-15', '2024-09-25', '2024-09-20', NULL, NULL, 'FINALIZADO', 'DINHEIRO', 'CONFIRMADO', '2024-09-15', NULL, 18, 1, 6);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-09', '2024-09-18', '2024-09-28', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 19, 5, 7);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-10', '2024-09-20', '2024-09-30', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 20, 8, 8);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-11', '2024-09-18', '2024-09-28', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 1, 11, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-12', '2024-09-20', '2024-09-30', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 2, 14, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-13', '2024-09-25', '2024-10-05', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 3, 17, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-14', '2024-09-20', '2024-09-30', '2024-09-28', NULL, NULL, 'FINALIZADO', 'CARTAO_DEBITO', 'CONFIRMADO', '2024-09-20', NULL, 4, 20, 4);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-15', '2024-09-27', '2024-10-07', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 5, 22, 5);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-16', '2024-09-25', '2024-10-05', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 6, 25, 6);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-17', '2024-09-28', '2024-10-08', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 7, 28, 7);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-18', '2024-09-25', '2024-10-05', '2024-10-02', NULL, NULL, 'FINALIZADO', 'CARTAO_CREDITO', 'CONFIRMADO', '2024-09-26', NULL, 8, 31, 8);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-19', '2024-10-01', '2024-10-11', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 9, 3, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-20', '2024-09-27', '2024-10-07', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 10, 6, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-21', '2024-09-28', '2024-10-08', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 11, 9, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-22', '2024-10-01', '2024-10-11', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 12, 13, 4);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-23', '2024-09-28', '2024-10-08', '2024-10-05', NULL, NULL, 'FINALIZADO', 'BOLETO', 'CONFIRMADO', '2024-09-29', NULL, 13, 15, 5);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-24', '2024-10-02', '2024-10-12', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 14, 18, 6);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-25', '2024-10-05', '2024-10-15', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 15, 21, 7);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-26', '2024-10-02', '2024-10-12', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 16, 24, 8);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-27', '2024-10-03', '2024-10-13', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 17, 27, 1);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-28', '2024-10-05', '2024-10-15', '2024-10-10', NULL, NULL, 'FINALIZADO', 'PIX', 'CONFIRMADO', '2024-10-06', NULL, 18, 30, 2);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-29', '2024-10-05', '2024-10-15', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 19, 32, 3);
INSERT INTO tb_aluguel (data_pedido, data_retirada, data_devolucao_prevista, data_devolucao_efetiva, valor_total_inicial, valor_total_final, status_aluguel, tipo_pagamento, status_pagamento, data_pagamento, data_cancelamento, motorista_id, carro_id, apolice_seguro_id) VALUES ('2024-09-30', '2024-10-08', '2024-10-18', NULL, NULL, NULL, 'INCOMPLETO', NULL, NULL, NULL, NULL, 20, 34, 4);
