package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Carro")
@Table(
        name = "tb_carro",
        schema = "db_car_rental_solutis",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_carro_placa", columnNames = "placa"),
                @UniqueConstraint(name = "uk_carro_chassi", columnNames = "chassi")
        }
)
@Schema(description = "Entidade que representa um carro.")
public class Carro {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nome do modelo do carro.", example = "Corolla")
    private String nome;

    @Column(nullable = false)
    @Schema(description = "Placa do carro.", example = "ABC1234")
    private String placa;

    @Column(nullable = false)
    @Schema(description = "Chassi do carro.", example = "1HGBH41JXMN109186")
    private String chassi;

    @Column(nullable = false)
    @Schema(description = "Cor do carro.", example = "Preto")
    private String cor;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Schema(description = "Indica se o carro está disponível para aluguel (true) ou indisponível (false).", example = "true")
    private boolean disponivel;

    @Column(precision = 10, scale = 2, nullable = false)
    @Schema(description = "Valor da diária do aluguel do carro.", example = "150.00")
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
    @JsonIgnore
    @ManyToMany
    @Column(nullable = false)
    @Setter(NONE)
    @JoinTable(name = "tb_carro_acessorio", joinColumns = @JoinColumn(name = "id_carro"), inverseJoinColumns = @JoinColumn(name = "id_acessorio"))
    @Schema(description = "Lista de acessórios do carro.")
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
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "modelo_carro_id", nullable = false)
    @Schema(description = "Modelo do carro.")
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
    @OneToMany(mappedBy = "carro", cascade = ALL, orphanRemoval = true)
    @Setter(NONE)
    @Schema(description = "Lista de aluguéis associados a este carro.")
    private List<Aluguel> alugueis = new ArrayList<>(); // Inicializa a lista de aluguéis com uma lista vazia pois um carro pode não ter aluguéis associados

    @CreationTimestamp
    @Setter(NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora de criação do registro.", accessMode = READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.", accessMode = READ_WRITE)
    private LocalDateTime lastUpdated;

    public void disponibilizarAluguel() {
        this.disponivel = true;
    }

    public void bloquearAluguel() {
        this.disponivel = false;
    }

    public Carro(DadosCadastroCarro dadosCadastroCarro) {
        this.nome = dadosCadastroCarro.nome();
        this.placa = dadosCadastroCarro.placa();
        this.chassi = dadosCadastroCarro.chassi();
        this.cor = dadosCadastroCarro.cor();
        this.valorDiaria = dadosCadastroCarro.valorDiario();
        this.acessorios = dadosCadastroCarro.acessorio();
        this.modelo = dadosCadastroCarro.modelo();
        this.disponivel = true;
        this.dataCreated = now();
        this.lastUpdated = now();
    }

    public void atualizar(@Valid DadosAtualizarCarro dadosAtualizarCarro) {
        ofNullable(dadosAtualizarCarro.nome()).ifPresent(this::setNome);
        ofNullable(dadosAtualizarCarro.placa()).ifPresent(this::setPlaca);
        ofNullable(dadosAtualizarCarro.chassi()).ifPresent(this::setChassi);
        ofNullable(dadosAtualizarCarro.cor()).ifPresent(this::setCor);
        ofNullable(dadosAtualizarCarro.valorDiario()).ifPresent(this::setValorDiaria);
        ofNullable(dadosAtualizarCarro.acessorio()).ifPresent(this::adicionarAcessorios);
        ofNullable(dadosAtualizarCarro.modelo()).ifPresent(this::setModelo);
        this.lastUpdated = now();
    }

    public void adicionarAcessorio(Acessorio acessorio) {
        this.acessorios.add(acessorio);
    }

    public void removerAcessorio(Acessorio acessorio) {
        this.acessorios.remove(acessorio);
    }

    public void adicionarAcessorios(List<Acessorio> acessorios) {
        this.acessorios.addAll(acessorios);
    }

    public void removerAcessorios(List<Acessorio> acessorios) {
        this.acessorios.removeAll(acessorios);
    }

    public void adicionarAluguel(Aluguel aluguel) {
        this.alugueis.add(aluguel);
    }

    public void removerAluguel(Aluguel aluguel) {
        this.alugueis.remove(aluguel);
    }

    public void adicionarAlugueis(List<Aluguel> alugueis) {
        this.alugueis.addAll(alugueis);
    }

    public void removerAlugueis(List<Aluguel> alugueis) {
        this.alugueis.removeAll(alugueis);
    }

    @Override
    public String toString() {
        return "Carro{id=" + id + ", placa='" + placa + '\'' + ", chassi='" + chassi + '\'' + ", cor='" + cor + '\'' + ", isDisponivelParaAluguel=" + disponivel + ", valorDiaria=" + valorDiaria + ", acessorios=" + acessorios + ", modeloCarro=" + modelo + ", alugueis=" + alugueis + '}';
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