package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.handler;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception.DuplicateEntryException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe responsável por tratar exceções de forma global na aplicação, proporcionando
 * uma resposta uniforme e amigável ao cliente em caso de erros.
 * <p>
 * Esta classe utiliza as anotações do Spring para interceptar exceções lançadas
 * nos controladores REST e retornar mensagens de erro apropriadas ao cliente.
 * <p>
 * Exceções específicas, como {@link DuplicateEntryException} e {@link EntityNotFoundException},
 * são manipuladas de maneira a fornecer feedback claro sobre o que deu errado na requisição.
 * </p>
 *
 * @see RestControllerAdvice
 * @see ExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula a exceção {@link DuplicateEntryException}, que é lançada quando há uma tentativa
     * de inserir uma entrada duplicada em uma entidade que deve ter valores únicos.
     * <p>
     * Esta exceção pode ser lançada, por exemplo, quando se tenta cadastrar um CPF ou CNPJ já existente
     * no sistema. O metodo encapsula os detalhes do erro em um objeto {@link ErrorDetails} e retorna uma
     * resposta com status HTTP 409 (Conflict), indicando que a requisição conflita com o estado atual
     * do recurso.
     * </p>
     *
     * @param exception  A exceção de entrada duplicada, que contém a mensagem de erro a ser retornada ao cliente.
     * @param webRequest O objeto {@link WebRequest} que fornece informações adicionais sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo uma lista com os detalhes do erro encapsulados em {@link ErrorDetails}
     *         e o status HTTP 409 (Conflict).
     */
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<List<ErrorDetails>> handleDuplicateEntryException(DuplicateEntryException exception,
                                                                            WebRequest webRequest) {
        // Cria um objeto ErrorDetails com a data/hora atual, mensagem da exceção, detalhes da requisição e o código de erro.
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "DUPLICATE_ENTRY"
        );

        // Retorna uma ResponseEntity contendo a lista de ErrorDetails e o status HTTP 409 (Conflict).
        return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of(errorDetails));
    }

    /**
     * Manipula a exceção {@link EntityNotFoundException}, que é lançada quando uma entidade
     * requisitada não é encontrada no banco de dados.
     * <p>
     * Esta exceção é comum em operações de busca ou atualização, onde o identificador fornecido
     * não corresponde a nenhuma entidade existente. O metodo encapsula os detalhes do erro em
     * um objeto {@link ErrorDetails} e retorna uma resposta com status HTTP 404 (Not Found),
     * indicando que o recurso requisitado não pôde ser localizado.
     * </p>
     *
     * @param exception  A exceção de entidade não encontrada, que contém a mensagem de erro a ser retornada ao cliente.
     * @param webRequest O objeto {@link WebRequest} que fornece informações adicionais sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo uma lista com os detalhes do erro encapsulados em {@link ErrorDetails}
     *         e o status HTTP 404 (Not Found).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<ErrorDetails>> handleResourceNotFoundException(EntityNotFoundException exception,
                                                                              WebRequest webRequest) {

        // Cria um objeto ErrorDetails com a data/hora atual, mensagem da exceção, detalhes da requisição e o código de erro.
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND"
        );

        // Retorna uma ResponseEntity contendo a lista de ErrorDetails e o status HTTP 404 (Not Found).
        return new ResponseEntity<>(List.of(errorDetails), HttpStatus.NOT_FOUND);
    }
}
