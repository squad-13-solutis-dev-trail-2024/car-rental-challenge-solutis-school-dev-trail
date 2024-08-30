package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.modeloCarro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.fabricante.DadosFabricanteTeste;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Fabricante;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;

public record DadosModeloCarroTeste(
        String descricao,
        Categoria categoria,
        DadosFabricanteTeste fabricante
) {
}
