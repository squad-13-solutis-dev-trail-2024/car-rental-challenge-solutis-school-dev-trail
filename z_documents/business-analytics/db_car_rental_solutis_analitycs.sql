USE db_car_rental_solutis;

-- Lista de todos os carros
SELECT id,
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

-- Lista de todos os carros disponíveis para aluguel
SELECT id,
       chassi,
       cor,
       nome,
       placa,
       valor_diaria,
       modelo_carro_id
FROM tb_carro
WHERE disponivel = 1;

-- Lista de todos os carros alugados e o período de aluguel
SELECT a.id AS aluguel_id,
       c.id AS carro_id,
       c.chassi,
       c.cor,
       c.nome,
       c.placa,
       c.valor_diaria,
       a.data_entrega,
       a.data_devolucao_prevista
FROM tb_carro c
JOIN tb_aluguel a ON c.id = a.carro_id
WHERE a.data_devolucao_efetiva IS NULL;

