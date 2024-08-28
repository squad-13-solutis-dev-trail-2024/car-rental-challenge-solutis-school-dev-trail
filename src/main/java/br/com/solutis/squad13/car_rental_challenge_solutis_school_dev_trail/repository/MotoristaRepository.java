package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Motorista findByEmail(String email);

    Page<Motorista> findAllByAtivoTrue(Pageable pageable);
}
