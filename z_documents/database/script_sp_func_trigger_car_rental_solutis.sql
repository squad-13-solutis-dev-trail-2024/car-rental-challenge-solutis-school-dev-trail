# DROP DATABASE IF EXISTS db_car_rental_solutis;
# CREATE DATABASE IF NOT EXISTS db_car_rental_solutis;
USE db_car_rental_solutis;

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
CREATE PROCEDURE atualizar_disponibilidade_carro(IN carro_id BIGINT UNSIGNED, IN novo_estado BOOLEAN)
BEGIN
    -- Atualiza o campo 'ativo' do carro na tabela 'tb_carro'.
    UPDATE tb_carro
    SET disponivel = novo_estado
    WHERE id = carro_id;
END //

DELIMITER ;

DELIMITER //
-- Função para calcular o valor total inicial
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

-- Função para calcular o valor total final
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

-- Trigger para calcular o valor total inicial antes da inserção
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

-- Trigger para calcular o valor total final antes da atualização
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


INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-01', DATE_ADD('2024-08-01', INTERVAL 10 DAY), DATE_ADD('2024-08-01', INTERVAL 15 DAY), NULL, 1, 1, 1, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-02', DATE_ADD('2024-08-02', INTERVAL 10 DAY), DATE_ADD('2024-08-02', INTERVAL 16 DAY), NULL, 2, 2, 2, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-03', DATE_ADD('2024-08-03', INTERVAL 10 DAY), DATE_ADD('2024-08-03', INTERVAL 17 DAY), NULL, 3, 3, 3, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-04', DATE_ADD('2024-08-04', INTERVAL 10 DAY), DATE_ADD('2024-08-04', INTERVAL 18 DAY), NULL, 4, 4, 4, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-05', DATE_ADD('2024-08-05', INTERVAL 10 DAY), DATE_ADD('2024-08-05', INTERVAL 19 DAY), NULL, 5, 5, 5, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-06', DATE_ADD('2024-08-06', INTERVAL 10 DAY), DATE_ADD('2024-08-06', INTERVAL 20 DAY), NULL, 6, 6, 6, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-07', DATE_ADD('2024-08-07', INTERVAL 10 DAY), DATE_ADD('2024-08-07', INTERVAL 11 DAY), NULL, 7, 7, 7, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-08', DATE_ADD('2024-08-08', INTERVAL 10 DAY), DATE_ADD('2024-08-08', INTERVAL 12 DAY), NULL, 8, 8, 8, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-09', DATE_ADD('2024-08-09', INTERVAL 10 DAY), DATE_ADD('2024-08-09', INTERVAL 13 DAY), NULL, 9, 9, 9, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-10', DATE_ADD('2024-08-10', INTERVAL 10 DAY), DATE_ADD('2024-08-10', INTERVAL 14 DAY), NULL, 10, 10, 10, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-11', DATE_ADD('2024-08-11', INTERVAL 7 DAY), DATE_ADD('2024-08-11', INTERVAL 17 DAY), NULL, 11, 11, 11, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-12', DATE_ADD('2024-08-12', INTERVAL 5 DAY), DATE_ADD('2024-08-12', INTERVAL 15 DAY), NULL, 12, 15, 12, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-13', DATE_ADD('2024-08-13', INTERVAL 9 DAY), DATE_ADD('2024-08-13', INTERVAL 19 DAY), NULL, 13, 18, 13, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-14', DATE_ADD('2024-08-14', INTERVAL 3 DAY), DATE_ADD('2024-08-14', INTERVAL 13 DAY), NULL, 14, 22, 14, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-15', DATE_ADD('2024-08-15', INTERVAL 6 DAY), DATE_ADD('2024-08-15', INTERVAL 16 DAY), NULL, 15, 25, 15, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-16', DATE_ADD('2024-08-16', INTERVAL 10 DAY), DATE_ADD('2024-08-16', INTERVAL 16 DAY), NULL, 16, 28, 16, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-17', DATE_ADD('2024-08-17', INTERVAL 4 DAY), DATE_ADD('2024-08-17', INTERVAL 14 DAY), NULL, 17, 31, 17, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-18', DATE_ADD('2024-08-18', INTERVAL 8 DAY), DATE_ADD('2024-08-18', INTERVAL 18 DAY), NULL, 18, 34, 18, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-19', DATE_ADD('2024-08-19', INTERVAL 12 DAY), DATE_ADD('2024-08-19', INTERVAL 13 DAY), NULL, 19, 2, 19, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-20', DATE_ADD('2024-08-20', INTERVAL 2 DAY), DATE_ADD('2024-08-20', INTERVAL 5 DAY), NULL, 20, 6, 20, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-21', DATE_ADD('2024-08-21', INTERVAL 7 DAY), DATE_ADD('2024-08-21', INTERVAL 17 DAY), NULL, 1, 9, 21, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-22', DATE_ADD('2024-08-22', INTERVAL 5 DAY), DATE_ADD('2024-08-22', INTERVAL 15 DAY), NULL, 2, 12, 22, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-23', DATE_ADD('2024-08-23', INTERVAL 9 DAY), DATE_ADD('2024-08-23', INTERVAL 19 DAY), '2024-09-01', 3, 16, 23, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-24', DATE_ADD('2024-08-24', INTERVAL 3 DAY), DATE_ADD('2024-08-24', INTERVAL 13 DAY), NULL, 4, 20, 24, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-25', DATE_ADD('2024-08-25', INTERVAL 6 DAY), DATE_ADD('2024-08-25', INTERVAL 16 DAY), '2024-08-31', 5, 23, 25, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-26', DATE_ADD('2024-08-26', INTERVAL 10 DAY), DATE_ADD('2024-08-26', INTERVAL 15 DAY), NULL, 6, 27, 26, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-27', DATE_ADD('2024-08-27', INTERVAL 4 DAY), DATE_ADD('2024-08-27', INTERVAL 14 DAY), NULL, 7, 30, 27, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-28', DATE_ADD('2024-08-28', INTERVAL 8 DAY), DATE_ADD('2024-08-28', INTERVAL 18 DAY), '2024-09-05', 8, 33, 28, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-29', DATE_ADD('2024-08-29', INTERVAL 12 DAY), DATE_ADD('2024-08-29', INTERVAL 18 DAY), NULL, 9, 4, 29, NULL, NULL);
INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id, carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final) VALUES ('2024-08-30', DATE_ADD('2024-08-30', INTERVAL 2 DAY), DATE_ADD('2024-08-30', INTERVAL 12 DAY), NULL, 10, 7, 30, NULL, NULL);

