package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Optional.ofNullable;


/**
 * Classe que representa um Motorista no sistema de aluguel de carros.
 * <p>
 * Esta classe herda os atributos comuns de {@link Pessoa}, adicionando campos específicos
 * que identificam o motorista, como o número da CNH, status de atividade, data de criação
 * e última atualização. A classe é mapeada para a tabela 'tb_motorista' no banco de dados,
 * com a chave primária herdada da classe {@link Pessoa}.
 * </p>
 *
 * <p>
 * A relação entre {@link Motorista} e {@link Aluguel} é definida como:
 * <ul>
 *     <li>Um {@link Motorista} pode ter vários {@link Aluguel} associados a ele.</li>
 *     <li>Cada {@link Aluguel} é obrigatoriamente associado a um único {@link Motorista}.</li>
 * </ul>
 * Esta relação é mapeada utilizando a anotação {@code @OneToMany} no lado de {@link Motorista},
 * indicando que o campo `alugueis` contém a lista de todos os aluguéis associados ao motorista.
 * </p>
 *
 * <p>
 * A classe inclui campos para:
 * <ul>
 *     <li>{@code numeroCNH} - O número da Carteira Nacional de Habilitação (CNH) do motorista.</li>
 *     <li>{@code ativo} - Um campo booleano que indica se o motorista está ativo no sistema.</li>
 *     <li>{@code dataCreated} - A data e hora em que o registro do motorista foi criado.</li>
 *     <li>{@code lastUpdated} - A data e hora da última atualização do registro do motorista.</li>
 * </ul>
 * </p>
 *
 * @see Pessoa
 * @see Aluguel
 * @see Funcionario
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Motorista")
@Table(
        name = "tb_motorista",
        schema = "db_car_rental_solutis"
)
@PrimaryKeyJoinColumn(name = "pessoa_id")
public class Motorista extends Pessoa {

    @Column(nullable = false)
    private String numeroCNH;

    /**
     * Lista de aluguéis realizados por este motorista.
     * <p>
     * Representa o lado "um" no relacionamento um-para-muitos com a entidade {@link Aluguel}.
     * Cada {@link Motorista} pode estar associado a múltiplos {@link Aluguel}es.
     * A lista é carregada de forma "lazy", ou seja, somente quando for acessada.
     * </p>
     *
     * @see Aluguel
     */
    @OneToMany(mappedBy = "motorista", fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private List<Aluguel> alugueis = new ArrayList<>();

    public Motorista(@Valid DadosCadastroMotorista dadosCadastroMotorista) {
        this.setNome(dadosCadastroMotorista.nome());
        this.setDataNascimento(dadosCadastroMotorista.dataNascimento());
        this.setCpf(dadosCadastroMotorista.cpf());
        this.setEmail(dadosCadastroMotorista.email());
        this.setSexo(dadosCadastroMotorista.sexo());
        this.setLastUpdated(LocalDateTime.now());
        this.setAtivo(true);
        this.numeroCNH = dadosCadastroMotorista.numeroCNH();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        ofNullable(dadosAtualizacaoMotorista.nome()).ifPresent(this::setNome);
        ofNullable(dadosAtualizacaoMotorista.dataNascimento()).ifPresent(this::setDataNascimento);
        ofNullable(dadosAtualizacaoMotorista.email()).ifPresent(this::setEmail);
        ofNullable(dadosAtualizacaoMotorista.sexo()).ifPresent(this::setSexo);
        ofNullable(dadosAtualizacaoMotorista.numeroCNH()).ifPresent(value -> this.numeroCNH = value);
        ofNullable(dadosAtualizacaoMotorista.cpf()).ifPresent(this::setCpf);
        this.setLastUpdated(LocalDateTime.now());
    }

    public void adicionarAluguel(Aluguel aluguel) {
        this.alugueis.add(aluguel);
    }

    public void adicionarListaAlugueis(List<Aluguel> alugueis) {
        this.alugueis.addAll(alugueis);
    }

    @Override
    public String toString() {
        return "Motorista{id=" + getId() + ", numeroCNH=" + numeroCNH + ", alugueis=" + alugueis + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Motorista motorista = (Motorista) o;
        return getId() != null && Objects.equals(getId(), motorista.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

