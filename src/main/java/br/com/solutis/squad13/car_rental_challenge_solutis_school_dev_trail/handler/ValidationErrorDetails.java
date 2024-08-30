package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Classe que encapsula os detalhes de um erro de validação ocorrido durante o processamento
 * de uma requisição na aplicação.
 * <p>
 * Esta classe estende {@link ErrorDetails} para incluir informações adicionais específicas
 * de erros de validação, como o campo que gerou o erro, facilitando a identificação e
 * correção de problemas relacionados à validação de dados.
 * </p>
 *
 * <p>Os detalhes do erro de validação incluem:</p>
 * <ul>
 *     <li>{@link #timestamp} - A data e hora em que o erro ocorreu.</li>
 *     <li>{@link #message} - A mensagem de erro que descreve o problema ocorrido.</li>
 *     <li>{@link #details} - Detalhes adicionais sobre o erro, como a URI da requisição
 *     ou a descrição da exceção.</li>
 *     <li>{@link #errorCode} - O código ou tipo do erro ocorrido, facilitando a
 *     categorização do problema.</li>
 *     <li>{@link #field} - O nome do campo que falhou na validação, indicando a origem exata do erro.</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     {@code
 *     ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails(
 *         LocalDateTime.now(),
 *         "O campo 'email' não pode estar vazio.",
 *         "uri=/usuarios",
 *         "VALIDATION_ERROR",
 *         "email"
 *     );
 *     }
 * </pre>
 *
 * @see ErrorDetails
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ValidationErrorDetails extends ErrorDetails {
    private String field;

    /**
     * Construtor que inicializa todos os atributos da classe {@link ValidationErrorDetails},
     * incluindo os atributos herdados de {@link ErrorDetails}.
     *
     * @param timestamp A data e hora em que o erro ocorreu.
     * @param message A mensagem de erro que descreve o problema ocorrido.
     * @param details Detalhes adicionais sobre o erro, como a URI da requisição ou a descrição da exceção.
     * @param errorCode O código ou tipo do erro ocorrido, facilitando a categorização do problema.
     * @param field O nome do campo que falhou na validação.
     */
    public ValidationErrorDetails(LocalDateTime timestamp,
                                  String message,
                                  String details,
                                  String errorCode,
                                  String field) {
        super(timestamp, message, details, errorCode);
        this.field = field;
    }
}
