package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Carro")
@Table(
        name = "tb_carro",
        schema = "db_car_rental_solutis"
)
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String placa;

    @Column(nullable = false)
    private String chassi;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean ativo;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    /**
     * Lista de acessórios associados a este carro.
     * <p>
     * Representa a relação muitos-para-muitos entre {@link Carro} e {@link Acessorio}.
     * Um carro pode ter vários acessórios e um acessório pode estar presente em vários carros.
     * </p>
     * <p>
     * A anotação `@ManyToMany` indica que a relação é de muitos-para-muitos.
     * </p>
     * <p>
     * A anotação `@JoinTable` define a tabela de junção `tb_carro_acessorio`, que gerencia a relação
     * entre carros e acessórios.
     * </p>
     * <ul>
     *     <li>`name = "tb_carro_acessorio"`: Especifica o nome da tabela de junção.</li>
     *     <li>`joinColumns = @JoinColumn(name = "id_carro")`: Define a coluna `id_carro` na tabela
     *     de junção como a chave estrangeira que referencia a tabela `tb_carro`.</li>
     *     <li>`inverseJoinColumns = @JoinColumn(name = "id_acessorio")`: Define a coluna
     *     `id_acessorio` na tabela de junção como a chave estrangeira que referencia a tabela
     *     `tb_acessorio`.</li>
     * </ul>
     * <p>
     * A anotação `@Column(nullable = false)` indica que a lista de acessórios não pode ser nula,
     * ou seja, todos carro deve ter pelo menos um acessório associado.
     * </p>
     * <p>
     * A anotação `@Setter(AccessLevel.NONE)` impede a modificação direta da lista de acessórios.
     * A manipulação da lista deve ser feita por meio de métodos específicos na classe `Carro`,
     * garantindo a consistência dos dados.
     * </p>
     */
    @ManyToMany
    @JoinTable(
            name = "tb_carro_acessorio",
            joinColumns = @JoinColumn(name = "id_carro"),
            inverseJoinColumns = @JoinColumn(name = "id_acessorio")
    )
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private List<Acessorio> acessorios;

    /**
     * Modelo do carro.
     * <p>
     * Representa o lado "muitos" no relacionamento muitos-para-um com a entidade {@link ModeloCarro}.
     * Cada {@link Carro} está associado a um único {@link ModeloCarro}.
     * O atributo `modelo_carro_id` na tabela `tb_carro` atua como chave estrangeira, referenciando
     * a tabela `tb_modelo_carro`.
     * </p>
     * <p>
     * O carregamento do modelo do carro é feito sob demanda (lazy) para otimizar o desempenho
     * e o consumo de memória, carregando os dados do {@link ModeloCarro} apenas quando necessário.
     * </p>
     * <p>
     * A anotação `@JoinColumn` define a chave estrangeira que relaciona as tabelas `tb_carro` e
     * `tb_modelo_carro`, garantindo a integridade referencial e evitando que um carro seja
     * associado a um modelo inexistente.
     * </p>
     *
     * @see ModeloCarro
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_carro_id", nullable = false)
    private ModeloCarro modelo;

    /**
     * Lista de aluguéis associados a este carro.
     * <p>
     * Representa o lado "um" no relacionamento um-para-muitos com a entidade {@link Aluguel}.
     * Um {@link Carro} pode estar associado a zero ou mais {@link Aluguel}es.
     * </p>
     *
     * <p>
     * A anotação `cascade = CascadeType.ALL` indica que as operações de persistência
     * (como salvar, atualizar e remover) realizadas em um {@link Carro} serão
     * automaticamente propagadas para os seus {@link Aluguel}es associados.
     * </p>
     *
     * <p>
     * A anotação `orphanRemoval = true` indica que se um {@link Aluguel} for removido
     * da lista `alugueis`, ele também será removido do banco de dados.
     * </p>
     *
     * @see Aluguel
     */
    @OneToMany(
            mappedBy = "carro",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Setter(AccessLevel.NONE)
    private List<Aluguel> alugueis = new ArrayList<>();

    public void disponibilizarAluguel() {
        this.ativo = true;
    }

    public void bloquearAluguel() {
        this.ativo = false;
    }

    @Override
    public String toString() {
        return "Carro{id=" + id + ", placa='" + placa + '\'' + ", chassi='" + chassi + '\'' + ", cor='" + cor + '\'' + ", isDisponivelParaAluguel=" + ativo + ", valorDiaria=" + valorDiaria + ", acessorios=" + acessorios + ", modeloCarro=" + modelo + ", alugueis=" + alugueis + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Carro carro = (Carro) o;
        return getId() != null && Objects.equals(getId(), carro.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}