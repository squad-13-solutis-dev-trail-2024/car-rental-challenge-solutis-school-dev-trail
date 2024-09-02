package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.validation.annotations.Adulto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.NONE;

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
@Inheritance(strategy = JOINED)
@Schema(description = "Entidade abstrata que representa uma pessoa, servindo como base para outras entidades.")
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nome completo da pessoa.")
    private String nome;

    @Column(nullable = false)
    @Schema(description = "Endereço de email da pessoa.")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Data de nascimento da pessoa.")
    @Adulto(message = "Você não é maior de idade")
    private LocalDate dataNascimento;

    @Column(nullable = false)
    @Schema(description = "CPF da pessoa.")
    private String cpf;

    @Enumerated(STRING)
    @Schema(description = "Sexo da pessoa.")
    private Sexo sexo;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Schema(description = "Indica se a pessoa está ativa (true) ou inativa (false).")
    @Setter(NONE)
    private Boolean ativo;

    @CreationTimestamp
    @Setter(NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora de criação do registro.", accessMode = READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.", accessMode = READ_WRITE)
    private LocalDateTime lastUpdated;

    public void desativar() {
        this.ativo = false;
        this.lastUpdated = now();
    }

    public void ativar() {
        this.ativo = true;
        this.lastUpdated = now();
    }

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