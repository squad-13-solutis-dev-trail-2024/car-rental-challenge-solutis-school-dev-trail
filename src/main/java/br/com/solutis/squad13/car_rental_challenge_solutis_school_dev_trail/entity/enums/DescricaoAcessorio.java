package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de acessórios disponíveis para os carros.")
public enum DescricaoAcessorio {
    @Schema(description = "Sistema de Posicionamento Global (GPS).")
    GPS,

    @Schema(description = "Ar Condicionado.")
    AR_CONDICIONADO,

    @Schema(description = "Cadeira Infantil.")
    CADEIRA_INFANTIL,

    @Schema(description = "Teto Solar.")
    TETO_SOLAR,

    @Schema(description = "Câmera de Ré.")
    CAMERA_RE,

    @Schema(description = "Sensor de Estacionamento.")
    SENSOR_ESTACIONAMENTO,

    @Schema(description = "Conectividade Bluetooth.")
    BLUETOOTH,

    @Schema(description = "Sistema de Alarme.")
    ALARM,

    @Schema(description = "Rodas de Liga Leve.")
    RODAS_LIGA_LEVE,

    @Schema(description = "Farol de Milha.")
    FAROL_DE_MILHA,

    @Schema(description = "Aquecimento dos Bancos.")
    AQUECIMENTO_BANCOS,

    @Schema(description = "Volante Multifuncional.")
    VOLANTE_MULTIFUNCIONAL,

    @Schema(description = "Bancos de Couro.")
    BANCOS_COURO,

    @Schema(description = "Sistema de Navegação Integrado.")
    NAVEGACAO_INTEGRADA,

    @Schema(description = "Freios ABS.")
    FREIOS_ABS,

    @Schema(description = "Airbags.")
    AIRBAGS,

    @Schema(description = "Controle de Estabilidade.")
    CONTROLE_ESTABILIDADE,

    @Schema(description = "Suspensão Esportiva.")
    SUSPENSAO_ESPORTIVA,

    @Schema(description = "Retrovisores Elétricos.")
    RETROVISORES_ELETRICOS,

    @Schema(description = "Sistema de Som Premium.")
    SOM_PREMIUM,

    @Schema(description = "Chave Presencial.")
    CHAVE_CARTAO,

    @Schema(description = "Assistente de Partida em Rampa.")
    ASSISTENTE_PARTIDA_RAMPA,

    @Schema(description = "Para-brisa com Desembaçador.")
    PARA_BRISA_DESEMBACADOR,

    @Schema(description = "Porta-malas com Abertura Automática.")
    PORTA_MALAS_AUTOMATICO,

    @Schema(description = "Carregador de Celular Wireless.")
    CARREGADOR_WIRELESS,

    @Schema(description = "Assistente de Frenagem de Emergência.")
    ASSISTENTE_FRENAGEM_EMERGENCIA
}
