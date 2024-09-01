USE db_car_rental_solutis;

DROP FUNCTION IF EXISTS calcular_valor_total_inicial;
DROP FUNCTION IF EXISTS calcular_valor_total_final;

DELIMITER //
-- Função para calcular o valorTotalParcial total inicial
--
-- Esta função calcula o valorTotalParcial total inicial de um aluguel com base na data de entrega
-- e na data de devolução prevista.
--
-- Parâmetros:
--   carro_id: ID do carro alugado.
--   data_entrega: Data em que o carro foi entregue ao cliente.
--   data_devolucao_prevista: Data prevista para a devolução do carro.
--
-- Retorno:
--   Valor total inicial do aluguel (DECIMAL(10,2)).
CREATE FUNCTION IF NOT EXISTS calcular_valor_total_inicial(carro_id BIGINT UNSIGNED,
                                                           data_entrega DATE,
                                                           data_devolucao_prevista DATE)
    RETURNS DECIMAL(10, 2)
    DETERMINISTIC
BEGIN
    DECLARE valor_diaria DECIMAL(10, 2);
    DECLARE quantidade_dias INT;

    -- Obter o valorTotalParcial da diária do carro a partir do ID do carro.
    SELECT c.valor_diaria
    INTO valor_diaria
    FROM tb_carro c
    WHERE c.id = carro_id;

    -- Calcular a quantidade de dias entre a data de entrega e a data de devolução prevista.
    -- Adicionar 1 para incluir o dia da entrega no cálculo.
    SELECT DATEDIFF(data_devolucao_prevista, data_entrega) + 1
    INTO quantidade_dias;

    -- Calcular e retornar o valorTotalParcial total inicial do aluguel.
    RETURN valor_diaria * quantidade_dias;
END //

-- Função para calcular o valorTotalParcial total final
--
-- Esta função calcula o valorTotalParcial total final de um aluguel com base na data de entrega
-- e na data de devolução efetiva. Se a data de devolução efetiva for NULL, a função
-- utiliza a data de devolução prevista.
--
-- Parâmetros:
--   carro_id: ID do carro alugado.
--   data_entrega: Data em que o carro foi entregue ao cliente.
--   data_devolucao_efetiva: Data efetiva da devolução do carro (pode ser NULL).
--   data_devolucao_prevista: Data prevista para a devolução do carro.
--
-- Retorno:
--   Valor total final do aluguel (DECIMAL(10,2)).
CREATE FUNCTION IF NOT EXISTS calcular_valor_total_final(carro_id BIGINT UNSIGNED,
                                                         data_entrega DATE,
                                                         data_devolucao_efetiva DATE,
                                                         data_devolucao_prevista DATE)
    RETURNS DECIMAL(10, 2)
    DETERMINISTIC
BEGIN
    DECLARE valor_diaria DECIMAL(10, 2);
    DECLARE quantidade_dias INT;

    -- Obter o valorTotalParcial da diária do carro a partir do ID do carro.
    SELECT c.valor_diaria
    INTO valor_diaria
    FROM tb_carro c
    WHERE c.id = carro_id;

    -- Calcular a quantidade de dias entre a data de entrega e a data de devolução.
    -- Se data_devolucao_efetiva for NULL, utiliza a data_devolucao_prevista.
    -- Adicionar 1 para incluir o dia da entrega no cálculo.
    SELECT DATEDIFF(IFNULL(data_devolucao_efetiva, data_devolucao_prevista), data_entrega) + 1
    INTO quantidade_dias;

    -- Calcular e retornar o valorTotalParcial total final do aluguel.
    RETURN valor_diaria * quantidade_dias;
END //

DELIMITER ;
