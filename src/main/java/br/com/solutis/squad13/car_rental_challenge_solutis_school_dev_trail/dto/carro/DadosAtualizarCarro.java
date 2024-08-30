package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record DadosAtualizarCarro(

        Long id,

        String nome,

        @Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "A placa deve seguir o formato ABC-1234")
        String placa,

        @Size(min = 17, max = 17, message = "O chassi deve ter 17 caracteres")
        String chassi,

        String cor,

        BigDecimal valorDiario,

        @Size(min = 1, message = "O carro deve ter pelo menos um acessório")
        List<Acessorio> acessorio,

        ModeloCarro modelo
) {
}
