package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Schema(description = "Repositório JPA para a entidade Aluguel.")
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByMotoristaId(Long motoristaId);

    boolean existsByMotoristaId(Long id);
}
