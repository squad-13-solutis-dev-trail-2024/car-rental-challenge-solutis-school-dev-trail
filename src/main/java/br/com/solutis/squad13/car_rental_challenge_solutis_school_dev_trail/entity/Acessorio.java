package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Acessorio")
@Table(
        name = "tb_acessorio",
        schema = "db_car_rental_solutis"
)
public class Acessorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DescricaoAcessorio descricao;

    @ManyToMany(mappedBy = "acessorios")
    @Setter(AccessLevel.NONE)
    private Set<Carro> carros;
}

enum DescricaoAcessorio {
    GPS,
    AR_CONDICIONADO,
    CADEIRA_INFANTIL,
    TETO_SOLAR,
    CAMERA_RE,
    SENSOR_ESTACIONAMENTO,
    BLUETOOTH,
    ALARM,
    RODAS_LIGA_LEVE,
    FAROL_DE_MILHA
}