USE db_car_rental_solutis;

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