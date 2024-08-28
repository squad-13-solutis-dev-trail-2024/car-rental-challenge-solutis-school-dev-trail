package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Motorista")
@Table(
        name = "tb_motorista",
        schema = "db_car_rental_solutis"
)
@PrimaryKeyJoinColumn(name = "pessoa_id")
public class Motorista extends Pessoa {

    private String numeroCNH;

    @OneToMany(mappedBy = "motorista")
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private List<Aluguel> alugueis;

    @Override
    public String toString() {
        return "Motorista{id=" + getId() + ", numeroCNH=" + numeroCNH + ", alugueis=" + alugueis + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Motorista motorista = (Motorista) o;
        return getId() != null && Objects.equals(getId(), motorista.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

