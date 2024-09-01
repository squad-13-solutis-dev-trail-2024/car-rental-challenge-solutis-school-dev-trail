package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ApoliceSeguro")
@Table(name = "tb_apolice_seguro", schema = "db_car_rental_solutis")
@Schema(description = "Entidade que representa uma apólice de seguro.")
public class ApoliceSeguro {

    private static final BigDecimal VALOR_PROTECAO_TERCEIRO = new BigDecimal("100.00");
    private static final BigDecimal VALOR_PROTECAO_CAUSAS_NATURAIS = new BigDecimal("200.00");
    private static final BigDecimal VALOR_PROTECAO_ROUBO = new BigDecimal("300.00");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    @Schema(description = "Valor da franquia da apólice.")
    private BigDecimal valorFranquia;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Schema(description = "Indica se a apólice cobre danos a terceiros.", example = "true")
    private Boolean protecaoTerceiro;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Schema(description = "Indica se a apólice cobre danos por causas naturais.", example = "false")
    private Boolean protecaoCausasNaturais;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Schema(description = "Indica se a apólice cobre roubo do veículo.", example = "true")
    private Boolean protecaoRoubo;

    /**
     * Aluguel associado à apólice de seguro.
     * <p>
     * Este campo estabelece uma relação um-para-um com a {@link Aluguel}. A anotação {@code @OneToOne}
     * com {@code mappedBy = "apoliceSeguro"} define que a apólice de seguro é o lado inverso da relação,
     * e a coluna de junção é gerenciada pela classe {@link Aluguel}.
     * </p>
     *
     * @see Aluguel
     */
    @OneToMany(mappedBy = "apoliceSeguro", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Aluguéis associados a esta apólice de seguro.")
    private List<Aluguel> alugueis = new ArrayList<>();

    public static BigDecimal calcularValorTotalApoliceSeguro(
            Boolean protecaoTerceiro,
            Boolean protecaoCausasNaturais,
            Boolean protecaoRoubo) {
        BigDecimal valorTotal = BigDecimal.ZERO;

        if (protecaoTerceiro) valorTotal = valorTotal.add(VALOR_PROTECAO_TERCEIRO);
        if (protecaoCausasNaturais) valorTotal = valorTotal.add(VALOR_PROTECAO_CAUSAS_NATURAIS);
        if (protecaoRoubo) valorTotal = valorTotal.add(VALOR_PROTECAO_ROUBO);

        return valorTotal;
    }

    public String toString() {
        return "ApoliceSeguro{id=" + id + ", valorFranquia=" + valorFranquia + ", protecaoTerceiro=" + protecaoTerceiro + ", protecaoCausasNaturais=" + protecaoCausasNaturais + ", protecaoRoubo=" + protecaoRoubo + ", aluguel=" + alugueis+ '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ApoliceSeguro that = (ApoliceSeguro) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
