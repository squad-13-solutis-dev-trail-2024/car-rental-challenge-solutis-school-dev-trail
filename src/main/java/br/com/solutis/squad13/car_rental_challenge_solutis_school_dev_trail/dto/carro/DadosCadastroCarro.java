package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record DadosCadastroCarro (
        @NotBlank(message = "O nome do carro é obrigatório")
        String nome,

        @NotBlank(message = "A placa do carro é obrigatória")
        @Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "A placa deve seguir o formato ABC-1234")
        String placa,

        @NotBlank(message = "O chassi do carro é obrigatório")
        @Size(min = 17, max = 17, message = "O chassi deve ter 17 caracteres")
        String chassi,

        @NotBlank(message = "A cor do carro é obrigatória")
        String cor,

        @NotNull(message = "O valor diário do aluguel é obrigatório")
        BigDecimal valorDiario,

        @NotNull(message = "A lista de acessórios não pode ser nula")
        @Size(min = 1, message = "O carro deve ter pelo menos um acessório")
        List<Acessorio> acessorio,

        @NotNull(message = "O modelo do carro é obrigatório")
        ModeloCarro modelo
) {
}
