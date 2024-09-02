package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_carrinho_alguel", schema = "db_car_rental_solutis")
public class CarrinhoAluguel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    @Schema(description = "Motorista que possui o carrinho de aluguel")
    private Motorista motorista;

    @ManyToMany
    @JoinTable(name = "tb_carrinho_aluguel_veiculo",joinColumns = @JoinColumn(name = "carrinho_id"), inverseJoinColumns = @JoinColumn(name = "veiculo_id"))
    @Schema(description = "Lista de veículos associados ao carrinho de aluguel")
    private List<Carro> veiculos = new ArrayList<>();

    @CreationTimestamp
    @Setter(NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora da criação do registro.", accessMode = READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.", accessMode = READ_WRITE)
    private LocalDateTime lastUpdated;

    @PrePersist
    protected void onCreate() {
        dataCreated = now();
    }

    // Metodo para adicionar um carro ao carrinho de aluguel
    public void adicionarCarro(Carro veiculo) {
        if (!veiculos.contains(veiculo)) {
            veiculos.add(veiculo);
        } else {
            throw new IllegalArgumentException("O veículo já está no carrinho.");
        }
    }

    // Metodo para retirar um carro do carrinho de aluguel
    public void retirarCarro(Carro veiculo) {
        if (veiculos.contains(veiculo)) {
            veiculos.remove(veiculo);
        } else {
            throw new IllegalArgumentException("O veículo não está no carrinho.");
        }
    }
}
