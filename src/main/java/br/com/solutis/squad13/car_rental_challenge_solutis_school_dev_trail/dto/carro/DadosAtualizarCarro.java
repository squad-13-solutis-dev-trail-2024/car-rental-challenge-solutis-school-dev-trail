package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record DadosAtualizarCarro(
        Long id,
        String nome,
        String placa,
        @Size(min = 17, max = 17, message = "O chassi deve ter 17 caracteres")
        String chassi,
        String cor,
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = "O valor diário deve ser maior que zero"
        )
        BigDecimal valorDiario,
        List<Acessorio> acessorio,
        ModeloCarro modelo
) {
}
