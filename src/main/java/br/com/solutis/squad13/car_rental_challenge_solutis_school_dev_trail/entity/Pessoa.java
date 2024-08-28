package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Pessoa")
@Table(
        name = "tb_pessoa",
        schema = "db_car_rental_solutis",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_pessoa_cpf", columnNames = "cpf"),
                @UniqueConstraint(name = "uk_pessoa_email", columnNames = "email")
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private Date dataNascimento;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Override
    public String toString() {
        return "Pessoa{id=" + id + ", nome=" + nome + ", email=" + email + ", dataNascimento=" + dataNascimento + ", cpf=" + cpf + ", sexo=" + sexo + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Pessoa pessoa = (Pessoa) o;
        return getId() != null && Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}