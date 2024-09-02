package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository("CarroRepository")
@Schema(description = "Repositório JPA para a entidade Carro.")
public interface CarroRepository extends JpaRepository<Carro, Long>, JpaSpecificationExecutor<Carro> {

    boolean existsByPlaca(String placa);

    boolean existsByChassi(String chassi);

    Page<Carro> findAllByDisponivelTrue(Pageable pageable);

    Page<Carro> findAllByDisponivelFalse(Pageable paginacao);
}
