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

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula a exceção de entrada duplicada.
     *
     * @param exception  A exceção de entrada duplicada.
     * @param webRequest O objeto WebRequest que fornece informações sobre a solicitação.
     * @return Uma ResponseEntity contendo detalhes do erro e status HTTP 409 (Conflict).
     */
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<List<ErrorDetails>> handleDuplicateEntryException(DuplicateEntryException exception,
                                                                            WebRequest webRequest) {
        // Cria um objeto ErrorDetails para encapsular os detalhes do erro.
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
     * Manipula a exceção de entidade não encontrada.
     *
     * @param exception  A exceção de entidade não encontrada.
     * @param webRequest O objeto WebRequest que fornece informações sobre a solicitação.
     * @return Uma ResponseEntity contendo detalhes do erro e status HTTP 404 (Not Found).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<ErrorDetails>> handleResourceNotFoundException(EntityNotFoundException exception,
                                                                              WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),                            // Data e hora do erro.
                exception.getMessage(),                         // Mensagem de erro da exceção.
                webRequest.getDescription(false),              // Descrição da solicitação.
                "RESOURCE_NOT_FOUND"                           // Tipo de erro.
        );

        return new ResponseEntity<>(List.of(errorDetails), HttpStatus.NOT_FOUND);
    }
}