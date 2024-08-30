package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "ModeloCarro")
@Table(
        name = "tb_modelo_carro",
        schema = "db_car_rental_solutis"
)
@Schema(description = "Entidade que representa um modelo de carro.")
public class ModeloCarro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Descrição do modelo de carro.", example = "Corolla")
    private String descricao;

    /**
     * Fabricante ao qual este modelo de carro pertence.
     * <p>
     * A relação entre {@link ModeloCarro} e {@link Fabricante} é representada aqui,
     * onde cada modelo de carro está associado a um único fabricante. A anotação
     * {@code @ManyToOne} indica que vários modelos podem referenciar o mesmo fabricante,
     * mas cada modelo tem um único fabricante.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "fabricante_id", nullable = false)
    @Schema(description = "Fabricante do modelo de carro.")
    private Fabricante fabricante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Categoria do modelo de carro.", example = "SEDAN_COMPACTO")
    private Categoria categoria;

    /**
     * Lista de carros específicos que são deste modelo.
     * <p>
     * A relação entre {@link ModeloCarro} e {@link Carro} é representada aqui,
     * onde um modelo pode estar associado a vários carros específicos. A anotação
     * {@code @OneToMany} indica que a lista de carros é gerenciada pelo modelo.
     * A cascade {@code CascadeType.ALL} garante que todas as operações em {@link ModeloCarro}
     * (inserção, atualização, remoção) sejam refletidas nos seus carros associados.
     * O atributo {@code orphanRemoval = true} assegura que os carros sem modelo
     * sejam removidos do banco de dados.
     * </p>
     */
    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    @Schema(description = "Lista de carros específicos deste modelo.")
    private List<Carro> carros = new ArrayList<>(); // Inicializa a lista, pois a entidade não depende de carro para existir

    @Override
    public String toString() {
        return "ModeloCarro{id=" + id + ", descricao=" + descricao + ", fabricante=" + fabricante + ", categoria=" + categoria + ", carros=" + carros + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ModeloCarro that = (ModeloCarro) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
