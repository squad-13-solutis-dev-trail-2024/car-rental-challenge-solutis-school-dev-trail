DROP DATABASE IF EXISTS db_car_rental_solutis;
CREATE DATABASE IF NOT EXISTS db_car_rental_solutis;
USE db_car_rental_solutis;

DROP FUNCTION IF EXISTS calcular_valor_total_inicial;
DROP FUNCTION IF EXISTS calcular_valor_total_final;
DROP TRIGGER IF EXISTS calcular_valor_inicial_before_insert;
DROP TRIGGER IF EXISTS calcular_valor_final_before_update;
DROP TRIGGER IF EXISTS atualizar_disponibilidade_after_insert;
DROP PROCEDURE IF EXISTS atualizar_disponibilidade_carro;

INSERT INTO tb_acessorio (descricao)
VALUES ('GPS'),
       ('AR_CONDICIONADO'),
       ('CADEIRA_INFANTIL'),
       ('TETO_SOLAR'),
       ('CAMERA_RE'),
       ('SENSOR_ESTACIONAMENTO'),
       ('BLUETOOTH'),
       ('ALARM'),
       ('RODAS_LIGA_LEVE'),
       ('FAROL_DE_MILHA'),
       ('AQUECIMENTO_BANCOS'),
       ('VOLANTE_MULTIFUNCIONAL'),
       ('BANCOS_COURO'),
       ('NAVEGACAO_INTEGRADA'),
       ('FREIOS_ABS'),
       ('AIRBAGS'),
       ('CONTROLE_ESTABILIDADE'),
       ('SUSPENSAO_ESPORTIVA'),
       ('RETROVISORES_ELETRICOS'),
       ('SOM_PREMIUM'),
       ('CHAVE_CARTAO'),
       ('ASSISTENTE_PARTIDA_RAMPA'),
       ('PARA_BRISA_DESEMBACADOR'),
       ('PORTA_MALAS_AUTOMATICO'),
       ('CARREGADOR_WIRELESS'),
       ('ASSISTENTE_FRENAGEM_EMERGENCIA');

-- quero contar quantos itens tem na tabela acessorio
SELECT COUNT(*)
FROM tb_acessorio;


INSERT INTO tb_apolice_seguro (valor_franquia, protecao_terceiro, protecao_causas_naturais, protecao_roubo)
VALUES (500.00, TRUE, FALSE, TRUE),  -- Apólice 1
       (300.00, FALSE, TRUE, FALSE), -- Apólice 2
       (750.00, TRUE, TRUE, TRUE),   -- Apólice 3
       (200.00, FALSE, FALSE, TRUE), -- Apólice 4
       (400.00, TRUE, FALSE, FALSE), -- Apólice 5
       (650.00, FALSE, TRUE, TRUE),  -- Apólice 6
       (550.00, TRUE, TRUE, FALSE),  -- Apólice 7
       (300.00, FALSE, FALSE, FALSE),-- Apólice 8
       (700.00, TRUE, TRUE, TRUE),   -- Apólice 9
       (250.00, FALSE, TRUE, TRUE),
       (450.00, TRUE, TRUE, FALSE),
       (600.00, FALSE, FALSE, TRUE),
       (350.00, TRUE, FALSE, TRUE),
       (700.00, FALSE, TRUE, FALSE),
       (500.00, TRUE, TRUE, TRUE),
       (250.00, FALSE, FALSE, TRUE),
       (550.00, TRUE, FALSE, FALSE),
       (400.00, FALSE, TRUE, TRUE),
       (650.00, TRUE, TRUE, FALSE),
       (300.00, FALSE, FALSE, FALSE),
       (750.00, TRUE, FALSE, TRUE),
       (200.00, FALSE, TRUE, FALSE),
       (400.00, TRUE, TRUE, TRUE),
       (600.00, FALSE, FALSE, TRUE),
       (350.00, TRUE, FALSE, FALSE),
       (700.00, FALSE, TRUE, TRUE),
       (550.00, TRUE, TRUE, FALSE),
       (250.00, FALSE, FALSE, FALSE),
       (650.00, TRUE, FALSE, TRUE),
       (300.00, FALSE, TRUE, FALSE);


