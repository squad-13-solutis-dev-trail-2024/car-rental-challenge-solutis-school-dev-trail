package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.BigDecimal.valueOf;
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
    @Schema(description = "Data em que o aluguel foi solicitado.", example = "2024-08-01")
    private LocalDate dataPedido;

    @Column(nullable = false)
    @Schema(description = "Data em que o carro foi entregue ao motorista.", example = "2024-08-11")
    private LocalDate dataEntrega;

    @Column(nullable = false)
    @Schema(description = "Data prevista para a devolução do carro.", example = "2024-08-21")
    private LocalDate dataDevolucaoPrevista;

    @Schema(description = "Data efetiva da devolução do carro (pode ser nula).", example = "2024-08-19")
    private LocalDate dataDevolucaoEfetiva;

    @Column(precision = 10, scale = 2)
    @Schema(description = "Valor total inicial do aluguel, calculado com base na data de devolução prevista.", example = "1500.00")
    private BigDecimal valorTotalInicial;

    @Column(precision = 10, scale = 2)
    @Schema(description = "Valor total final do aluguel, calculado com base na data de devolução efetiva (se disponível).", example = "1350.00")
    private BigDecimal valorTotalFinal;

    @Enumerated(EnumType.STRING)
    private StatusAluguel statusAluguel;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    @JsonIgnore
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
    @JoinColumn(name = "apolice_seguro_id", nullable = false)
    @JsonIgnore
    @Schema(description = "Apólice de seguro associada ao aluguel.")
    private ApoliceSeguro apoliceSeguro;

    @PostLoad
    private void calcularValores() {
        this.valorTotalInicial = calcularValorTotalInicial();
        this.valorTotalFinal = calcularValorTotalFinal();
    }

    @Schema(description = "Calcula o valor total inicial do aluguel (com base na data de devolução prevista).")
    public BigDecimal calcularValorTotalInicial() {
        long quantidadeDias = DAYS.between(dataEntrega, dataDevolucaoPrevista);
        return carro.getValorDiaria().multiply(valueOf(quantidadeDias));
    }

    @Schema(description = "Calcula o valor total final do aluguel (com base na data de devolução efetiva, se disponível).")
    public BigDecimal calcularValorTotalFinal() {
        if (dataDevolucaoEfetiva != null) {
            long quantidadeDias = DAYS.between(dataEntrega, dataDevolucaoEfetiva);
            return carro.getValorDiaria().multiply(valueOf(quantidadeDias));
        } else {
            return valorTotalInicial;
        }
    }

    @Override
    public String toString() {
        return "Aluguel{id=" + id + ", dataPedido=" + dataPedido + ", dataEntrega=" + dataEntrega + ", dataDevolucaoPrevista=" + dataDevolucaoPrevista + ", valor=" + valorTotalFinal + ", motorista=" + motorista + ", carro=" + carro + ", apoliceSeguro=" + apoliceSeguro + '}';
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