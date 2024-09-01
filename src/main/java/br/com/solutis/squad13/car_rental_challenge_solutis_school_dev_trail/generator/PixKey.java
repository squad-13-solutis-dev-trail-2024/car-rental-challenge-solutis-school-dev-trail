package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.generator;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe utilitária para gerar chaves Pix fictícias.
 * <p>
 * Esta classe fornece um metodo para gerar uma chave Pix aleatória no formato UUID (Universally Unique Identifier),
 * que pode ser utilizada para fins de teste ou demonstração em aplicações que lidam com pagamentos via Pix.
 * </p>
 * <p>
 * <b>Observação:</b> As chaves Pix geradas por esta classe são fictícias e não correspondem
 * a chaves Pix válidas registradas em instituições financeiras.
 * </p>
 */
@Schema(description = "Classe utilitária para gerar chaves Pix fictícias.")
public class PixKey {

    /**
     * Gera uma chave Pix aleatória no formato UUID.
     * <p>
     * O UUID gerado é uma string que representa um identificador único universalmente,
     * comumente utilizado em sistemas de software para garantir a unicidade de dados.
     * </p>
     *
     * @return A chave Pix gerada como uma string no formato UUID.
     */
    @Schema(description = "Gera uma chave Pix aleatória no formato UUID.")
    public static String generatePixKey() {
        return UUID.randomUUID().toString();
    }
}