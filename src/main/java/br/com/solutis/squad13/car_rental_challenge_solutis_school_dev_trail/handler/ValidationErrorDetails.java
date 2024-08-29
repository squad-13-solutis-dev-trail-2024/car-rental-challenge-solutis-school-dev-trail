package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ValidationErrorDetails extends ErrorDetails {
    private String field;

    public ValidationErrorDetails(LocalDateTime timestamp, String message, String details, String errorCode, String field) {
        super(timestamp, message, details, errorCode);
        this.field = field;
    }
}
