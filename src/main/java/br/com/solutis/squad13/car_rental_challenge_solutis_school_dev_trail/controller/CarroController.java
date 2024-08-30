package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;


import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosDetalhamentoCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/carros")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosListagemCarro> cadastrar(
            @RequestBody @Valid DadosCadastroCarro dadosCadastroCarro,
            UriComponentsBuilder uriBuilder
    ) {
        var carro = carroService.cadastrarCarro(dadosCadastroCarro);
        var uri = uriBuilder.path("/api/v1/carros/{id}").buildAndExpand(carro.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemCarro(carro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemCarro> detalhar(@PathVariable Long id) {
        var carro = carroService.buscarPorId(id);
        return ResponseEntity.ok(new DadosListagemCarro(carro));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCarro>> listar(@PageableDefault(size = 20, sort = {"modelo"}) Pageable paginacao) {
        var carros = carroService.listar(paginacao);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/detalhar-completo/{id}")
    public ResponseEntity<DadosDetalhamentoCarro> detalharCompleto(@PathVariable Long id) {
        var carro = carroService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoCarro(carro));
    }

    @Transactional
    @PatchMapping
    public ResponseEntity<DadosListagemCarro> atualizar(@RequestBody @Valid DadosAtualizarCarro dadosAtualizacaoCarro) {
        var carro = carroService.atualizarCarro(dadosAtualizacaoCarro);
        return ResponseEntity.ok(new DadosListagemCarro(carro));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        carroService.desativarCarro(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carroService.deletarCarro(id);
        return ResponseEntity.noContent().build();
    }
}
