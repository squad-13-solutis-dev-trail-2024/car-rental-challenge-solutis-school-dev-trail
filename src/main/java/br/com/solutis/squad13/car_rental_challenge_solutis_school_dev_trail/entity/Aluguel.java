package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Aluguel")
@Table(
        name = "tb_aluguel",
        schema = "db_car_rental_solutis"
)
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate dataPedido;

    @Column(nullable = false)
    private LocalDate dataEntrega;

    @Column(nullable = true)
    private LocalDate dataDevolucao;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;

    @OneToOne
    @JoinColumn(name = "apolice_seguro_id", nullable = false)
    private ApoliceSeguro apoliceSeguro;

    @Override
    public String toString() {
        return "Aluguel{id=" + id + ", dataPedido=" + dataPedido + ", dataEntrega=" + dataEntrega + ", dataDevolucao=" + dataDevolucao + ", valor=" + valor + ", motorista=" + motorista + ", carro=" + carro + ", apoliceSeguro=" + apoliceSeguro + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Aluguel aluguel = (Aluguel) o;
        return getId() != null && Objects.equals(getId(), aluguel.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}