INSERT INTO tb_fabricante (nome)
VALUES ('Toyota'),
       ('Honda'),
       ('Hyundai'),
       ('Jeep'),
       ('Volkswagen'),
       ('Ford'),
       ('Chevrolet'),
       ('Nissan'),
       ('Kia'),
       ('Mazda'),
       ('Subaru'),
       ('BMW'),
       ('Mercedes-Benz'),
       ('Audi'),
       ('Volkswagen'),
       ('Peugeot'),
       ('Renault'),
       ('Fiat'),
       ('Land Rover'),
       ('Porsche'),
       ('Mitsubishi'),
       ('Chrysler'),
       ('Buick'),
       ('GMC'),
       ('Cadillac'),
       ('Jaguar'),
       ('Lexus'),
       ('Infiniti'),
       ('Acura'),
       ('Alfa Romeo'),
       ('Tesla'),
       ('Rover'),
       ('Mini'),
       ('Lincoln'),
       ('Saturn'),
       ('Saab'),
       ('Dodge'),
       ('Mazda'),
       ('Opel'),
       ('Seat'),
       ('Skoda');

INSERT INTO tb_pessoa (nome, email, data_nascimento, cpf, sexo)
VALUES ('João Silva', 'joao.silva@email.com', '1990-05-10', '244.722.840-65', 'MASCULINO'),
       ('Maria Oliveira', 'maria.oliveira@email.com', '1985-12-20', '626.407.030-05', 'FEMININO'),
       ('Pedro Santos', 'pedro.santos1@email.com', '1995-08-15', '424.472.920-82', 'MASCULINO'),
       ('Pedro Santos', 'pedro.santos2@email.com', '1995-08-15', '311.233.360-83', 'MASCULINO'),
       ('Pedro Santos', 'pedro.santos3@email.com', '1995-08-15', '318.298.570-10', 'MASCULINO'),
       ('Pedro Santos', 'pedro.santos4@email.com', '1995-08-15', '116.708.530-20', 'MASCULINO'),
       ('Ana Souza', 'ana.souza@email.com', '1992-03-25', '106.637.590-99', 'FEMININO'),
       ('Carlos Pereira', 'carlos.pereira@email.com', '1988-09-12', '967.258.780-59', 'MASCULINO'),
       ('Juliana Rocha', 'juliana.rocha@email.com', '1997-06-08', '538.878.550-51', 'FEMININO'),
       ('Ricardo Alves', 'ricardo.alves@email.com', '1995-11-02', '738.398.920-15', 'MASCULINO'),
       ('Fernanda Lima', 'fernanda.lima@email.com', '1987-04-18', '257.075.030-13', 'FEMININO'),
       ('Marcelo Castro', 'marcelo.castro@email.com', '1993-07-30', '279.698.210-65', 'MASCULINO'),
       ('Patricia Silva', 'patricia.silva@email.com', '1990-01-15', '684.675.850-05', 'FEMININO'),
       ('Gustavo Cunha', 'gustavo.cunha@email.com', '1989-12-28', '151.096.210-71', 'MASCULINO'),
       ('Isabela Santos', 'isabela.santos@email.com', '1996-09-05', '375.200.790-74', 'FEMININO'),
       ('Lucas Oliveira', 'lucas.oliveira@email.com', '1994-05-22', '576.460.840-69', 'MASCULINO'),
       ('Amanda Ferreira', 'amanda.ferreira@email.com', '1991-08-11', '441.589.140-35', 'FEMININO'),
       ('Bruno Rodrigues', 'bruno.rodrigues@email.com', '1998-03-07', '967.178.240-00', 'MASCULINO'),
       ('Camila Gomes', 'camila.gomes@email.com', '1986-10-19', '700.432.460-52', 'FEMININO'),
       ('Vinícius dos Santos Andrade', 'vinicius_andrade2010@hotmail.com', '2001-12-06', '447.841.608-76', 'MASCULINO');

INSERT INTO tb_motorista (pessoa_id, numerocnh)
VALUES (1, '69539881188'),
       (2, '25603561673'),
       (3, '71235649396'),
       (4, '89970324002'),
       (5, '77350257314'),
       (6, '50620772310'),
       (7, '58615174396'),
       (8, '19206078416'),
       (9, '27999265962'),
       (10, '52126715528'),
       (11, '94315675339'),
       (12, '98512579243'),
       (13, '69417970651'),
       (14, '53813874646'),
       (15, '65054459401'),
       (16, '45226044971'),
       (17, '17234508761'),
       (18, '24726224861'),
       (19, '25480246897'),
       (20, '07493612633');

