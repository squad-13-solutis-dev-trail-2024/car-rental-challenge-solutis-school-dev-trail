package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada lançada quando ocorre um erro de validação de dados.
 * <p>
 * Esta exceção é tipicamente usada quando os dados fornecidos pelo cliente não atendem
 * aos requisitos de validação definidos para uma determinada entidade ou operação.
 * Ao ser lançada, esta exceção resulta em uma resposta HTTP com o status 400 (Bad Request),
 * indicando ao cliente que a solicitação é inválida e precisa ser corrigida.
 * </p>
 *
 * @see RuntimeException
 * @see ResponseStatus
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    /**
     * Construtor para criar uma nova instância de {@code ValidationException} com uma mensagem de erro detalhada.
     * <p>
     * A mensagem deve descrever claramente a natureza do erro de validação, como o campo que causou o erro
     * ou a regra de validação que foi violada. Essa mensagem será útil para a depuração e para fornecer
     * feedback adequado ao cliente da API.
     * </p>
     */
    public ValidationException(String message) {
        super(message);
    }
}