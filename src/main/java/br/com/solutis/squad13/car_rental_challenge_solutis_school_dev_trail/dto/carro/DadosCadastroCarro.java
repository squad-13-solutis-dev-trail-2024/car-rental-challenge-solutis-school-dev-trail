package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.Chassi;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.Placa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;

@Schema(description = "Dados necessários para cadastrar um novo carro.")
public record DadosCadastroCarro(

        @NotBlank(message = "O nome do carro é obrigatório")
        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @NotBlank(message = "A placa do carro é obrigatória")
        @Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "A placa deve seguir o formato ABC-1234")
        @Schema(description = "Placa do carro.")
        @Placa(message = "Placa inválida")
        String placa,

        @NotBlank(message = "O chassi do carro é obrigatório")
        @Size(min = 17, max = 17, message = "O chassi deve ter 17 caracteres")
        @Schema(description = "Chassi do carro.")
        @Chassi(message = "Chassi inválido")
        String chassi,

        @NotBlank(message = "A cor do carro é obrigatória")
        @Schema(description = "Cor do carro.")
        String cor,

        @NotNull(message = "O valorTotalParcial diário do aluguel é obrigatório")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valorTotalParcial diário deve ser maior que zero")
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiario,

        @NotNull(message = "A lista de acessórios não pode ser nula")
        @Size(min = 1, message = "O carro deve ter pelo menos um acessório")
        @Schema(description = "Lista de acessórios incluídos no carro.")
        List<Acessorio> acessorio,

        @NotNull(message = "O modelo do carro é obrigatório")
        @Schema(description = "Modelo do carro ao qual este carro pertence.")
        ModeloCarro modelo,

        @CreationTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
        @Schema(description = "Data e hora de criação do registro do motorista.", accessMode = READ_ONLY)
        LocalDateTime dataCreated,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        @Schema(description = "Data e hora da última atualização do registro do motorista.", accessMode = READ_WRITE)
        LocalDateTime lastUpdated
) {
}
