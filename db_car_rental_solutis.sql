DROP DATABASE IF EXISTS db_car_rental_solutis;
CREATE DATABASE IF NOT EXISTS db_car_rental_solutis;
USE db_car_rental_solutis;

SHOW TABLES;

DESCRIBE tb_acessorio;
DESCRIBE tb_aluguel;
DESCRIBE tb_apolice_seguro;
DESCRIBE tb_carro;
DESCRIBE tb_categoria;
DESCRIBE tb_fabricante;
DESCRIBE tb_funcionario;
DESCRIBE tb_modelo_carro;
-- DESCRIBE tb_motorista;
DESCRIBE tb_pessoa;
DESCRIBE tb_sexo;
DESCRIBE tb_cliente;
DESCRIBE tb_carro_acessorio;


/*
História de Usuário: Cadastro de Cliente

Como um cliente em potencial,
Eu quero poder me cadastrar na locadora de automóveis
Para ter acesso aos serviços e alugar veículos.

Critérios de Aceitação:
    - Deve haver um formulário de cadastro na página inicial.
    - O formulário deve solicitar as informações básicas do cliente: nome completo, data de nascimento, cpf, número da CNH.
    - O cliente deve receber uma confirmação na tela após o cadastro bem-sucedido.
    - O sistema deve verificar a validade do endereço de e-mail para evitar registros duplicados.
    - Após o cadastro, o cliente deve ser redirecionado para a página inicial, onde pode acessar serviços da locadora.

Notas adicionais:
    Esta história de usuário aborda a necessidade de um cliente se cadastrar na locadora de
    automóveis para obter acesso aos serviços de aluguel de veículos. Os critérios de aceitação
    definem os requisitos específicos que devem ser atendidos para que a história seja
    considerada completa. Isso inclui aspectos técnicos, como validação de dados, bem como
    aspectos funcionais, como a confirmação por e-mail e a concordância com os termos e
    condições.

Lembrando que os detalhes exatos podem variar com base nas necessidades do projeto e
da equipe, mas esse exemplo oferece uma estrutura sólida para capturar os requisitos
essenciais do cadastro de cliente em uma locadora de automóveis.
 */

/*
 Validações e Regras de Negócio
    - A data de nascimento do cliente deve ser válida.
    - O CPF do cliente deve ser válido.
    - O número da CNH do cliente deve ser válido.
    - O cliente deve ser maior de 18 anos para se cadastrar.
    - O cliente não pode ter mais de um cadastro com o mesmo CPF.
    - O cliente não pode ter mais de um cadastro com o mesmo número de CNH.
 */

