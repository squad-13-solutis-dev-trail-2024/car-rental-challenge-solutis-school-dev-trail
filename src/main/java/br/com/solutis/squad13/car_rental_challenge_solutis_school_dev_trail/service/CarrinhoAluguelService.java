package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.CarrinhoAluguel;


import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CarrinhoAluguelService {

    CarrinhoAluguel buscarPorId(Long id);

    CarrinhoAluguel buscarPorMotoristaId(Long motoristaId);

    @Transactional
    CarrinhoAluguel adicionarCarroAoCarrinho(Long motoristaId, Long carroId);

    @Transactional
    void removerCarroDoCarrinho(Long motoristaId, Long carroId);

    Page<Carro> listarCarrosNoCarrinho(Long motoristaId, Pageable pageable);
}