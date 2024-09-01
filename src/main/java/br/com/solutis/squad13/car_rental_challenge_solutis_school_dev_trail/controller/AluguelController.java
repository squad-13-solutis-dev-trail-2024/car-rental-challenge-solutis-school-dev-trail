package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosDetalhamentoAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.AluguelService;
import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/aluguel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Aluguel Controller", description = "Controller para gerenciamento de aluguel")
public class AluguelController {

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @Transactional
    @PostMapping("/alugar")
    @Operation(summary = "Alugar um carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carro alugado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de aluguel inválidos ou carro indisponível.")
    })
    public ResponseEntity<DadosListagemAluguel> alugarCarro(
            @RequestBody @Valid DadosCadastroAluguel dadosAlugarCarro,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var aluguel = aluguelService.reservarCarro(dadosAlugarCarro);
        var uri = uriComponentsBuilder.path("/api/v1/aluguel/{id}").buildAndExpand(aluguel.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemAluguel(aluguel));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um aluguel pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluguel encontrado."),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado.")
    })
    public ResponseEntity<DadosListagemAluguel> buscarAluguel(@PathVariable Long id) {
        Aluguel aluguel = aluguelService.buscarPorId(id);
        if (aluguel == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DadosListagemAluguel(aluguel));
    }

    @GetMapping
    @Operation(summary = "Listar aluguéis", description = "Retorna uma lista paginada de aluguéis.")
    @ApiResponse(responseCode = "200", description = "Lista de aluguéis.")
    public ResponseEntity<Page<DadosListagemAluguel>> listar(@PageableDefault(size = 5) Pageable paginacao) {
        var aluguel = aluguelService.listarAlugueis(paginacao);
        return ResponseEntity.ok(aluguel);
    }

    @GetMapping("/detalhar-completo/{id}")
    @Operation(summary = "Detalhar um aluguel completo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluguel encontrado."),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado.")
    })
    public ResponseEntity<DadosDetalhamentoAluguel> detalharCompleto(@PathVariable Long id) {
        Aluguel aluguel = aluguelService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoAluguel(aluguel));
    }

    @PatchMapping("/confirmar/{id}")
    @Operation(summary = "Confirmar um aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluguel confirmado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Aluguel não pode ser confirmado."),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado.")
    })
    public ResponseEntity<DadosListagemAluguel> confirmarAluguel(
            @PathVariable Long id,
            @RequestBody  @Valid DadosPagamento tipoPagamento) {
        var aluguel = aluguelService.confirmarAluguel(id, tipoPagamento);
        return ResponseEntity.ok(new DadosListagemAluguel(aluguel));
    }

    @PatchMapping("/finalizar/{id}")
    @Operation(summary = "Finalizar um aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluguel finalizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Aluguel não pode ser finalizado ou data de devolução inválida."),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado."),
    })
    public ResponseEntity<DadosListagemAluguel> finalizarAluguel(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucao
    ) {
        var aluguel = aluguelService.finalizarAluguel(id, dataDevolucao);
        return ResponseEntity.ok(new DadosListagemAluguel(aluguel));
    }

    @PatchMapping("/cancelar/{id}")
    @Operation(summary = "Cancelar um aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluguel cancelado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Aluguel não pode ser cancelado."),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado."),
    })
    public ResponseEntity<DadosListagemAluguel> cancelarAluguel(@PathVariable Long id) {
        var aluguel = aluguelService.cancelarAluguel(id);
        return ResponseEntity.ok(new DadosListagemAluguel(aluguel));
    }
}
