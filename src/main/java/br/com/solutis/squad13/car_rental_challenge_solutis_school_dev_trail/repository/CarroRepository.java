package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("CarroRepository")
@Schema(description = "Repositório JPA para a entidade Carro.")
public interface CarroRepository extends JpaRepository<Carro, Long>, JpaSpecificationExecutor<Carro> {

    boolean existsByPlaca(String placa);

    boolean existsByChassi(String chassi);

    Page<Carro> findAllByDisponivelTrue(Pageable pageable);

    Page<Carro> findAllByDisponivelFalse(Pageable paginacao);

    Carro findByIdAndDisponivelTrue(Long id);

    @Query("SELECT c.id FROM Carro c JOIN c.carrinhosAluguel ca JOIN ca.aluguel a WHERE a.id = :idAluguel")
    Long findCarroIdByAluguelId(@Valid Long idAluguel);
}
