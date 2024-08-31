package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada lançada quando ocorre um erro de validação de dados.
 * <p>
 * Esta exceção é tipicamente usada para indicar que os dados fornecidos em uma
 * requisição não atendem às regras de validação definidas pela aplicação.
 * Ao ser lançada, esta exceção resulta em uma resposta HTTP com o status
 * 400 (Bad Request), indicando ao cliente que a requisição não pôde ser
 * processada devido a erros de validação.
 * </p>
 *
 * @see RuntimeException
 * @see ResponseStatus
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Schema(description = "Exceção lançada quando ocorre um erro de validação de dados.")
public class ValidationesException extends RuntimeException {

    /**
     * Construtor para criar uma nova instância de {@code ValidationException}
     * com uma mensagem de erro detalhada.
     * <p>
     * A mensagem deve descrever claramente o erro de validação ocorrido, como
     * o campo que não atende às regras ou a regra específica que foi violada.
     * Essa mensagem será útil para a depuração e para fornecer feedback
     * adequado ao cliente da API.
     * </p>
     */
    public ValidationesException(String message) {
        super(message);
    }
}