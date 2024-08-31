package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dados necessários para cadastrar um novo carro.")
public record DadosCadastroCarro(

        @NotBlank(message = "O nome do carro é obrigatório")
        @Schema(description = "Nome do modelo do carro.", example = "Corolla")
        String nome,

        @NotBlank(message = "A placa do carro é obrigatória")
        @Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "A placa deve seguir o formato ABC-1234")
        @Schema(description = "Placa do carro.", example = "ABC-1234")
        String placa,

        @NotBlank(message = "O chassi do carro é obrigatório")
        @Size(min = 17, max = 17, message = "O chassi deve ter 17 caracteres")
        @Schema(description = "Chassi do carro.", example = "1HGBH41JXMN109186")
        String chassi,

        @NotBlank(message = "A cor do carro é obrigatória")
        @Schema(description = "Cor do carro.", example = "Preto")
        String cor,

        @NotNull(message = "O valor diário do aluguel é obrigatório")
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = "O valor diário deve ser maior que zero"
        )
        @Schema(description = "Valor da diária do aluguel do carro.", example = "150.00")
        BigDecimal valorDiario,

        @NotNull(message = "A lista de acessórios não pode ser nula")
        @Size(min = 1, message = "O carro deve ter pelo menos um acessório")
        @Schema(description = "Lista de acessórios incluídos no carro.")
        List<Acessorio> acessorio,

        @NotNull(message = "O modelo do carro é obrigatório")
        @Schema(description = "Modelo do carro ao qual este carro pertence.")
        ModeloCarro modelo
) {
}
