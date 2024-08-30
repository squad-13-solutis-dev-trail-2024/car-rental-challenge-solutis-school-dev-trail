package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.acessorios.DadosAcessorioTeste;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.modeloCarro.DadosModeloCarroTeste;

import java.math.BigDecimal;
import java.util.List;

public record DadosCarroTeste (
        String nome,
        String placa,
        String chassi,
        String cor,
        Boolean disponivel,
        BigDecimal valorDiario,
        List<DadosAcessorioTeste> dadosAcessoriosTeste,
        DadosModeloCarroTeste modelo

){
}
