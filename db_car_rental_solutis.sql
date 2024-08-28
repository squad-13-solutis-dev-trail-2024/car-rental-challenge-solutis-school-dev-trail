DROP DATABASE IF EXISTS db_car_rental_solutis;
CREATE DATABASE IF NOT EXISTS db_car_rental_solutis;
USE db_car_rental_solutis;

SHOW TABLES;

DESCRIBE tb_acessorio;
DESCRIBE tb_aluguel;
DESCRIBE tb_apolice_seguro;
DESCRIBE tb_carro;
DESCRIBE tb_carro_acessorio;
DESCRIBE tb_fabricante;
DESCRIBE tb_funcionario;
DESCRIBE tb_modelo_carro;
DESCRIBE tb_motorista;
DESCRIBE tb_pessoa;


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


CREATE TABLE IF NOT EXISTS tb_pessoa
(
    id              BIGINT UNSIGNED AUTO_INCREMENT,
    nome            VARCHAR(100)                   NOT NULL,
    data_nascimento DATE                           NOT NULL,
    cpf             CHAR(11)                       NOT NULL,
    sexo            ENUM ('MASCULINO', 'FEMININO') NOT NULL,

    UNIQUE (cpf),

    PRIMARY KEY (id)
);

-- ou tb_motorista
CREATE TABLE IF NOT EXISTS tb_motorista
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
    nome          ENUM (
        'HATCH_COMPACTO',
        'HATCH_MEDIO',
        'SEDAN_COMPACTO',
        'SEDAN_MEDIO',
        'SEDAN_GRANDE',
        'MINIVAN',
        'ESPORTIVO',
        'UTILITARIO_COMERCIAL'
        )                         NOT NULL,
    id_fabricante BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

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

    id_motorista  BIGINT UNSIGNED NOT NULL,
    id_carro      BIGINT UNSIGNED NOT NULL,
    id_apolice    BIGINT UNSIGNED NOT NULL,

    dataPedido    DATE            NOT NULL,
    dataEntregue  DATE            NOT NULL,
    dataDevolucao DATE,
    valorTotal    DECIMAL(10, 2)  NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (id_motorista) REFERENCES tb_motorista (id) ON DELETE CASCADE,
    FOREIGN KEY (id_carro) REFERENCES tb_carro (id) ON DELETE CASCADE,
    FOREIGN KEY (id_apolice) REFERENCES tb_apolice_seguro (id) ON DELETE CASCADE
);

/*
 Hibernate:
    create table tb_acessorio (
        id bigint not null auto_increment,
        descricao enum ('ALARM','AR_CONDICIONADO','BLUETOOTH','CADEIRA_INFANTIL','CAMERA_RE','FAROL_DE_MILHA','GPS','RODAS_LIGA_LEVE','SENSOR_ESTACIONAMENTO','TETO_SOLAR') not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_aluguel (
        id bigint not null auto_increment,
        data_devolucao date,
        data_entrega date not null,
        data_pedido date not null,
        valor decimal(10,2) not null,
        apolice_seguro_id bigint not null,
        carro_id bigint not null,
        motorista_id bigint not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_apolice_seguro (
        id bigint not null auto_increment,
        protecao_causas_naturais boolean default false not null,
        protecao_roubo boolean default false not null,
        protecao_terceiro boolean default false not null,
        valor_franquia decimal(10,2),
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_carro (
        id bigint not null auto_increment,
        chassi varchar(17) not null,
        cor varchar(50) not null,
        is_disponivel_para_aluguel boolean default false not null,
        placa varchar(7) not null,
        valor_diaria decimal(10,2),
        modelo_carro_id bigint not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_fabricante (
        id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_funcionario (
        matricula varchar(255),
        pessoa_id bigint not null,
        primary key (pessoa_id)
    ) engine=InnoDB
Hibernate:
    create table tb_modelo_carro (
        id bigint not null auto_increment,
        categoria enum ('ESPORTIVO','HATCH_COMPACTO','HATCH_MEDIO','MINIVAN','SEDAN_COMPACTO','SEDAN_GRANDE','SEDAN_MEDIO','UTILITARIO_COMERCIAL'),
        descricao varchar(255),
        fabricante_id bigint not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_motorista (
        numerocnh varchar(255),
        pessoa_id bigint not null,
        primary key (pessoa_id)
    ) engine=InnoDB
Hibernate:
    create table tb_pessoa (
        id bigint not null auto_increment,
        cpf varchar(255),
        data_nascimento datetime(6),
        email varchar(255),
        nome varchar(255),
        sexo enum ('FEMININO','MASCULINO'),
        primary key (id)
    ) engine=InnoDB
Hibernate:
    alter table tb_aluguel
       drop index UK4tkn76dohfy2re8237cr8dmcb
Hibernate:
    alter table tb_aluguel
       add constraint UK4tkn76dohfy2re8237cr8dmcb unique (apolice_seguro_id)
Hibernate:
    alter table tb_funcionario
       drop index uk_funcionario_matricula
Hibernate:
    alter table tb_funcionario
       add constraint uk_funcionario_matricula unique (matricula)
Hibernate:
    alter table tb_pessoa
       drop index uk_pessoa_cpf
Hibernate:
    alter table tb_pessoa
       add constraint uk_pessoa_cpf unique (cpf)
Hibernate:
    alter table tb_pessoa
       drop index uk_pessoa_email
Hibernate:
    alter table tb_pessoa
       add constraint uk_pessoa_email unique (email)
Hibernate:
    create table tb_carro_acessorio (
        id_carro bigint not null,
        id_acessorio bigint not null
    ) engine=InnoDB
Hibernate:
    alter table tb_aluguel
       add constraint FKh7xaobj94rpt292hwotl1irn6
       foreign key (apolice_seguro_id)
       references tb_apolice_seguro (id)
Hibernate:
    alter table tb_aluguel
       add constraint FKlvmt4ph6gr2r9sdlqq06kf4g8
       foreign key (carro_id)
       references tb_carro (id)
Hibernate:
    alter table tb_aluguel
       add constraint FKf1bcfn2heg58msxn2k2sj0gef
       foreign key (motorista_id)
       references tb_motorista (pessoa_id)
Hibernate:
    alter table tb_carro
       add constraint FK3o51cc9nwb0bhp9r7llvrqk1
       foreign key (modelo_carro_id)
       references tb_modelo_carro (id)
Hibernate:
    alter table tb_funcionario
       add constraint FKekpws4jkfri675vhralomxo1
       foreign key (pessoa_id)
       references tb_pessoa (id)
Hibernate:
    alter table tb_modelo_carro
       add constraint FKu2tq8byyt3wvfqe7qy5alpy2
       foreign key (fabricante_id)
       references tb_fabricante (id)
Hibernate:
    alter table tb_motorista
       add constraint FKdrt0hr605caqaiurt1yodq0nv
       foreign key (pessoa_id)
       references tb_pessoa (id)
Hibernate:
    alter table tb_carro_acessorio
       add constraint FKbsfriur010kgg3xufw2qn7pbo
       foreign key (id_acessorio)
       references tb_acessorio (id)
Hibernate:
    alter table tb_carro_acessorio
       add constraint FKh4r50po6pk34u6vytpokby8ib
       foreign key (id_carro)
       references tb_carro (id)
 */