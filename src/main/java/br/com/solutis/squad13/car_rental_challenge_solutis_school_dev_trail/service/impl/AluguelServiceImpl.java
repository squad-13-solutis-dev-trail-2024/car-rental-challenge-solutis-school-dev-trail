package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.AluguelRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.ApoliceSeguroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.AluguelService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

/*

História de Usuário: Escolha de Veículo para Aluguel
Como um cliente cadastrado na locadora de automóveis,
Eu quero poder escolher um veículo disponível para alugar,
Para reservar o veículo que atenda às minhas necessidades de locomoção.

Critérios de Aceitação:
    - Na página inicial deve haver uma seção "Seleção de Veículos" ou similar.
    - Os veículos disponíveis para aluguel devem ser apresentados em uma lista ou grade, exibindo informações como fabricante, modelo, categoria, acessórios e preço por dia.
    - Cada veículo deve ter uma imagem representativa para auxiliar na identificação.
    - O cliente deve ser capaz de aplicar filtros, como categoria de veículo (carro, SUV, caminhonete) ou acessórios (ar-condicionado, sistema de navegação, etc.).
    - Ao clicar em um veículo, o cliente deve ser direcionado para uma página de detalhes do veículo.
    - Na página de detalhes, o cliente deve ver informações mais detalhadas sobre o veículo, incluindo especificações técnicas e descrição.
    - O cliente deve ter a opção de selecionar o período de aluguel, especificando datas de início e término.
    - Após selecionar o período, o cliente deve ser capaz de adicionar o veículo ao seu carrinho de aluguel.
    - O carrinho de aluguel deve exibir um resumo dos veículos selecionados, suas datas de aluguel e o custo total estimado.
    - O cliente deve ter a opção de revisar o carrinho, fazer ajustes e confirmar a reserva.
    - Uma vez confirmada a reserva, o cliente deve receber uma confirmação na tela com os detalhes da reserva.

Notas adicionais:
Esta história de usuário trata do processo de escolha de um veículo para aluguel por parte
de um cliente cadastrado. Os critérios de aceitação descrevem os passos e funcionalidades
necessárias para que o cliente possa navegar pelos veículos disponíveis, selecionar um
veículo, escolher as datas de aluguel e confirmar sua reserva. Essa funcionalidade é
essencial para permitir que os clientes escolham e reservem veículos de acordo com suas
preferências e necessidades.

------------------------------------------------------------------------------------------------------

História de Usuário: Efetivação do Aluguel de Veículo
Como um cliente cadastrado que selecionou um veículo para alugar,
Eu quero poder efetivar o aluguel do veículo selecionado,
Para confirmar minha reserva e iniciar o processo de aluguel.

Critérios de Aceitação:
    - Após revisar o carrinho de aluguel, devo ter a opção de confirmar a reserva e efetivar o aluguel.
    - Ao confirmar a reserva, devo ser redirecionado para uma página de resumo da reserva, exibindo todos os detalhes do aluguel.
    - A página de resumo deve conter informações sobre o veículo selecionado, datas de aluguel, custo total estimado e termos de aluguel.
    - Devo ser solicitado a revisar e concordar com os termos e condições do aluguel antes de prosseguir.
    - Após concordar com os termos, devo ter a opção de escolher um metodo de pagamento.
    - O sistema deve permitir a inserção das informações do cartão de crédito ou outro metodo de pagamento aceito. (simulado)
    - Deve haver uma opção para confirmar o pagamento e finalizar o processo de aluguel.
    - Após a confirmação do pagamento, devo receber na tela uma confirmação contendo todos os detalhes do aluguel, informações de contato e a fatura.
    - O sistema deve marcar o veículo como "reservado" e bloquear as datas de aluguel no calendário.
    - O cliente deve poder acessar seus aluguéis confirmados e detalhes futuros através da sua conta.

Notas adicionais:
Essa história de usuário aborda a etapa final do processo de aluguel, onde o cliente
confirmou a reserva do veículo selecionado e efetiva o aluguel ao concordar com os termos,
inserir informações de pagamento e confirmar o pagamento. Os critérios de aceitação
detalham as ações que o cliente deve ser capaz de realizar, bem como os resultados
esperados, como o recebimento de um e-mail de confirmação e o bloqueio das datas de
aluguel. A efetivação do aluguel é um passo crucial para garantir que os clientes obtenham
acesso ao veículo escolhido para as datas desejadas.
 */

@Service
@Schema(description = "Serviço que implementa as operações de aluguel")
public class AluguelServiceImpl implements AluguelService {

    private static final Logger log = getLogger(AluguelServiceImpl.class);

    private final AluguelRepository aluguelRepository;
    private final MotoristaRepository motoristaRepository;
    private final CarroRepository carroRepository;
    private final 
    ApoliceSeguroRepository apoliceSeguroRepository;

