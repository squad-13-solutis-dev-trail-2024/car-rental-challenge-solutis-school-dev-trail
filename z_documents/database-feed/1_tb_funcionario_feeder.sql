USE db_car_rental_solutis;

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

INSERT INTO tb_funcionario (pessoa_id, matricula)
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

SELECT *
FROM tb_pessoa;
SELECT *
FROM tb_funcionario;