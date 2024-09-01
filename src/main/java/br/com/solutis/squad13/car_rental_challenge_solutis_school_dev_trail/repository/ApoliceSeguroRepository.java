package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ApoliceSeguroRepository extends JpaRepository<ApoliceSeguro, Long> {

    ApoliceSeguro findByProtecaoCausasNaturaisAndProtecaoTerceiroAndProtecaoRoubo(
            boolean coberturaRoubo,
            boolean coberturaTerceiros,
            boolean coberturaAcidentes
    );
}