package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada lançada quando ocorre uma tentativa de adicionar uma entrada duplicada
 * em uma entidade que exige valores únicos, como CPF, CNPJ, e-mails, etc.
 * <p>
 * Esta exceção é tipicamente usada em operações de inserção ou atualização de dados,
 * onde é necessário garantir a unicidade de determinados campos no banco de dados.
 * Ao ser lançada, esta exceção resulta em uma resposta HTTP com o status 409 (Conflict),
 * indicando ao cliente que a operação não pôde ser concluída devido a um conflito
 * com os dados existentes.
 * </p>
 *
 * @see RuntimeException
 * @see ResponseStatus
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEntryException extends RuntimeException {

    /**
     * Construtor para criar uma nova instância de {@code DuplicateEntryException} com uma mensagem de erro detalhada.
     * <p>
     * A mensagem deve descrever claramente a natureza do conflito, como o campo que causou a duplicidade
     * ou a operação que não pôde ser realizada. Essa mensagem será útil para a depuração e para fornecer
     * feedback adequado ao cliente da API.
     * </p>
     */
    public DuplicateEntryException(String message) {
        super(message);
    }
}
