package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarroRepository extends JpaRepository<Carro,Long> {
}
