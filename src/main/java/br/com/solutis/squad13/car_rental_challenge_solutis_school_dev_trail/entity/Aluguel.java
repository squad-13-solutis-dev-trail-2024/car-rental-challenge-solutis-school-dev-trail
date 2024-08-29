package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
    private LocalDate dataPedido;

    @Column(nullable = false)
    private LocalDate dataEntrega;

    @Column(nullable = false)
    private LocalDate dataDevolucaoPrevista;

    private LocalDate dataDevolucaoEfetiva;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    /**
     * Motorista que realizou este aluguel.
     * <p>
     * Representa o lado "muitos" no relacionamento um-para-muitos com a entidade {@link Motorista}.
     * Cada {@link Aluguel} está associado a um único {@link Motorista}.
     * O atributo `motorista_id` na tabela `tb_aluguel` atua como chave estrangeira, referenciando
     * a tabela `tb_motorista`.
     * </p>
     *
     * @see Motorista
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    @JsonIgnore
    private Motorista motorista;

    /**
     * Carro associado a este aluguel.
     * <p>
     * Representa o lado "muitos" no relacionamento um-para-muitos com a entidade {@link Carro}.
     * Cada {@link Aluguel} está obrigatoriamente associado a um único {@link Carro}.
     * O atributo `carro_id` na tabela `tb_aluguel` atua como chave estrangeira, referenciando
     * a tabela `tb_carro`.
     * </p>
     *
     * @see Carro
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    @JsonIgnore
    private Carro carro;

    /**
     * Apólice de seguro associada ao aluguel.
     * <p>
     * Este campo estabelece uma relação um-para-um com a {@link ApoliceSeguro}. Cada aluguel deve
     * ter uma apólice de seguro associada, e a apólice é obrigatória. A anotação {@code @JoinColumn}
     * define a coluna de junção na tabela 'tb_aluguel' que referencia a apólice de seguro.
     * </p>
     *
     * @see ApoliceSeguro
     */
    @OneToOne
    @JoinColumn(name = "apolice_seguro_id", nullable = false)
    @JsonIgnore
    private ApoliceSeguro apoliceSeguro;

    @Override
    public String toString() {
        return "Aluguel{id=" + id + ", dataPedido=" + dataPedido + ", dataEntrega=" + dataEntrega + ", dataDevolucao=" + dataDevolucaoPrevista + ", valor=" + valor + ", motorista=" + motorista + ", carro=" + carro + ", apoliceSeguro=" + apoliceSeguro + '}';
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