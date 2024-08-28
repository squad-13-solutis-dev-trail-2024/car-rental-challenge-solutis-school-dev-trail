package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Long id;

    @Column(nullable = false, length = 7)
    private String placa;

    @Column(nullable = false, length = 17)
    private String chassi;

    @Column(nullable = false, length = 50)
    private String cor;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDisponivelParaAluguel;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    @ManyToMany
    @JoinTable(
            name = "tb_carro_acessorio",
            joinColumns = @JoinColumn(name = "id_carro"),
            inverseJoinColumns = @JoinColumn(name = "id_acessorio")
    )
    @Column(nullable = false)
    private List<Acessorio> acessorios;

    @ManyToOne
    @JoinColumn(name = "modelo_carro_id", nullable = false)
    private ModeloCarro modeloCarro;

    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aluguel> alugueis = new ArrayList<>();


    public void disponibilizarAluguel() {
        this.isDisponivelParaAluguel = true;
    }

    public void bloquearAluguel() {
        this.isDisponivelParaAluguel = false;
    }

    @Override
    public String toString() {
        return "Carro{id=" + id + ", placa='" + placa + '\'' + ", chassi='" + chassi + '\'' + ", cor='" + cor + '\'' + ", isDisponivelParaAluguel=" + isDisponivelParaAluguel + ", valorDiaria=" + valorDiaria + ", acessorios=" + acessorios + ", modeloCarro=" + modeloCarro + ", alugueis=" + alugueis + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Carro carro = (Carro) o;
        return getId() != null && Objects.equals(getId(), carro.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}