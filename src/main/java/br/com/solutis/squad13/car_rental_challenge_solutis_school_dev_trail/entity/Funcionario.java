package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Classe que representa um Funcionário no sistema de aluguel de carros.
 * <p>
 * A classe herda os atributos comuns de {@link Pessoa} e inclui campos específicos do funcionário,
 * como a matrícula do funcionário.
 * É mapeada para a tabela 'tb_funcionario' no banco de dados.
 * </p>
 *
 * @see Pessoa
 * @see Motorista
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Funcionário")
@Table(
        name = "tb_funcionario",
        schema = "db_car_rental_solutis",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_funcionario_matricula", columnNames = "matricula")
        }
)
@PrimaryKeyJoinColumn(name = "pessoa_id")
public class Funcionario extends Pessoa {
    private String matricula;

    @Override
    public String toString() {
        return "Funcionario{id=" + getId() + ", matricula=" + matricula + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Funcionario that = (Funcionario) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

