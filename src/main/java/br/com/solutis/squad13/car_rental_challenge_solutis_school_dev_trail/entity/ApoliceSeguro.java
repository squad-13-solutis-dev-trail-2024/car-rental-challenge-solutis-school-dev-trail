package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ApoliceSeguro")
@Table(
        name = "tb_apolice_seguro",
        schema = "db_car_rental_solutis"
)
public class ApoliceSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorFranquia;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean protecaoTerceiro;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean protecaoCausasNaturais;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean protecaoRoubo;

    /**
     * Aluguel associado à apólice de seguro.
     * <p>
     * Este campo estabelece uma relação um-para-um com a {@link Aluguel}. A anotação {@code @OneToOne}
     * com {@code mappedBy = "apoliceSeguro"} define que a apólice de seguro é o lado inverso da relação,
     * e a coluna de junção é gerenciada pela classe {@link Aluguel}.
     * </p>
     *
     * @see Aluguel
     */
    @OneToOne(mappedBy = "apoliceSeguro")
    @JoinColumn(nullable = false)
    private Aluguel aluguel;

    @Override
    public String toString() {
        return "ApoliceSeguro{id=" + id + ", valorFranquia=" + valorFranquia + ", protecaoTerceiro=" + protecaoTerceiro + ", protecaoCausasNaturais=" + protecaoCausasNaturais + ", protecaoRoubo=" + protecaoRoubo + ", aluguel=" + aluguel + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ApoliceSeguro that = (ApoliceSeguro) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
