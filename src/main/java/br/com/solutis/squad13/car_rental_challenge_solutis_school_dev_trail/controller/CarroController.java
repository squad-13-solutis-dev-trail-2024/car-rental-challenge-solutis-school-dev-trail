package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAtualizarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosDetalhamentoCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carro")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Carro Controller", description = "Controller para gerenciamento de carros")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Cadastrar um novo carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carro criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos.")
    })
    public ResponseEntity<DadosListagemCarro> cadastrar(
            @RequestBody @Valid DadosCadastroCarro dadosCadastroCarro,
            UriComponentsBuilder uriBuilder
    ) {
        var carro = carroService.cadastrarCarro(dadosCadastroCarro);
        var uri = uriBuilder.path("/api/v1/carro/{id}").buildAndExpand(carro.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemCarro(carro));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar um carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro encontrado."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<DadosDetalhamentoCarro> detalhar(@PathVariable Long id) {
        var carro = carroService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoCarro(carro));
    }

    @GetMapping
    @Operation(summary = "Listar carros", description = "Retorna uma lista paginada de carros.")
    @ApiResponse(responseCode = "200", description = "Lista de carros.")
    public ResponseEntity<Page<DadosListagemCarro>> listar(@PageableDefault(size = 20, sort = {"modelo"}) Pageable paginacao) {
        var carros = carroService.listar(paginacao);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/detalhar-completo/{id}")
    @Operation(summary = "Detalhar um carro completo incluindo todos os seus aluguéis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro encontrado."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<DadosDetalhamentoCarro> detalharCompleto(@PathVariable Long id) {
        var carro = carroService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoCarro(carro));
    }

    @GetMapping("/pesquisar")
    @Operation(summary = "Pesquisar carros por critérios", description = "Retorna uma lista paginada de carros que correspondem aos critérios de pesquisa.")
    @ApiResponse(responseCode = "200", description = "Lista de carros encontrados.")
    public ResponseEntity<Page<DadosDetalhamentoCarro>> pesquisarCarros(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) Boolean disponivel,
            @RequestParam(required = false) BigDecimal valorDiariaMin,
            @RequestParam(required = false) BigDecimal valorDiariaMax,
            @RequestParam(required = false) String modeloDescricao,
            @RequestParam(required = false) String fabricanteNome,
            @RequestParam(required = false) String categoriaNome,
            @RequestParam(required = false) List<String> acessoriosNomes,
            @PageableDefault(size = 5) Pageable paginacao
    ) {
        Page<DadosDetalhamentoCarro> carros = carroService.pesquisarCarros(
                nome,
                placa,
                chassi,
                cor,
                disponivel,
                valorDiariaMin,
                valorDiariaMax,
                modeloDescricao,
                fabricanteNome,
                categoriaNome,
                acessoriosNomes,
                paginacao
        );

        return ResponseEntity.ok(carros);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar carros disponíveis", description = "Retorna uma lista paginada de carros disponíveis para aluguel.")
    @ApiResponse(responseCode = "200", description = "Lista de carros disponíveis.")
    public ResponseEntity<Page<DadosListagemCarro>> listarCarrosDisponiveis(@PageableDefault(size = 20) Pageable paginacao) {
        var carros = carroService.listarCarrosDisponiveis(paginacao);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/alugados")
    @Operation(summary = "Listar carros alugados", description = "Retorna uma lista paginada de carros que estão atualmente alugados.")
    @ApiResponse(responseCode = "200", description = "Lista de carros alugados.")
    public ResponseEntity<Page<DadosListagemCarro>> listarCarrosAlugados(@PageableDefault(size = 20) Pageable paginacao) {
        var carros = carroService.listarCarrosAlugados(paginacao);
        return ResponseEntity.ok(carros);
    }

    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear aluguel de um carro", description = "Bloqueia um carro para que ele não possa ser alugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carro bloqueado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<Void> bloquearAluguel(@PathVariable Long id) {
        carroService.bloquearCarroAluguel(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/disponibilizar")
    @Operation(summary = "Disponibilizar aluguel de um carro", description = "Disponibiliza um carro para que ele possa ser alugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carro disponibilizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<Void> disponibilizarAluguel(@PathVariable Long id) {
        carroService.disponibilizarCarroAluguel(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping
    @Operation(summary = "Atualizar um carro", description = "Atualiza parte ou todos os dados de um carro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<DadosListagemCarro> atualizar(@RequestBody @Valid DadosAtualizarCarro dadosAtualizacaoCarro) {
        var carro = carroService.atualizarCarro(dadosAtualizacaoCarro);
        return ResponseEntity.ok(new DadosListagemCarro(carro));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar um carro", description = "Aos desativar um carro, ele não poderá ser alugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carro desativado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        carroService.excluirCarro(id);
        return ResponseEntity.noContent().build();
    }
}