package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Fabricante")
@Table(name = "tb_fabricante", schema = "db_car_rental_solutis")
@Schema(description = "Entidade que representa um fabricante de carros.")
public class Fabricante {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nome do fabricante.", example = "Toyota")
    private String nome;

    /**
     * Lista de modelos de carros fabricados por este fabricante.
     * <p>
     * A relação entre {@link Fabricante} e {@link ModeloCarro} é representada aqui,
     * onde um fabricante pode estar associado a vários modelos de carros. A anotação
     * {@code @OneToMany} indica que a lista de modelos é gerenciada pelo fabricante.
     * A cascade {@code CascadeType.ALL} garante que todas as operações em {@link Fabricante}
     * (inserção, atualização, remoção) sejam refletidas nos seus modelos associados.
     * O atributo {@code orphanRemoval = true} assegura que os modelos sem fabricante
     * sejam removidos do banco de dados.
     * </p>
     */
    @OneToMany(mappedBy = "fabricante", cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @Setter(AccessLevel.NONE)
    @Schema(description = "Lista de modelos de carros produzidos pelo fabricante.")
    private List<ModeloCarro> modelos = new ArrayList<>(); // Inicializa a lista, pois a entidade não depende de modelo para existir

    public Fabricante(@NotBlank(message = "O nome do fabricante é obrigatório.") String nome) {
        this.nome = nome;
    }

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
