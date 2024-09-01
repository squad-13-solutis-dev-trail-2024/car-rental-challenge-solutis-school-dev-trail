package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import static java.text.NumberFormat.getCurrencyInstance;

/**
 * Classe responsável por serializar objetos do tipo {@link BigDecimal} em formato de moeda brasileira (R$).
 * <p>
 * Esta classe estende {@link JsonSerializer} e sobrescreve o metodo {@link #serialize(BigDecimal, JsonGenerator, SerializerProvider)},
 * formatando o valorTotalParcial do {@link BigDecimal} como uma string no formato de moeda brasileira, incluindo o símbolo "R$".
 * A formatação é feita utilizando a classe {@link NumberFormat} com o {@link Locale} apropriado para o Brasil ("pt", "BR").
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 *     {@code
 *     ObjectMapper mapper = new ObjectMapper();
 *     SimpleModule module = new SimpleModule();
 *     module.addSerializer(BigDecimal.class, new BigDecimalCurrencySerializer());
 *     mapper.registerModule(module);
 *     }
 * </pre>
 *
 * @see JsonSerializer
 * @see NumberFormat
 * @see Locale
 */
@Schema(description = "Serializador personalizado para formatar valores BigDecimal como moeda brasileira (R$).")
public class BigDecimalCurrencySerializer extends JsonSerializer<BigDecimal> {

    /**
     * Serializa um valorTotalParcial {@link BigDecimal} em uma string formatada como moeda brasileira.
     *
     * @param value       O valorTotalParcial {@link BigDecimal} a ser serializado.
     * @param gen         O gerador JSON utilizado para a escrita da string.
     * @param serializers O provedor de serializers que pode ser utilizado para serialização de objetos complexos.
     * @throws IOException Se ocorrer um erro de I/O durante a serialização.
     */
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        NumberFormat formatter = getCurrencyInstance(Locale.of("pt", "BR"));
        String formattedValue = formatter.format(value);
        gen.writeString(formattedValue);
    }
}
