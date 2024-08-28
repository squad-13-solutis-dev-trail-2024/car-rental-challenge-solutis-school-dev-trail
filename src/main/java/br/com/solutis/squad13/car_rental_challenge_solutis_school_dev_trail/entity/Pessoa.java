package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Pessoa")
@Table(
        name = "tb_pessoa",
        schema = "db_car_rental_solutis"
)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @Email
    private String email; //Agora tem =)
    
    //Depois pensar numa validação para aceitar só maiores de idade
    private Date dataNascimento;

    @CPF // Validation
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Sexo sexo; // Mas sexo está em pessoa, olha ele aqui
}