package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.handler;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception.DuplicateEntryException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Schema(description = "Classe responsável por tratar exceções globalmente na aplicação.")
public class GlobalExceptionHandler {

    /**
     * Manipula a exceção {@link MethodArgumentNotValidException}, que é lançada quando ocorre um erro de validação
     * dos dados de entrada de um metodo de um controlador.
     * <p>
     * Esta exceção é comum em operações onde os dados fornecidos pelo cliente, através do corpo da requisição
     * ou parâmetros de URL,  não atendem aos requisitos de validação definidos pelas anotações do Bean Validation,
     * como `@NotNull`, `@NotBlank`, `@Size`, etc.
     * </p>
     * <p>
     * O metodo captura a exceção, extrai os detalhes dos erros de validação e os encapsula em uma lista de
     * objetos {@link ValidationErrorDetails}. Cada objeto `ValidationErrorDetails` contém informações específicas
     * sobre um erro de validação, como o campo que causou o erro, a mensagem de erro e o código de erro.
     * </p>
     * <p>
     * Em seguida, o metodo retorna uma resposta HTTP com o status 400 (Bad Request) e a lista de erros de
     * validação no corpo da resposta, no formato JSON. Essa resposta informa ao cliente quais campos da requisição
     * são inválidos e quais são as mensagens de erro correspondentes, permitindo que o cliente corrija os erros e
     * reenvie a requisição.
     * </p>
     *
     * @param exception A exceção {@link MethodArgumentNotValidException} que contém os detalhes dos erros de validação.
     * @param request   O objeto {@link WebRequest} que fornece informações adicionais sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo uma lista de {@link ValidationErrorDetails} e o status HTTP 400 (Bad Request).
     * @see ValidationErrorDetails
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Schema(description = "Manipula a exceção MethodArgumentNotValidException, lançada em caso de erros de validação.")
    public ResponseEntity<List<ValidationErrorDetails>> handleValidationException(MethodArgumentNotValidException exception,
                                                                                  WebRequest request) {
        List<ValidationErrorDetails> errors = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationErrorDetails(
                    LocalDateTime.now(),
                    error.getDefaultMessage(), // Mensagem mais amigável do Bean Validation
                    request.getDescription(false),
                    "METHOD_ARGUMENT_NOT_VALID_ERROR",
                    error.getField() // Nome do campo com erro
            ));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
     * e o status HTTP 404 (Not Found).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @Schema(description = "Manipula a exceção EntityNotFoundException, lançada quando uma entidade não é encontrada.")
    public ResponseEntity<List<ErrorDetails>> handleResourceNotFoundException(EntityNotFoundException exception,
                                                                              WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND"
        );

        return new ResponseEntity<>(List.of(errorDetails), HttpStatus.NOT_FOUND);
    }

    /**
     * Manipula a exceção {@link IllegalArgumentException}, que é lançada quando um argumento inválido
     * é passado para um metodo.
     * <p>
     * Esta exceção indica que os dados fornecidos pelo cliente na requisição não são válidos
     * para a operação solicitada. O metodo encapsula os detalhes do erro em um objeto
     * {@link ErrorDetails} e retorna uma resposta com status HTTP 400 (Bad Request),
     * indicando que a requisição não pôde ser processada devido a dados inválidos.
     * </p>
     *
     * @param exception  A exceção de argumento inválido, que contém a mensagem de erro a ser retornada ao cliente.
     * @param webRequest O objeto {@link WebRequest} que fornece informações adicionais sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo uma lista com os detalhes do erro encapsulados em {@link ErrorDetails}
     * e o status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @Schema(description = "Manipula a exceção IllegalArgumentException, lançada quando um argumento inválido é passado.")
    public ResponseEntity<List<ErrorDetails>> handleIllegalArgumentException(IllegalArgumentException exception,
                                                                             WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INVALID_ARGUMENT"
        );

        return new ResponseEntity<>(List.of(errorDetails), HttpStatus.BAD_REQUEST);
    }

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
     * e o status HTTP 409 (Conflict).
     */
    @ExceptionHandler(DuplicateEntryException.class)
    @Schema(description = "Manipula a exceção DuplicateEntryException, lançada quando há uma tentativa de inserir uma entrada duplicada.")
    public ResponseEntity<List<ErrorDetails>> handleDuplicateEntryException(DuplicateEntryException exception,
                                                                            WebRequest webRequest) {
        List<ErrorDetails> errors = new ArrayList<>();
        String[] mensagens = exception.getMessage().split("\\n");

        // Cria um ErrorDetails para cada mensagem de erro
        for (String mensagem : mensagens) {
            errors.add(new ErrorDetails(
                    LocalDateTime.now(),
                    mensagem.trim(), // Remove espaços em branco no início e no final da mensagem
                    webRequest.getDescription(false),
                    "DUPLICATE_ENTRY"
            ));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
}