    public AluguelServiceImpl(AluguelRepository aluguelRepository,
                              MotoristaRepository motoristaRepository,
                              CarroRepository carroRepository,
                              ApoliceSeguroRepository apoliceSeguroRepository) {
        this.aluguelRepository = aluguelRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
        this.apoliceSeguroRepository = apoliceSeguroRepository;
    }
/// ajudando vc um pouquinho, ou atrapalhando, ai eu não sei
/// Obrigado amigue
    @Override
    public Aluguel buscarPorId(Long id) {
        log.info("Buscando aluguel por ID: {}", id);

        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aluguel não encontrado para ID: {}", id);
                    return new EntityNotFoundException("Aluguel não encontrado");
                });

        log.info("Aluguel encontrado: {}", aluguel);
        return aluguel;
    }

    @Override
    @Transactional
    public Aluguel alugar(@Valid DadosCadastroAluguel alugar)  {
        log.info("Iniciando processo de aluguel: {}", alugar);

        // 1. Verificar se o motorista existe
        if (!motoristaRepository.existsByEmail(alugar.emailMotorista())) {
            log.warn("Motorista não encontrado com o email: {}", alugar.emailMotorista());
            throw new EntityNotFoundException("Motorista não encontrado com o email: " + alugar.emailMotorista());
        }

        // 2. Validate Carro
        Carro carro = carroRepository.findById(alugar.idCarro())
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado com o ID: {}", alugar.idCarro());
                    return new EntityNotFoundException("Carro não encontrado com o ID: " + alugar.idCarro());
                });

        if (!carro.isDisponivel()) {
            log.warn("Carro indisponível para aluguel: {}", carro.getId());
            throw new EntityNotFoundException("Carro indisponível para aluguel");
        }

        // 3. Create Aluguel
        Aluguel aluguel = new Aluguel();
        aluguel.setMotorista(motoristaRepository.findByEmail(alugar.emailMotorista())); // Fetch Motorista
        aluguel.setCarro(carro);
        aluguel.setDataEntrega(alugar.dataEntrega());
        aluguel.setDataDevolucaoPrevista(alugar.dataDevolucaoPrevista());
        aluguel.setDataPedido(LocalDate.now());

        // 4. Block Carro and Save
        carro.bloquearAluguel();
        carroRepository.save(carro);

        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);
        log.info("Aluguel realizado com sucesso: {}", aluguelSalvo);
        return aluguelSalvo;
    }

    @Override
    public Page<DadosListagemAluguel> listar(Pageable paginacao) {
        log.info("Listando alugueis com paginação: {}", paginacao);
        Page<Aluguel> alugueis = aluguelRepository.findAll(paginacao);
        log.info("Alugueis listados com sucesso: {}", alugueis);
        return alugueis.map(DadosListagemAluguel::new);
    }

    @Override
    public BigDecimal calcularCustoTotal(Long idCarro, Long idApoliceSeguro, Aluguel aluguel) {
        log.info("Calculando custo total do aluguel para o carro: {}", idCarro);

        Carro carro = carroRepository.findById(idCarro)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado com o ID: " + idCarro));

        ApoliceSeguro apoliceSeguro = apoliceSeguroRepository.findById(idApoliceSeguro)
                .orElseThrow(() -> new EntityNotFoundException("Apólice de seguro não encontrada com o ID: " + idApoliceSeguro));

        long diasAluguel = ChronoUnit.DAYS.between(aluguel.getDataEntrega(), aluguel.getDataDevolucaoPrevista());
        BigDecimal custoDiarias = carro.getValorDiaria().multiply(BigDecimal.valueOf(diasAluguel));
        BigDecimal valorSeguro = apoliceSeguro.getValorFranquia();

        BigDecimal custoTotal = valorSeguro.add(custoDiarias);

        log.info("Custo total calculado: {}", custoTotal);
        return custoTotal;
    }

//    @Override
//    @Transactional
//    public Aluguel confirmarAluguel(Long idAluguel, DadosPagamento dadosPagamento) {
//        log.info("Confirmando aluguel com ID: {}", idAluguel);
//
//        Aluguel aluguel = aluguelRepository.findById(idAluguel)
//                .orElseThrow(() -> new EntityNotFoundException("Aluguel não encontrado com o ID: " + idAluguel));
//
//        // TODO: Implementar lógica de validação dos dados de pagamento (simulada)
//
//        aluguel.setStatusAluguel(StatusAluguel.CONFIRMADO); // Atualizar status do aluguel
//        aluguel.setDadosPagamento(dadosPagamento); // Salvar dados de pagamento (simulados)
//
//        Aluguel aluguelConfirmado = aluguelRepository.save(aluguel);
//
//        // TODO: Implementar lógica de geração e envio da confirmação do aluguel
//
//        log.info("Aluguel confirmado com sucesso: {}", aluguelConfirmado);
//        return aluguelConfirmado;
//    }

    @Override
    public Page<DadosListagemAluguel> listarAlugueisPorCliente(Long idCliente, Pageable paginacao) {
        log.info("Listando alugueis do cliente com ID: {}", idCliente);

        // TODO: Implementar lógica para buscar alugueis por cliente (utilizando o relacionamento com Motorista)

        Page<Aluguel> alugueis = aluguelRepository.findAll(paginacao); // Substituir pela busca correta
        log.info("Alugueis do cliente listados com sucesso: {}", alugueis);
        return alugueis.map(DadosListagemAluguel::new);
    }

    @Override
    @Transactional
    public void cancelarAluguel(Long idAluguel) {
        log.info("Cancelando aluguel com ID: {}", idAluguel);

        Aluguel aluguel = aluguelRepository.findById(idAluguel)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel não encontrado com o ID: " + idAluguel));

        // TODO: Implementar lógica de verificação do prazo permitido para cancelamento

        aluguel.setStatusAluguel(StatusAluguel.CANCELADO); // Atualizar status do aluguel

        Carro carro = aluguel.getCarro();
        carro.disponibilizarAluguel();

        carroRepository.save(carro);
        aluguelRepository.save(aluguel);

        log.info("Aluguel cancelado com sucesso: {}", aluguel);
    }
}