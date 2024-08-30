package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    /**
     * Cria uma nova instância de {@code BadRequestException} com uma mensagem detalhada.
     * @param message A mensagem detalhada que descreve o motivo da solicitação inválida.
     */
    public BadRequestException(String message){
        super(message);
    }

    /**
     *Cria uma nova instância de {@code BadRequestException} com uma mensagem detalhada e uma causa.
     * @param message A mensagem detalhada que descreve o motivo da solicitação inválida
     * @param cause A causa que causou a exceção
     */

    public BadRequestException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Cria uma nova instância de {@code BadRequestException} com uma causa
     * @param cause A cause que causou a exceção
     */


    public BadRequestException(Throwable cause){
        super(cause);
    }

    /**
     * Construtor para criar uma nova instância de {@code BadRequestException}.
     */
    public BadRequestException(){
        super();
    }
}

