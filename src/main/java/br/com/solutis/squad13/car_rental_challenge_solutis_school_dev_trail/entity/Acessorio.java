package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "Acessorio")
@Table(
        name = "tb_acessorio",
        schema = "db_car_rental_solutis"
)
public class Acessorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DescricaoAcessorio descricao;

    @ManyToMany(mappedBy = "acessorios")
    @Column(nullable = true)
    private Set<Carro> carros;

    @Override
    public String toString() {
        return "Acessorio{id=" + id + ", descricao=" + descricao + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Acessorio acessorio = (Acessorio) o;
        return getId() != null && Objects.equals(getId(), acessorio.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}


