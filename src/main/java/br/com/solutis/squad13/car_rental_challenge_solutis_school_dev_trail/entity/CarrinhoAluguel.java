package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity(name = "CarrinhoAluguel")
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
    @JoinTable(name = "tb_carrinho_aluguel_veiculo", joinColumns = @JoinColumn(name = "carrinho_id"), inverseJoinColumns = @JoinColumn(name = "veiculo_id"))
    @Schema(description = "Lista de veículos associados ao carrinho de aluguel")
    private Set<Carro> veiculos = new HashSet<>();

    /**
     * Aluguel associado a este carrinho.
     * <p>
     * Representa o lado "um" no relacionamento um-para-um com a entidade {@link Aluguel}.
     * Cada {@link CarrinhoAluguel} está associado a um único {@link Aluguel}.
     * </p>
     *
     * @see Aluguel
     */
    @OneToOne(mappedBy = "carrinhoAluguel")
    @Schema(description = "Aluguel associado ao carrinho.")
    private Aluguel aluguel;

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

    @Override
    public String toString() {
        return "CarrinhoAluguel{" +
                "dataCreated=" + dataCreated +
                ", id=" + id +
                ", lastUpdated=" + lastUpdated +
                ", motorista=" + motorista +
                ", veiculos=" + veiculos +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        CarrinhoAluguel that = (CarrinhoAluguel) o;

        if (!id.equals(that.id)) return false;
        if (!motorista.equals(that.motorista)) return false;
        if (veiculos.size() != that.veiculos.size()) return false;

        for (Carro carro : veiculos) {
            if (!that.veiculos.contains(carro)) {
                return false; // Se um carro não estiver presente no outro Set, são diferentes
            }
        }

        return true; // Se todos os carros estiverem presentes em ambos os Sets, são iguais
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = id.hashCode();

        hash *= prime + motorista.hashCode();
        hash *= prime + veiculos.size();
        hash *= veiculos.stream().mapToInt(carro -> prime + carro.hashCode()).reduce(1, (a, b) -> a * b);

        if (hash < 0) hash *= -1;

        return hash;
    }
}
