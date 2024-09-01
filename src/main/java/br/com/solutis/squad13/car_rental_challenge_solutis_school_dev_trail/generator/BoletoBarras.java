package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.generator;

import java.util.Random;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe utilitária para gerar códigos de barras de boletos bancários fictícios.
 * <p>
 * Esta classe fornece um metodo para gerar um código de barras numérico aleatório com 44 dígitos,
 * que pode ser utilizado para fins de teste ou demonstração em aplicações que lidam com boletos bancários.
 * </p>
 * <p>
 * <b>Observação:</b> Os códigos de barras gerados por esta classe são fictícios e não correspondem
 * a códigos de barras válidos de boletos bancários reais.
 * </p>
 */
@Schema(description = "Classe utilitária para gerar códigos de barras de boletos bancários fictícios.")
public class BoletoBarras {

    /**
     * Gera um código de barras numérico aleatório com 44 dígitos.
     * <p>
     * O código de barras gerado é composto apenas por dígitos numéricos (0-9) e não segue
     * nenhum padrão específico de formatação de boletos bancários reais.
     * </p>
     *
     * @return O código de barras gerado como uma string.
     */
    @Schema(description = "Gera um código de barras numérico aleatório com 44 dígitos.")
    public static String gerarCodigoDeBarras() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        // Gerar um código de barras fictício com 44 dígitos
        for (int i = 0; i < 44; i++) codigo.append(random.nextInt(10));

        return codigo.toString();
    }
}