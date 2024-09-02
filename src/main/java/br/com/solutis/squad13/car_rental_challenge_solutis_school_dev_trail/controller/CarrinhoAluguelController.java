package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.CarrinhoAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarrinhoAluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carrinho-aluguel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Carrinho de Aluguel Controller", description = "Controller para gerenciamento do carrinho de aluguel")
public class CarrinhoAluguelController {

    private final CarrinhoAluguelService carrinhoAluguelService;

    public CarrinhoAluguelController(CarrinhoAluguelService carrinhoAluguelService) {
        this.carrinhoAluguelService = carrinhoAluguelService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um carrinho de aluguel pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho de aluguel encontrado."),
            @ApiResponse(responseCode = "404", description = "Carrinho de aluguel não encontrado.")
    })
    public ResponseEntity<CarrinhoAluguel> buscarPorId(@PathVariable Long id) {
        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelService.buscarPorId(id);
        return ResponseEntity.ok(carrinhoAluguel);
    }

    @GetMapping("/motorista/{motoristaId}")
    @Operation(summary = "Buscar um carrinho de aluguel pelo ID do motorista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho de aluguel encontrado."),
            @ApiResponse(responseCode = "404", description = "Carrinho de aluguel não encontrado para o motorista.")
    })
    public ResponseEntity<CarrinhoAluguel> buscarPorMotoristaId(@PathVariable Long motoristaId) {
        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelService.buscarPorMotoristaId(motoristaId);
        return ResponseEntity.ok(carrinhoAluguel);
    }

    @PostMapping("/motorista/{motoristaId}/carro/{carroId}")
    @Transactional
    @Operation(summary = "Adicionar um carro ao carrinho de aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro adicionado ao carrinho com sucesso."),
            @ApiResponse(responseCode = "404", description = "Motorista ou carro não encontrado.")
    })
    public ResponseEntity<CarrinhoAluguel> adicionarCarroAoCarrinho(
            @PathVariable Long motoristaId,
            @PathVariable Long carroId
    ) {
        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelService.adicionarCarroAoCarrinho(motoristaId, carroId);
        return ResponseEntity.ok(carrinhoAluguel);
    }

    @DeleteMapping("/motorista/{motoristaId}/carro/{carroId}")
    @Transactional
    @Operation(summary = "Remover um carro do carrinho de aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carro removido do carrinho com sucesso."),
            @ApiResponse(responseCode = "404", description = "Motorista, carro ou carrinho de aluguel não encontrado.")
    })
    public ResponseEntity<Void> removerCarroDoCarrinho(
            @PathVariable Long motoristaId,
            @PathVariable Long carroId
    ) {
        carrinhoAluguelService.removerCarroDoCarrinho(motoristaId, carroId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/motorista/{motoristaId}/carros")
    @Operation(summary = "Listar os carros no carrinho de aluguel de um motorista")
    @ApiResponse(responseCode = "200", description = "Lista de carros no carrinho.")
    public ResponseEntity<Page<Carro>> listarCarrosNoCarrinho(
            @PathVariable Long motoristaId,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        Page<Carro> carros = carrinhoAluguelService.listarCarrosNoCarrinho(motoristaId, pageable);
        return ResponseEntity.ok(carros);
    }
}