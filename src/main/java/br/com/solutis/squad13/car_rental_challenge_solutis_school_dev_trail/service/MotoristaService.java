package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosAtualizacaoMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.DadosListagemMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


/*
História de Usuário: Cadastro de Cliente

Como um cliente em potencial,
Eu quero poder me cadastrar na locadora de automóveis
Para ter acesso aos serviços e alugar veículos.

Critérios de Aceitação:
    - Deve haver um formulário de cadastro na página inicial.
    - O formulário deve solicitar as informações básicas do cliente: nome completo, data de nascimento, cpf, número da CNH.
    - O cliente deve receber uma confirmação na tela após o cadastro bem-sucedido.
    - O sistema deve verificar a validade do endereço de e-mail para evitar registros duplicados.
    - Após o cadastro, o cliente deve ser redirecionado para a página inicial, onde pode acessar serviços da locadora.

Notas adicionais:
Esta história de usuário aborda a necessidade de um cliente se cadastrar na locadora de
automóveis para obter acesso aos serviços de aluguel de veículos. Os critérios de aceitação
definem os requisitos específicos que devem ser atendidos para que a história seja
considerada completa. Isso inclui aspectos técnicos, como validação de dados, bem como
aspectos funcionais, como a confirmação por e-mail e a concordância com os termos e
condições.

Lembrando que os detalhes exatos podem variar com base nas necessidades do projeto e
da equipe, mas esse exemplo oferece uma estrutura sólida para capturar os requisitos
essenciais do cadastro de cliente em uma locadora de automóveis.
 */
public interface MotoristaService {

    @Transactional
    Motorista cadastrarMotorista(DadosCadastroMotorista motorista);

    Motorista buscarPorId(Long id);

    @Transactional
    Motorista atualizarMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista);

    @Transactional
    void deletarMotorista(Long id);

    Page<DadosListagemMotorista> listar(Pageable paginacao);
}
