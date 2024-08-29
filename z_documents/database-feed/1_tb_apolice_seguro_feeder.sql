USE db_car_rental_solutis;

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
       (250.00, FALSE, TRUE, TRUE); -- Apólice 10