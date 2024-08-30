package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosDetalhamentoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.MotoristaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/*
 * História de Usuário: Cadastro de Cliente
 * <p>
 * Como um cliente em potencial,
 * Eu quero poder me cadastrar na locadora de automóveis
 * Para ter acesso aos serviços e alugar veículos.
 * <p>
 * Critérios de Aceitação:
 * - Deve haver um formulário de cadastro na página inicial.
 * - O formulário deve solicitar as informações básicas do cliente: nome completo, data de nascimento, cpf, número da CNH.
 * - O cliente deve receber uma confirmação na tela após o cadastro bem-sucedido.
 * - O sistema deve verificar a validade do endereço de e-mail para evitar registros duplicados.
 * - Após o cadastro, o cliente deve ser redirecionado para a página inicial, onde pode acessar serviços da locadora.
 * <p>
 * Notas adicionais:
 * Esta história de usuário aborda a necessidade de um cliente se cadastrar na locadora de
 * automóveis para obter acesso aos serviços de aluguel de veículos. Os critérios de aceitação
 * definem os requisitos específicos que devem ser atendidos para que a história seja
 * considerada completa. Isso inclui aspectos técnicos, como validação de dados, bem como
 * aspectos funcionais, como a confirmação por e-mail e a concordância com os termos e
 * condições.
 * <p>
 * Lembrando que os detalhes exatos podem variar com base nas necessidades do projeto e
 * da equipe, mas esse exemplo oferece uma estrutura sólida para capturar os requisitos
 * essenciais do cadastro de cliente em uma locadora de automóveis.
 */

@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

    private final MotoristaService motoristaService;

    public ClienteController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosListagemMotorista> cadastrar(
            @RequestBody @Valid DadosCadastroMotorista dadosCadastroMotorista,
            UriComponentsBuilder uriBuilder
    ) {
        var motorista = motoristaService.cadastrarMotorista(dadosCadastroMotorista);
        var uri = uriBuilder.path("/api/v1/clientes/{id}").buildAndExpand(motorista.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemMotorista(motorista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemMotorista> detalhar(@PathVariable Long id) {
        var motorista = motoristaService.buscarPorId(id);
        return ResponseEntity.ok(new DadosListagemMotorista(motorista));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMotorista>> listar(@PageableDefault(size = 20, sort = {"nome"}) Pageable paginacao) {
        var motoristas = motoristaService.listar(paginacao);
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/detalhar-completo/{id}")
    public ResponseEntity<DadosDetalhamentoMotorista> detalharCompleto(@PathVariable Long id) {
        var motorista = motoristaService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoMotorista(motorista));
    }

    @Transactional
    @PatchMapping
    public ResponseEntity<DadosListagemMotorista> atualizar(@RequestBody @Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        var motorista = motoristaService.atualizarMotorista(dadosAtualizacaoMotorista);
        return ResponseEntity.ok(new DadosListagemMotorista(motorista));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        motoristaService.desativarMotorista(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoristaService.deletarMotorista(id);
        return ResponseEntity.noContent().build();
    }
}