INSERT INTO tb_modelo_carro (descricao, fabricante_id, categoria)
VALUES ('Corolla', 1, 'SEDAN_COMPACTO'),
       ('Civic', 2, 'SEDAN_MEDIO'),
       ('Elantra', 3, 'SEDAN_MEDIO'),
       ('Compass', 4, 'UTILITARIO_COMERCIAL'),
       ('Golf', 5, 'HATCH_MEDIO'),
       ('Mustang', 6, 'ESPORTIVO'),
       ('Cruze', 7, 'SEDAN_MEDIO'),
       ('Altima', 8, 'SEDAN_GRANDE'),
       ('Soul', 9, 'UTILITARIO_COMERCIAL'),
       ('MX-5 Miata', 10, 'ESPORTIVO'),
       ('Outback', 11, 'UTILITARIO_COMERCIAL'),
       ('X3', 12, 'UTILITARIO_COMERCIAL'),
       ('C-Class', 13, 'SEDAN_MEDIO'),
       ('A4', 14, 'SEDAN_MEDIO'),
       ('308', 15, 'HATCH_MEDIO'),
       ('Clio', 16, 'HATCH_COMPACTO'),
       ('Punto', 17, 'HATCH_COMPACTO'),
       ('Range Rover', 18, 'UTILITARIO_COMERCIAL'),
       ('911', 19, 'ESPORTIVO'),
       ('Outlander', 20, 'UTILITARIO_COMERCIAL'),
       ('300C', 21, 'SEDAN_GRANDE'),
       ('Enclave', 22, 'UTILITARIO_COMERCIAL'),
       ('Yukon', 23, 'UTILITARIO_COMERCIAL'),
       ('XT5', 24, 'UTILITARIO_COMERCIAL'),
       ('F-Pace', 25, 'UTILITARIO_COMERCIAL'),
       ('RX', 26, 'UTILITARIO_COMERCIAL'),
       ('Q7', 27, 'UTILITARIO_COMERCIAL'),
       ('Giulia', 28, 'SEDAN_MEDIO'),
       ('Model 3', 29, 'SEDAN_COMPACTO'),
       ('Freelander', 30, 'UTILITARIO_COMERCIAL'),
       ('Clubman', 31, 'MINIVAN'),
       ('Navigator', 32, 'UTILITARIO_COMERCIAL'),
       ('Astra', 33, 'HATCH_MEDIO'),
       ('Ibiza', 34, 'HATCH_COMPACTO'),
       ('Octavia', 35, 'SEDAN_MEDIO');

