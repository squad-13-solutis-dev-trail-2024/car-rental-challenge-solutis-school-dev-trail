package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.handler;

import java.time.LocalDateTime;

/**
 * Record que encapsula os detalhes de um erro ocorrido durante o processamento
 * de uma requisição na aplicação.
 * <p>
 * Esta classe é utilizada para padronizar as informações retornadas ao cliente
 * quando uma exceção é lançada, facilitando a identificação do erro e
 * a resolução do problema.
 * </p>
 *
 * <p>Os detalhes do erro incluem:</p>
 * <ul>
 *     <li>{@link #timestamp} - A data e hora em que o erro ocorreu.</li>
 *     <li>{@link #field} - O campo específico ou a parte da requisição onde o erro foi detectado.</li>
 *     <li>{@link #details} - Detalhes adicionais sobre o erro, como a descrição da exceção.</li>
 *     <li>{@link #error} - O código ou tipo do erro ocorrido, facilitando a categorização do problema.</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     {@code
 *     ErrorDetails errorDetails = new ErrorDetails(
 *         LocalDateTime.now(),
 *         "campo_invalido",
 *         "O valor fornecido para o campo 'email' já está em uso.",
 *         "DUPLICATE_ENTRY"
 *     );
 *     }
 * </pre>
 *
 * @param timestamp A data e hora exata em que o erro ocorreu.
 * @param field     O campo específico ou a parte da requisição onde o erro foi identificado.
 * @param details   Informações adicionais que descrevem o erro ocorrido.
 * @param error     O código ou tipo do erro, utilizado para categorização e tratamento.
 */
public record ErrorDetails(
        LocalDateTime timestamp,
        String field,
        String details,
        String error
) {
}
