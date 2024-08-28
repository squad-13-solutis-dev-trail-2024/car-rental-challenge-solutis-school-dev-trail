package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Fabricante")
@Table(
        name = "tb_fabricante",
        schema = "db_car_rental_solutis"
)
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "fabricante",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    @Column(nullable = true)
    private List<ModeloCarro> modelos = new ArrayList<>();

    @Override
    public String toString() {
        return "Fabricante{id=" + id + ", nome=" + nome + ", modelos=" + modelos + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Fabricante that = (Fabricante) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
