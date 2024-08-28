package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "Carro")
@Table(
        name = "tb_carro",
        schema = "db_car_rental_solutis"
)
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "id_modelo", nullable = false)
    private Long idModelo;

    @Column(nullable = false, length = 7)
    private String placa;

    @Column(nullable = false, length = 17)
    private String chassi;

    @Column(nullable = false, length =   50)
    private String cor;

    @Column
    private boolean isDisponivelParaAluguel;

    public void tornarDisponivel() {
        this.isDisponivelParaAluguel = true;
    }

    public void bloquearAluguel() {
        this.isDisponivelParaAluguel = false;
    }

    @Column(name = "valorDiaria", nullable = false, precision = 10, scale = 2)
    private Double valorDiaria;

    @ManyToMany
    @JoinTable(
            name = "tb_carro_acessorio",
            joinColumns = @JoinColumn(name = "id_carro"),
            inverseJoinColumns = @JoinColumn(name = "id_acessorio")
    )
    private List<Acessorio> acessorios;
}
