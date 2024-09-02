package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosDetalhamentoAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.CarrinhoAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.AluguelService;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarrinhoAluguelService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/api/v1/aluguel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Aluguel Controller", description = "Controller para gerenciamento de aluguel")
public class AluguelController {

    private final AluguelService aluguelService;
    private final CarrinhoAluguelService carrinhoAluguelService;

    public AluguelController(AluguelService aluguelService,
                             CarrinhoAluguelService carrinhoAluguelService) {
        this.aluguelService = aluguelService;
        this.carrinhoAluguelService = carrinhoAluguelService;
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
        Long motoristaId = dadosAlugarCarro.motoristaId(); // Obtém o ID do motorista do DTO
        List<Long> carrosIds = dadosAlugarCarro.carrosIds(); // Obtém os IDs dos carros do DTO

        // Adiciona os carros ao carrinho do motorista
        carrosIds.forEach(carroId -> carrinhoAluguelService.adicionarCarroAoCarrinho(motoristaId, carroId));

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
            @RequestBody @Valid DadosPagamento tipoPagamento) {
        var aluguel = aluguelService.confirmarAluguel(id, tipoPagamento);
        return ResponseEntity.ok(new DadosListagemAluguel(aluguel));
    }

    @GetMapping("/pesquisar")
    @Operation(summary = "Pesquisar aluguéis por critérios", description = "Retorna uma lista paginada de aluguéis que correspondem aos critérios de pesquisa.")
    @ApiResponse(responseCode = "200", description = "Lista de aluguéis encontrados.")
    public ResponseEntity<Page<DadosDetalhamentoAluguel>> pesquisarAlugueis(
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataPedido,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataRetirada,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataDevolucaoPrevista,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataDevolucaoEfetiva,
            @RequestParam(required = false) BigDecimal valorTotalInicialMin,
            @RequestParam(required = false) BigDecimal valorTotalInicialMax,
            @RequestParam(required = false) BigDecimal valorTotalFinalMin,
            @RequestParam(required = false) BigDecimal valorTotalFinalMax,
            @RequestParam(required = false) StatusAluguel status,
            @RequestParam(required = false) String motoristaNome,
            @RequestParam(required = false) String motoristaCpf,
            @RequestParam(required = false) String motoristaCnh,
            @RequestParam(required = false) String carroNome,
            @RequestParam(required = false) String carroPlaca,
            @RequestParam(required = false) String carroCor,
            @RequestParam(required = false) String modeloDescricaoCarro,
            @RequestParam(required = false) String fabricanteNomeCarro,
            @RequestParam(required = false) String categoriaNomeCarro,
            @RequestParam(required = false) List<String> acessoriosNomesCarro,
            @PageableDefault(size = 5) Pageable paginacao
    ) {
        Page<DadosDetalhamentoAluguel> alugueis = aluguelService.pesquisarAlugueis(
                dataPedido,
                dataRetirada,
                dataDevolucaoPrevista,
                dataDevolucaoEfetiva,
                valorTotalInicialMin,
                valorTotalInicialMax,
                valorTotalFinalMin,
                valorTotalFinalMax,
                status,
                motoristaNome,
                motoristaCpf,
                motoristaCnh,
                carroNome,
                carroPlaca,
                carroCor,
                modeloDescricaoCarro,
                fabricanteNomeCarro,
                categoriaNomeCarro,
                acessoriosNomesCarro,
                paginacao
        );

        return ResponseEntity.ok(alugueis);
    }

    @PatchMapping("/trocar-carro/{idAluguel}/carro/{carroId}")
    @Operation(summary = "Trocar Carro do aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro trocado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Aluguel não pode ser confirmado."),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado.")
    })
    public ResponseEntity<DadosListagemAluguel> trocarCarro(
            @PathVariable Long idAluguel,
            @PathVariable Long carroId) {
        var aluguel = aluguelService.trocarCarro(idAluguel, carroId);
        return ResponseEntity.ok(new DadosListagemAluguel(aluguel));
    }

    @GetMapping("/carrinho/{id}")
    @Operation(summary = "Buscar um carrinho de aluguel pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho de aluguel encontrado."),
            @ApiResponse(responseCode = "404", description = "Carrinho de aluguel não encontrado.")
    })
    public ResponseEntity<CarrinhoAluguel> buscarCarrinhoPorId(@PathVariable Long id) {
        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelService.buscarPorId(id);
        return ResponseEntity.ok(carrinhoAluguel);
    }

    @GetMapping("/carrinho/motorista/{motoristaId}")
    @Operation(summary = "Buscar um carrinho de aluguel pelo ID do motorista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho de aluguel encontrado."),
            @ApiResponse(responseCode = "404", description = "Carrinho de aluguel não encontrado para o motorista.")
    })
    public ResponseEntity<CarrinhoAluguel> buscarCarrinhoPorMotoristaId(@PathVariable Long motoristaId) {
        CarrinhoAluguel carrinhoAluguel = carrinhoAluguelService.buscarPorMotoristaId(motoristaId);
        return ResponseEntity.ok(carrinhoAluguel);
    }

    @PostMapping("/carrinho/motorista/{motoristaId}/carro/{carroId}")
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

    @DeleteMapping("/carrinho/motorista/{motoristaId}/carro/{carroId}")
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

    @GetMapping("/carrinho/motorista/{motoristaId}/carros")
    @Operation(summary = "Listar os carros no carrinho de aluguel de um motorista")
    @ApiResponse(responseCode = "200", description = "Lista de carros no carrinho.")
    public ResponseEntity<Page<Carro>> listarCarrosNoCarrinho(
            @PathVariable Long motoristaId,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        Page<Carro> carros = carrinhoAluguelService.listarCarrosNoCarrinho(motoristaId, pageable);
        return ResponseEntity.ok(carros);
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
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate dataDevolucao
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
