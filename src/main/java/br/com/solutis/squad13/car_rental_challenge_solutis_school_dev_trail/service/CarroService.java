package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CarroService {
    Carro findById(Long id);
    Page<DadosListagemCarro> listar(Pageable pageable);
    Page<DadosListagemCarro> listarCarroCategoria(Categoria categoria, Pageable pageable);
}
