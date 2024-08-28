package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Funcionário")
@Table(
        name = "tb_funcionario",
        schema = "db_car_rental_solutis"
)
@PrimaryKeyJoinColumn(name = "pessoa_id") // Substitua "pessoa_id" pelo nome da coluna correspondente à chave primária da entidade "Pessoa"
public class Funcionario extends Pessoa {
    private String matricula;
}

