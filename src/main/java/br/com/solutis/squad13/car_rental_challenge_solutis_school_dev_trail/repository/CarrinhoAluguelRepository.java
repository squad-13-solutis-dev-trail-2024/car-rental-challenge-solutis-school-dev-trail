package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.CarrinhoAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Schema(description = "Interface que define os serviços relacionados ao Carrinho de Aluguel.")
@Repository("CarrinhoAluguelRepository")
public interface CarrinhoAluguelRepository extends JpaRepository<CarrinhoAluguel, Long> {

    Optional<CarrinhoAluguel> findByMotoristaId(Long motoristaId);

}