INSERT INTO tb_carro (nome, placa, chassi, cor, disponivel, valor_diaria, modelo_carro_id)
VALUES ('Corolla', 'ABC1234', '1HGBH41JXMN109186', 'Preto', TRUE, 150.00, 1),
       ('Civic', 'XYZ5678', '2HGBH41JXMN109187', 'Branco', TRUE, 180.00, 2),
       ('Elantra', 'LMN9012', '3HGBH41JXMN109188', 'Prata', TRUE, 170.00, 3),
       ('Compass', 'PQR3456', '4HGBH41JXMN109189', 'Azul', TRUE, 200.00, 4),
       ('Golf', 'STU7890', '5HGBH41JXMN109190', 'Verde', TRUE, 160.00, 5),
       ('Mustang', 'VWX2345', '6HGBH41JXMN109191', 'Vermelho', TRUE, 220.00, 6),
       ('Cruze', 'YZA6789', '7HGBH41JXMN109192', 'Cinza', TRUE, 175.00, 7),
       ('Altima', 'BCD0123', '8HGBH41JXMN109193', 'Bege', TRUE, 190.00, 8),
       ('Soul', 'EFG4567', '9HGBH41JXMN109194', 'Amarelo', TRUE, 165.00, 9),
       ('MX-5 Miata', 'HIJ8901', '0HGBH41JXMN109195', 'Rosa', TRUE, 210.00, 10),
       ('Outback', 'KLM2345', '1HGBH41JXMN109196', 'Marrom', TRUE, 205.00, 11),
       ('X3', 'NOP6789', '2HGBH41JXMN109197', 'Preto', TRUE, 230.00, 12),
       ('C-Class', 'QRS0123', '3HGBH41JXMN109198', 'Prata', TRUE, 200.00, 13),
       ('A4', 'TUV4567', '4HGBH41JXMN109199', 'Branco', TRUE, 220.00, 14),
       ('308', 'WXY8901', '5HGBH41JXMN109200', 'Azul', TRUE, 185.00, 15),
       ('Clio', 'ZAB2345', '6HGBH41JXMN109201', 'Verde', TRUE, 155.00, 16),
       ('Punto', 'CDE6789', '7HGBH41JXMN109202', 'Cinza', TRUE, 160.00, 17),
       ('Range Rover', 'FGH9012', '8HGBH41JXMN109203', 'Bege', TRUE, 250.00, 18),
       ('911', 'IJK3456', '9HGBH41JXMN109204', 'Vermelho', TRUE, 300.00, 19),
       ('Outlander', 'LMN7890', '0HGBH41JXMN109205', 'Prata', TRUE, 210.00, 20),
       ('300C', 'OPQ1234', '1HGBH41JXMN109206', 'Preto', TRUE, 220.00, 21),
       ('Enclave', 'RST5678', '2HGBH41JXMN109207', 'Azul', TRUE, 230.00, 22),
       ('Yukon', 'UVW9012', '3HGBH41JXMN109208', 'Marrom', TRUE, 240.00, 23),
       ('XT5', 'XYZ2345', '4HGBH41JXMN109209', 'Bege', TRUE, 225.00, 24),
       ('RX', 'ABC6789', '5HGBH41JXMN109210', 'Cinza', TRUE, 210.00, 25),
       ('Q7', 'DEF9012', '6HGBH41JXMN109211', 'Branco', TRUE, 250.00, 26),
       ('Giulia', 'GHI3456', '7HGBH41JXMN109212', 'Preto', TRUE, 205.00, 27),
       ('Model 3', 'JKL6789', '8HGBH41JXMN109213', 'Prata', TRUE, 190.00, 28),
       ('Freelander', 'MNO9012', '9HGBH41JXMN109214', 'Verde', TRUE, 210.00, 29),
       ('Clubman', 'PQR3456', '0HGBH41JXMN109215', 'Amarelo', TRUE, 195.00, 30),
       ('Navigator', 'STU6789', '1HGBH41JXMN109216', 'Cinza', TRUE, 230.00, 31),
       ('Astra', 'VWX9012', '2HGBH41JXMN109217', 'Vermelho', TRUE, 170.00, 32),
       ('Ibiza', 'YZA2345', '3HGBH41JXMN109218', 'Branco', TRUE, 160.00, 33),
       ('Octavia', 'BCD6789', '4HGBH41JXMN109219', 'Azul', TRUE, 185.00, 34),
       ('Tiguan', 'EFG9012', '5HGBH41JXMN109220', 'Preto', TRUE, 200.00, 35);

INSERT INTO tb_carro_acessorio (id_carro, id_acessorio)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (2, 2),
       (2, 4),
       (2, 6),
       (2, 7),
       (2, 8),
       (2, 9),
       (3, 1),
       (3, 3),
       (3, 5),
       (3, 7),
       (3, 9),
       (3, 10),
       (4, 2),
       (4, 6),
       (4, 8),
       (4, 10),
       (4, 11),
       (5, 1),
       (5, 4),
       (5, 9),
       (5, 10),
       (5, 12),
       (6, 3),
       (6, 7),
       (6, 8),
       (6, 9),
       (6, 10),
       (6, 13),
       (7, 1),
       (7, 5),
       (7, 6),
       (7, 10),
       (7, 14),
       (7, 15),
       (8, 2),
       (8, 4),
       (8, 7),
       (8, 8),
       (8, 16),
       (8, 17),
       (9, 1),
       (9, 3),
       (9, 6),
       (9, 9),
       (9, 18),
       (9, 19),
       (10, 2),
       (10, 5),
       (10, 7),
       (10, 8),
       (10, 20),
       (10, 21),
       (11, 3),
       (11, 6),
       (11, 10),
       (11, 22),
       (11, 23),
       (12, 1),
       (12, 4),
       (12, 9),
       (12, 24),
       (12, 25),
       (13, 2),
       (13, 5),
       (13, 7),
       (13, 26),
       (13, 1),
       (14, 1),
       (14, 6),
       (14, 8),
       (14, 2),
       (14, 3),
       (15, 3),
       (15, 4),
       (15, 9),
       (15, 4),
       (15, 5),
       (16, 2),
       (16, 5),
       (16, 6),
       (16, 7),
       (16, 8),
       (17, 1),
       (17, 3),
       (17, 8),
       (17, 9),
       (17, 10),
       (17, 11),
       (18, 2),
       (18, 5),
       (18, 7),
       (18, 10),
       (18, 12),
       (18, 13),
       (19, 1),
       (19, 4),
       (19, 6),
       (19, 14),
       (19, 15),
       (20, 3),
       (20, 5),
       (20, 8),
       (20, 16),
       (20, 17),
       (21, 2),
       (21, 4),
       (21, 7),
       (21, 18),
       (21, 19),
       (22, 1),
       (22, 6),
       (22, 9),
       (22, 20),
       (22, 21),
       (23, 2),
       (23, 3),
       (23, 5),
       (23, 22),
       (23, 23),
       (24, 1),
       (24, 8),
       (24, 10),
       (24, 24),
       (24, 25),
       (25, 2),
       (25, 4),
       (25, 7),
       (25, 26),
       (25, 1),
       (26, 1),
       (26, 5),
       (26, 9),
       (26, 2),
       (26, 3),
       (27, 3),
       (27, 6),
       (27, 8),
       (27, 4),
       (27, 5),
       (28, 1),
       (28, 4),
       (28, 7),
       (28, 6),
       (28, 8),
       (29, 2),
       (29, 5),
       (29, 10),
       (29, 11),
       (30, 3),
       (30, 6),
       (30, 9),
       (30, 12),
       (30, 13),
       (31, 2),
       (31, 7),
       (31, 10),
       (31, 14),
       (32, 1),
       (32, 4),
       (32, 9),
       (32, 15),
       (32, 16),
       (33, 2),
       (33, 7),
       (33, 10),
       (33, 17),
       (33, 18),
       (34, 1),
       (34, 3),
       (34, 8),
       (34, 19),
       (34, 20),
       (35, 2),
       (35, 5),
       (35, 9),
       (35, 21),
       (35, 22);



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


INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, motorista_id,
                        carro_id, apolice_seguro_id, valor_total_inicial, valor_total_final)
VALUES ('2024-08-01', DATE_ADD('2024-08-01', INTERVAL 10 DAY), DATE_ADD('2024-08-01', INTERVAL 15 DAY), NULL, 1,
        1, 1, NULL, NULL),
       ('2024-08-02', DATE_ADD('2024-08-02', INTERVAL 10 DAY), DATE_ADD('2024-08-02', INTERVAL 16 DAY), NULL, 2,
        2, 2, NULL, NULL),
       ('2024-08-03', DATE_ADD('2024-08-03', INTERVAL 10 DAY), DATE_ADD('2024-08-03', INTERVAL 17 DAY), NULL, 3,
        3, 3, NULL, NULL),
       ('2024-08-04', DATE_ADD('2024-08-04', INTERVAL 10 DAY), DATE_ADD('2024-08-04', INTERVAL 18 DAY), NULL, 4,
        4, 4, NULL, NULL),
       ('2024-08-05', DATE_ADD('2024-08-05', INTERVAL 10 DAY), DATE_ADD('2024-08-05', INTERVAL 19 DAY), NULL, 5,
        5, 5, NULL, NULL),
       ('2024-08-06', DATE_ADD('2024-08-06', INTERVAL 10 DAY), DATE_ADD('2024-08-06', INTERVAL 20 DAY), NULL, 6,
        6, 6, NULL, NULL),
       ('2024-08-07', DATE_ADD('2024-08-07', INTERVAL 10 DAY), DATE_ADD('2024-08-07', INTERVAL 11 DAY), NULL, 7,
        7, 7, NULL, NULL),
       ('2024-08-08', DATE_ADD('2024-08-08', INTERVAL 10 DAY), DATE_ADD('2024-08-08', INTERVAL 12 DAY), NULL, 8,
        8, 8, NULL, NULL),
       ('2024-08-09', DATE_ADD('2024-08-09', INTERVAL 10 DAY), DATE_ADD('2024-08-09', INTERVAL 13 DAY), NULL, 9,
        9, 9, NULL, NULL),
       ('2024-08-10', DATE_ADD('2024-08-10', INTERVAL 10 DAY), DATE_ADD('2024-08-10', INTERVAL 14 DAY), NULL, 10,
        10, 10, NULL, NULL),
       ('2024-08-11', DATE_ADD('2024-08-11', INTERVAL 7 DAY), DATE_ADD('2024-08-11', INTERVAL 17 DAY), NULL, 11,
        11, 11, NULL, NULL),
       ('2024-08-12', DATE_ADD('2024-08-12', INTERVAL 5 DAY), DATE_ADD('2024-08-12', INTERVAL 15 DAY), NULL, 12,
        15, 12, NULL, NULL),
       ('2024-08-13', DATE_ADD('2024-08-13', INTERVAL 9 DAY), DATE_ADD('2024-08-13', INTERVAL 19 DAY), NULL, 13,
        18, 13, NULL, NULL),
       ('2024-08-14', DATE_ADD('2024-08-14', INTERVAL 3 DAY), DATE_ADD('2024-08-14', INTERVAL 13 DAY), NULL, 14,
        22, 14, NULL, NULL),
       ('2024-08-15', DATE_ADD('2024-08-15', INTERVAL 6 DAY), DATE_ADD('2024-08-15', INTERVAL 16 DAY), NULL, 15,
        25, 15, NULL, NULL),
       ('2024-08-16', DATE_ADD('2024-08-16', INTERVAL 10 DAY), DATE_ADD('2024-08-16', INTERVAL 16 DAY), NULL, 16,
        28, 16, NULL, NULL),
       ('2024-08-17', DATE_ADD('2024-08-17', INTERVAL 4 DAY), DATE_ADD('2024-08-17', INTERVAL 14 DAY), NULL, 17,
        31, 17, NULL, NULL),
       ('2024-08-18', DATE_ADD('2024-08-18', INTERVAL 8 DAY), DATE_ADD('2024-08-18', INTERVAL 18 DAY), NULL, 18,
        34, 18, NULL, NULL),
       ('2024-08-19', DATE_ADD('2024-08-19', INTERVAL 12 DAY), DATE_ADD('2024-08-19', INTERVAL 13 DAY), NULL, 19,
        2, 19, NULL, NULL),
       ('2024-08-20', DATE_ADD('2024-08-20', INTERVAL 2 DAY), DATE_ADD('2024-08-20', INTERVAL 5 DAY), NULL, 20, 6,
        20, NULL, NULL),
       ('2024-08-21', DATE_ADD('2024-08-21', INTERVAL 7 DAY), DATE_ADD('2024-08-21', INTERVAL 17 DAY), NULL, 1, 9,
        21, NULL, NULL),
       ('2024-08-22', DATE_ADD('2024-08-22', INTERVAL 5 DAY), DATE_ADD('2024-08-22', INTERVAL 15 DAY), NULL, 2, 12,
        22, NULL, NULL),
       ('2024-08-23', DATE_ADD('2024-08-23', INTERVAL 9 DAY), DATE_ADD('2024-08-23', INTERVAL 19 DAY), '2024-09-01',
        3, 16, 23, NULL, NULL),
       ('2024-08-24', DATE_ADD('2024-08-24', INTERVAL 3 DAY), DATE_ADD('2024-08-24', INTERVAL 13 DAY), NULL, 4, 20,
        24, NULL, NULL),
       ('2024-08-25', DATE_ADD('2024-08-25', INTERVAL 6 DAY), DATE_ADD('2024-08-25', INTERVAL 16 DAY), '2024-08-31',
        5, 23, 25, NULL, NULL),
       ('2024-08-26', DATE_ADD('2024-08-26', INTERVAL 10 DAY), DATE_ADD('2024-08-26', INTERVAL 15 DAY), NULL, 6,
        27, 26, NULL, NULL),
       ('2024-08-27', DATE_ADD('2024-08-27', INTERVAL 4 DAY), DATE_ADD('2024-08-27', INTERVAL 14 DAY), NULL, 7, 30,
        27, NULL, NULL),
       ('2024-08-28', DATE_ADD('2024-08-28', INTERVAL 8 DAY), DATE_ADD('2024-08-28', INTERVAL 18 DAY), '2024-09-05',
        8, 33, 28, NULL, NULL),
       ('2024-08-29', DATE_ADD('2024-08-29', INTERVAL 12 DAY), DATE_ADD('2024-08-29', INTERVAL 18 DAY), NULL, 9,
        4, 29, NULL, NULL),
       ('2024-08-30', DATE_ADD('2024-08-30', INTERVAL 2 DAY), DATE_ADD('2024-08-30', INTERVAL 12 DAY), NULL, 10, 7,
        30, NULL, NULL);

SELECT
    id,
    chassi,
    cor,
    CASE
        WHEN disponivel = 0 THEN 'Indisponível'
        WHEN disponivel = 1 THEN 'Livre'
        ELSE 'Estado Desconhecido' -- Trata casos inesperados, se houver
        END AS disponivel,
    nome,
    placa,
    valor_diaria,
    modelo_carro_id
FROM tb_carro;
SELECT * FROM tb_aluguel;