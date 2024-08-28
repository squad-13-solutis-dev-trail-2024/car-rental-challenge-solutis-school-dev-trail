package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosCadastroMotorista;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Motorista")
@Table(
        name = "tb_motorista",
        schema = "db_car_rental_solutis"
)
@PrimaryKeyJoinColumn(name = "pessoa_id")
public class Motorista extends Pessoa {

    private String numeroCNH;

    @OneToMany(mappedBy = "motorista")
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private List<Aluguel> alugueis;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean ativo;

    @CreationTimestamp
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    public Motorista(@Valid DadosCadastroMotorista dadosCadastroMotorista) {
        this.setNome(dadosCadastroMotorista.nome());
        this.setDataNascimento(dadosCadastroMotorista.dataNascimento());
        this.setCpf(dadosCadastroMotorista.cpf());
        this.setEmail(dadosCadastroMotorista.email());
        this.setSexo(dadosCadastroMotorista.sexo());
        this.ativo = true;
        this.numeroCNH = dadosCadastroMotorista.numeroCNH();
        this.dataCreated = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        this.setNome(dadosAtualizacaoMotorista.nome());
        this.setDataNascimento(dadosAtualizacaoMotorista.dataNascimento());
        this.setCpf(dadosAtualizacaoMotorista.cpf());
        this.setEmail(dadosAtualizacaoMotorista.email());
        this.setSexo(dadosAtualizacaoMotorista.sexo());
        this.numeroCNH = dadosAtualizacaoMotorista.numeroCNH();
        this.lastUpdated = LocalDateTime.now();
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

