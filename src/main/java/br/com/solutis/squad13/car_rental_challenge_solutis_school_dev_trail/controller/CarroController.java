package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Categoria;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carros")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarroController {
    // O cabra da peste, a gente n tinha combinado de não fazer o crud do carro? kakakakakaka
    //Só o get, na parada pede
    // Ok
    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCarro>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var carros = carroService.listar(paginacao);
        return ResponseEntity.ok(carros);
    }
    @GetMapping("/carros/categoria")
    public ResponseEntity<Page<DadosListagemCarro>> listarCategoria(
            @RequestParam("categoria") Categoria categoria,
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        Page<DadosListagemCarro> dadosListagemCarros = carroService.listarCarroCategoria(categoria, paginacao);
        return ResponseEntity.ok(dadosListagemCarros);
    }
}
