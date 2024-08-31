package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Schema(description = "Repositório JPA para a entidade Aluguel.")
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    Page<Aluguel> findAllByMotorista(Motorista motorista, Pageable paginacao);
}