CREATE TABLE IF NOT EXISTS tb_sexo
(
    id   BIGINT UNSIGNED AUTO_INCREMENT,
    nome ENUM ('MASCULINO', 'FEMININO') NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_pessoa
(
    id              BIGINT UNSIGNED AUTO_INCREMENT,
    nome            VARCHAR(100)    NOT NULL,
    data_nascimento DATE            NOT NULL,
    cpf             CHAR(11)        NOT NULL,
    id_sexo         BIGINT UNSIGNED NOT NULL,

    UNIQUE (cpf),

    PRIMARY KEY (id),
    FOREIGN KEY (id_sexo) REFERENCES tb_sexo (id) ON DELETE CASCADE
);

-- ou tb_motorista
CREATE TABLE IF NOT EXISTS tb_cliente
(
    id  BIGINT UNSIGNED,
    cnh CHAR(11) NOT NULL UNIQUE,

    UNIQUE (cnh),

    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES tb_pessoa (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_funcionario
(
    id        BIGINT UNSIGNED,
    matricula VARCHAR(50) NOT NULL,

    UNIQUE (matricula),

    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES tb_pessoa (id) ON DELETE CASCADE
);

/*
 ------------------------------------------------------------------------------------------------------

História de Usuário: Escolha de Veículo para Aluguel
Como um cliente cadastrado na locadora de automóveis,
Eu quero poder escolher um veículo disponível para alugar,
Para reservar o veículo que atenda às minhas necessidades de locomoção.

Critérios de Aceitação:
    - Na página inicial deve haver uma seção "Seleção de Veículos" ou similar.
    - Os veículos disponíveis para aluguel devem ser apresentados em uma lista ou grade, exibindo informações como fabricante, modelo, categoria, acessórios e preço por dia.
    - Cada veículo deve ter uma imagem representativa para auxiliar na identificação.
    - O cliente deve ser capaz de aplicar filtros, como categoria de veículo (carro, SUV, caminhonete) ou acessórios (ar-condicionado, sistema de navegação, etc.).
    - Ao clicar em um veículo, o cliente deve ser direcionado para uma página de detalhes do veículo.
    - Na página de detalhes, o cliente deve ver informações mais detalhadas sobre o veículo, incluindo especificações técnicas e descrição.
    - O cliente deve ter a opção de selecionar o período de aluguel, especificando datas de início e término.
    - Após selecionar o período, o cliente deve ser capaz de adicionar o veículo ao seu carrinho de aluguel.
    - O carrinho de aluguel deve exibir um resumo dos veículos selecionados, suas datas de aluguel e o custo total estimado.
    - O cliente deve ter a opção de revisar o carrinho, fazer ajustes e confirmar a reserva.
    - Uma vez confirmada a reserva, o cliente deve receber uma confirmação na tela com os detalhes da reserva.

Notas adicionais:
    Esta história de usuário trata do processo de escolha de um veículo para aluguel por parte
    de um cliente cadastrado. Os critérios de aceitação descrevem os passos e funcionalidades
    necessárias para que o cliente possa navegar pelos veículos disponíveis, selecionar um
    veículo, escolher as datas de aluguel e confirmar sua reserva. Essa funcionalidade é
    essencial para permitir que os clientes escolham e reservem veículos de acordo com suas
    preferências e necessidades.

------------------------------------------------------------------------------------------------------
 */

CREATE TABLE IF NOT EXISTS tb_fabricante
(
    id   BIGINT UNSIGNED AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_categoria
(
    id   BIGINT UNSIGNED AUTO_INCREMENT,
    nome ENUM (
        'HATCH_COMPACTO',
        'HATCH_MEDIO',
        'SEDAN_COMPACTO',
        'SEDAN_MEDIO',
        'SEDAN_GRANDE',
        'MINIVAN',
        'ESPORTIVO',
        'UTILITARIO_COMERCIAL'
        ) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_acessorio
(
    id        TINYINT UNSIGNED AUTO_INCREMENT,
    descricao ENUM (
        'GPS',
        'AR_CONDICIONADO',
        'CADEIRA_INFANTIL',
        'TETO_SOLAR',
        'CAMERA_RE',
        'SENSOR_ESTACIONAMENTO',
        'BLUETOOTH',
        'ALARM',
        'RODAS_LIGA_LEVE',
        'FAROL_DE_MILHA'
        ) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_modelo_carro
(
    id            BIGINT UNSIGNED AUTO_INCREMENT,
    descricao     VARCHAR(100)    NOT NULL,
    id_categoria  BIGINT UNSIGNED NOT NULL,
    id_fabricante BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (id_categoria) REFERENCES tb_categoria (id) ON DELETE CASCADE,
    FOREIGN KEY (id_fabricante) REFERENCES tb_fabricante (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_carro
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    id_modelo   BIGINT UNSIGNED NOT NULL,
    placa       CHAR(7)         NOT NULL,
    chassi      CHAR(17)        NOT NULL,
    cor         VARCHAR(50)     NOT NULL,
    valorDiaria DECIMAL(10, 2)  NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (id_modelo) REFERENCES tb_modelo_carro (id) ON DELETE CASCADE
);

-- Um carro pode ter vários acessórios e um acessório pode estar em vários carros
CREATE TABLE IF NOT EXISTS tb_carro_acessorio
(
    id_carro     BIGINT UNSIGNED  NOT NULL,
    id_acessorio TINYINT UNSIGNED NOT NULL,

    PRIMARY KEY (id_carro, id_acessorio),

    FOREIGN KEY (id_carro) REFERENCES tb_carro (id) ON DELETE CASCADE,
    FOREIGN KEY (id_acessorio) REFERENCES tb_acessorio (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_apolice_seguro
(
    id                     BIGINT UNSIGNED AUTO_INCREMENT,
    valorFranquia          DECIMAL(10, 2) NOT NULL,
    protecaoTerceiro       BOOLEAN        NOT NULL,
    protecaoCausasNaturais BOOLEAN        NOT NULL,
    protecaoRoubo          BOOLEAN        NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_aluguel
(
    id            BIGINT UNSIGNED AUTO_INCREMENT,

    id_cliente    BIGINT UNSIGNED NOT NULL,
    id_carro      BIGINT UNSIGNED NOT NULL,
    id_apolice    BIGINT UNSIGNED NOT NULL,

    dataPedido    DATE            NOT NULL,
    dataEntregue  DATE            NOT NULL,
    dataDevolucao DATE,
    valorTotal    DECIMAL(10, 2)  NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (id_cliente) REFERENCES tb_cliente (id) ON DELETE CASCADE,
    FOREIGN KEY (id_carro) REFERENCES tb_carro (id) ON DELETE CASCADE,
    FOREIGN KEY (id_apolice) REFERENCES tb_apolice_seguro (id) ON DELETE CASCADE
);
