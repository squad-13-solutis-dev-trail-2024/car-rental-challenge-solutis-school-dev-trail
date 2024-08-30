DROP DATABASE IF EXISTS db_car_rental_solutis;
CREATE DATABASE IF NOT EXISTS db_car_rental_solutis;
USE db_car_rental_solutis;

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

/*
 Validações e Regras de Negócio
    - A cnh do motorista deve ser válida.
    - O motorista deve ser maior de 18 anos para se cadastrar.
    - O motorista não pode ter mais de um cadastro com o mesmo número de CNH.
    - O motorista deve ser uma pessoa cadastrada no sistema.
 */
CREATE TABLE IF NOT EXISTS tb_motorista
(
    id  BIGINT UNSIGNED,
    cnh CHAR(11) NOT NULL UNIQUE,

    UNIQUE (cnh),

    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES tb_pessoa (id) ON DELETE CASCADE
);

/*
 Validações e Regras de Negócio
    - A matrícula do funcionário deve ser única.
    - O funcionário deve ser uma pessoa cadastrada no sistema.
 */
CREATE TABLE IF NOT EXISTS tb_funcionario
(
    id        BIGINT UNSIGNED,
    matricula VARCHAR(50) NOT NULL,

    UNIQUE (matricula),

    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES tb_pessoa (id) ON DELETE CASCADE
);

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
        descricao enum ('AIRBAGS','ALARM','AQUECIMENTO_BANCOS','AR_CONDICIONADO','ASSISTENTE_FRENAGEM_EMERGENCIA','ASSISTENTE_PARTIDA_RAMPA','BANCOS_COURO','BLUETOOTH','CADEIRA_INFANTIL','CAMERA_RE','CARREGADOR_WIRELESS','CHAVE_CARTAO','CONTROLE_ESTABILIDADE','FAROL_DE_MILHA','FREIOS_ABS','GPS','NAVEGACAO_INTEGRADA','PARA_BRISA_DESEMBACADOR','PORTA_MALAS_AUTOMATICO','RETROVISORES_ELETRICOS','RODAS_LIGA_LEVE','SENSOR_ESTACIONAMENTO','SOM_PREMIUM','SUSPENSAO_ESPORTIVA','TETO_SOLAR','VOLANTE_MULTIFUNCIONAL') not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_aluguel (
        id bigint not null auto_increment,
        data_devolucao_efetiva date,
        data_devolucao_prevista date not null,
        data_entrega date not null,
        data_pedido date not null,
        valor_total_final decimal(10,2),
        valor_total_inicial decimal(10,2),
        apolice_seguro_id bigint not null,
        carro_id bigint not null,
        motorista_id bigint not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_apolice_seguro (
        id bigint not null auto_increment,
        protecao_causas_naturais BOOLEAN DEFAULT FALSE not null,
        protecao_roubo BOOLEAN DEFAULT FALSE not null,
        protecao_terceiro BOOLEAN DEFAULT FALSE not null,
        valor_franquia decimal(10,2) not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_carro (
        id bigint not null auto_increment,
        ativo BOOLEAN DEFAULT FALSE not null,
        chassi varchar(255) not null,
        cor varchar(255) not null,
        nome varchar(255) not null,
        placa varchar(255) not null,
        valor_diaria decimal(10,2) not null,
        modelo_carro_id bigint not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_fabricante (
        id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_funcionario (
        matricula varchar(255) not null,
        pessoa_id bigint not null,
        primary key (pessoa_id)
    ) engine=InnoDB
Hibernate:
    create table tb_modelo_carro (
        id bigint not null auto_increment,
        categoria enum ('ESPORTIVO','HATCH_COMPACTO','HATCH_MEDIO','MINIVAN','SEDAN_COMPACTO','SEDAN_GRANDE','SEDAN_MEDIO','UTILITARIO_COMERCIAL') not null,
        descricao varchar(255),
        fabricante_id bigint not null,
        primary key (id)
    ) engine=InnoDB
Hibernate:
    create table tb_motorista (
        numerocnh varchar(255) not null,
        pessoa_id bigint not null,
        primary key (pessoa_id)
    ) engine=InnoDB
Hibernate:
    create table tb_pessoa (
        id bigint not null auto_increment,
        ativo BOOLEAN DEFAULT TRUE not null,
        cpf varchar(255) not null,
        data_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
        data_nascimento date not null,
        email varchar(255) not null,
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
        nome varchar(255) not null,
        sexo enum ('FEMININO','MASCULINO'),
        primary key (id)
    ) engine=InnoDB
Hibernate:
    alter table tb_acessorio
       drop index uk_acessorio_descricao
Hibernate:
    alter table tb_acessorio
       add constraint uk_acessorio_descricao unique (descricao)
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