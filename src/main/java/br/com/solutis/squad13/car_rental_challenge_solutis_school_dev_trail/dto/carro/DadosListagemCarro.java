package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Fabricante;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;

import java.math.BigDecimal;

public record DadosListagemCarro(
        Long id,
        String nome,
        ModeloCarro modelo,
        boolean isDisponivel,
        BigDecimal valorDiario,
        Fabricante fabricante,
        Categoria categoria
) {
    //salvei vc rsrs
    public DadosListagemCarro(Carro carro) {
        this(
                carro.getId(),
                carro.getNome(),
                carro.getModelo(),
                carro.isDisponivelParaAluguel(),
                carro.getValorDiaria(),
                carro.getModelo().getFabricante(),
                carro.getModelo().getCategoria()
        );
    }
}
