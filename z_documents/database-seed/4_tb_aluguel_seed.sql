USE db_car_rental_solutis;

INSERT INTO tb_aluguel (data_pedido, data_entrega, data_devolucao_prevista, data_devolucao_efetiva, valor, motorista_id,
                        carro_id, apolice_seguro_id)
VALUES ('2024-08-01', DATE_ADD('2024-08-01', INTERVAL 10 DAY), DATE_ADD('2024-08-01', INTERVAL 10 DAY), NULL, 500.00, 1,
        1, 1), -- Aluguel 1
       ('2024-08-02', DATE_ADD('2024-08-02', INTERVAL 10 DAY), DATE_ADD('2024-08-02', INTERVAL 10 DAY), NULL, 600.00, 2,
        2, 2), -- Aluguel 2
       ('2024-08-03', DATE_ADD('2024-08-03', INTERVAL 10 DAY), DATE_ADD('2024-08-03', INTERVAL 10 DAY), NULL, 550.00, 3,
        3, 3), -- Aluguel 3
       ('2024-08-04', DATE_ADD('2024-08-04', INTERVAL 10 DAY), DATE_ADD('2024-08-04', INTERVAL 10 DAY), NULL, 650.00, 4,
        4, 4), -- Aluguel 4
       ('2024-08-05', DATE_ADD('2024-08-05', INTERVAL 10 DAY), DATE_ADD('2024-08-05', INTERVAL 10 DAY), NULL, 700.00, 5,
        5, 5), -- Aluguel 5
       ('2024-08-06', DATE_ADD('2024-08-06', INTERVAL 10 DAY), DATE_ADD('2024-08-06', INTERVAL 10 DAY), NULL, 500.00, 6,
        6, 6), -- Aluguel 6
       ('2024-08-07', DATE_ADD('2024-08-07', INTERVAL 10 DAY), DATE_ADD('2024-08-07', INTERVAL 10 DAY), NULL, 600.00, 7,
        7, 7), -- Aluguel 7
       ('2024-08-08', DATE_ADD('2024-08-08', INTERVAL 10 DAY), DATE_ADD('2024-08-08', INTERVAL 10 DAY), NULL, 550.00, 8,
        8, 8), -- Aluguel 8
       ('2024-08-09', DATE_ADD('2024-08-09', INTERVAL 10 DAY), DATE_ADD('2024-08-09', INTERVAL 10 DAY), NULL, 650.00, 9,
        9, 9), -- Aluguel 9
       ('2024-08-10', DATE_ADD('2024-08-10', INTERVAL 10 DAY), DATE_ADD('2024-08-10', INTERVAL 10 DAY), NULL, 700.00,
        10, 10, 10); -- Aluguel 10
