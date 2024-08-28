package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Classe que representa um Motorista no sistema de aluguel de carros.
 * <p>
 * A classe herda os atributos comuns de {@link Pessoa} e inclui campos específicos do motorista,
 * como número da CNH, status de atividade, data de criação e última atualização.
 * É mapeada para a tabela 'tb_motorista' no banco de dados.
 * </p>
 *
 * @see Pessoa
 * @see Funcionario
 */
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
    private LocalDate dataNascimento;
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