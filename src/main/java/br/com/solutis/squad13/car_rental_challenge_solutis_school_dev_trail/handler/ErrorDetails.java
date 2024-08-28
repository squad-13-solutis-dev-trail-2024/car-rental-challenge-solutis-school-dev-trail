package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.handler;

import java.time.LocalDateTime;

public record ErrorDetails(
        LocalDateTime timestamp,
        String field,
        String details,
        String error
) {
}
