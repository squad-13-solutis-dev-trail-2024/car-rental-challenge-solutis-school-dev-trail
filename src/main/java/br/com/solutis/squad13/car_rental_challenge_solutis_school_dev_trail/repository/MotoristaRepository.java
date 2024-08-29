package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    @Query(value = "SELECT COUNT(*) > 0 AS existe_motorista " +
            "FROM tb_pessoa " +
            "WHERE cpf = :cpf", nativeQuery = true)
    boolean existsByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT COUNT(*) > 0 AS existe_motorista " +
            "FROM tb_pessoa " +
            "WHERE email = :email", nativeQuery = true)
    boolean existsByEmail(String email);

    @Query(value = "SELECT COUNT(*) > 0 AS existe_motorista " +
            "FROM tb_motorista " +
            "WHERE numerocnh = :numeroCNH", nativeQuery = true)
    boolean existsByNumeroCNH(String numeroCNH);

    Motorista findByEmail(String email);

    Page<Motorista> findAllByAtivoTrue(Pageable pageable);
}
