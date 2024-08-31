package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Schema(description = "Repositório JPA para a entidade Motorista.")
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByNumeroCNH(String numeroCNH);

    Optional<Motorista> findByEmail(String email);

    Page<Motorista> findAllByAtivoTrue(Pageable pageable);
}
