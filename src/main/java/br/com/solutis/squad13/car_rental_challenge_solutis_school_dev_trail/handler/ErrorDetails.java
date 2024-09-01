package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Classe que encapsula os detalhes de um erro ocorrido durante o processamento
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
 *     <li>{@link #message} - A mensagem de erro que descreve o problema ocorrido.</li>
 *     <li>{@link #details} - Detalhes adicionais sobre o erro, como a URI da requisição
 *     ou a descrição da exceção.</li>
 *     <li>{@link #errorCode} - O código ou tipo do erro ocorrido, facilitando a
 *     categorização do problema.</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     {@code
 *     ErrorDetails errorDetails = new ErrorDetails(
 *         LocalDateTime.now(),
 *         "O valorTotalParcial fornecido para o campo 'email' já está em uso.",
 *         "uri=/usuarios",
 *         "DUPLICATE_ENTRY"
 *     );
 *     }
 * </pre>
 *
 * @see ValidationErrorDetails
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String errorCode;
}