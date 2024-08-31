package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Aluguel")
@Table(
        name = "tb_aluguel",
        schema = "db_car_rental_solutis"
)
@Schema(description = "Representa um aluguel de carro.")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Data em que o aluguel foi solicitado.")
    private LocalDate dataPedido;

    @Column(nullable = false)
    @Schema(description = "Data em que o carro foi entregue ao motorista.")
    private LocalDate dataEntrega;

    @Column(nullable = false)
    @Schema(description = "Data prevista para a devolução do carro.")
    private LocalDate dataDevolucaoPrevista;

    @Schema(description = "Data efetiva da devolução do carro (pode ser nula).")
    private LocalDate dataDevolucaoEfetiva;

    @Column(name = "valor_total_inicial", precision = 10, scale = 2)
    @Schema(description = "Valor total inicial do aluguel, calculado com base na data de devolução prevista.")
    private BigDecimal valorTotalInicial;

    @Column(precision = 10, scale = 2)
    @Schema(description = "Valor total final do aluguel, calculado com base na data de devolução efetiva (se disponível).")
    private BigDecimal valorTotalFinal;

    @Enumerated(EnumType.STRING)
    private StatusAluguel statusAluguel;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    private LocalDate dataPagamento;

    /**
     * Motorista que realizou este aluguel.
     * <p>
     * Representa o lado "muitos" no relacionamento um-para-muitos com a entidade {@link Motorista}.
     * Cada {@link Aluguel} está associado a um único {@link Motorista}.
     * O atributo `motorista_id` na tabela `tb_aluguel` atua como chave estrangeira, referenciando
     * a tabela `tb_motorista`.
     * </p>
     *
     * @see Motorista
     */
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    @Schema(description = "Motorista que realizou o aluguel.")
    private Motorista motorista;

    /**
     * Carro associado a este aluguel.
     * <p>
     * Representa o lado "muitos" no relacionamento um-para-muitos com a entidade {@link Carro}.
     * Cada {@link Aluguel} está obrigatoriamente associado a um único {@link Carro}.
     * O atributo `carro_id` na tabela `tb_aluguel` atua como chave estrangeira, referenciando
     * a tabela `tb_carro`.
     * </p>
     *
     * @see Carro
     */
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    @Schema(description = "Carro alugado.")
    private Carro carro;

    /**
     * Apólice de seguro associada ao aluguel.
     * <p>
     * Este campo estabelece uma relação um-para-um com a {@link ApoliceSeguro}. Cada aluguel deve
     * ter uma apólice de seguro associada, e a apólice é obrigatória. A anotação {@code @JoinColumn}
     * define a coluna de junção na tabela 'tb_aluguel' que referencia a apólice de seguro.
     * </p>
     *
     * @see ApoliceSeguro
     */
    @OneToOne
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "apolice_seguro_id", nullable = false)
    @Schema(description = "Apólice de seguro associada ao aluguel.")
    private ApoliceSeguro apoliceSeguro;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora da criação do registro.", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.")
    private LocalDateTime lastUpdated;

    public void adicionarApoliceSeguro(ApoliceSeguro apoliceSeguro) {
        this.apoliceSeguro = apoliceSeguro;
    }

    public void adicionarCarro(Carro carro) {
        this.carro = carro;
    }

    public void adicionarMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    @PostLoad
    private void calcularValores() {
        this.valorTotalInicial = calcularValorTotalInicial();
        this.valorTotalFinal = calcularValorTotalFinal();
    }

    @Schema(description = "Calcula o valor total inicial do aluguel (com base na data de devolução prevista e no valor da apólice de seguro).")
    public BigDecimal calcularValorTotalInicial() {
        long quantidadeDias = DAYS.between(dataEntrega, dataDevolucaoPrevista) + 1;
        BigDecimal valorTotal = carro.getValorDiaria().multiply(BigDecimal.valueOf(quantidadeDias));
        return valorTotal.add(apoliceSeguro.getValorFranquia());
    }

    @Schema(description = "Calcula o valor total final do aluguel (com base na data de devolução efetiva, se disponível, e no valor da apólice de seguro).")
    public BigDecimal calcularValorTotalFinal() {
        long quantidadeDias = DAYS.between(dataEntrega, dataDevolucaoEfetiva != null ? dataDevolucaoEfetiva : dataDevolucaoPrevista) + 1;
        BigDecimal valorTotal = carro.getValorDiaria().multiply(BigDecimal.valueOf(quantidadeDias));
        return valorTotal.add(apoliceSeguro.getValorFranquia());
    }

    @Override
    public String toString() {
        return "Aluguel{id=" + id + ", dataPedido=" + dataPedido + ", dataRetirada=" + dataEntrega + ", dataDevolucaoPrevista=" + dataDevolucaoPrevista + ", valor=" + valorTotalFinal + ", motorista=" + motorista + ", carro=" + carro + ", apoliceSeguro=" + apoliceSeguro + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Aluguel aluguel = (Aluguel) o;
        return getId() != null && Objects.equals(getId(), aluguel.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}