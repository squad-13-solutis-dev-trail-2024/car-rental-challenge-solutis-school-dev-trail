package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Motorista")
@Table(
        name = "tb_motorista",
        schema = "db_car_rental_solutis"
)
public class Motorista extends Pessoa {
    private String numeroCNH;

    //Brother
    //Não tem email, aqui, mas tem em Pessoa,
    // TU concorda que se Motorista Extends Pessoa
    //Naturalmente o motorista é uma pessoa
    // Portanto, motorista tem email

    @OneToMany(mappedBy = "motorista")
    @Setter(AccessLevel.NONE)
    private List<Aluguel> alugueis;
}

