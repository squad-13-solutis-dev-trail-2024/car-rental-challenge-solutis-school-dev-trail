package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
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

@Schema(description = "Dados para atualizar um carro existente.")
public record DadosAtualizarCarro(

        @NotNull(message = "ID é obrigatório")
        @Schema(description = "ID do carro a ser atualizado.", example = "1")
        Long id,

        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @NotBlank(message = "A placa é obrigatória")
        @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "Placa inválida. Formato esperado: AAA1234")
        @Schema(description = "Placa do carro.")
        String placa,

        @NotBlank(message = "O chassi é obrigatório")
        @Size(min = 17, max = 17, message = "O chassi deve ter 17 caracteres")
        @Schema(description = "Chassi do carro.")
        String chassi,

        @NotBlank(message = "A cor é obrigatória")
        @Schema(description = "Cor do carro.")
        String cor,

        @NotNull(message = "O valorTotalParcial da diária é obrigatório")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valorTotalParcial diário deve ser maior que zero")
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiario,

        @NotNull(message = "A lista de acessórios é obrigatória")
        @NotEmpty(message = "A lista de acessórios não pode estar vazia")
        @Schema(description = "Lista de acessórios do carro.")
        List<Acessorio> acessorio,

        @NotNull(message = "O modelo do carro é obrigatório")
        @Schema(description = "Modelo do carro.")
        ModeloCarro modelo,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        @Schema(description = "Data e hora da última atualização do registro do motorista.", accessMode = READ_WRITE)
        LocalDateTime lastUpdated
) {
}
