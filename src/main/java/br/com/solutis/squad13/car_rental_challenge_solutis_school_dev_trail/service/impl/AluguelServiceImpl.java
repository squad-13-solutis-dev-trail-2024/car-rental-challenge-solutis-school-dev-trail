package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.AluguelRepository;
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

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;
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

    public AluguelServiceImpl(AluguelRepository aluguelRepository,
                              MotoristaRepository motoristaRepository,
                              CarroRepository carroRepository) {
        this.aluguelRepository = aluguelRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
    }

    @Override
    public Aluguel buscarPorId(Long id) {
        log.info("Buscando aluguel por ID: {}", id);
        Aluguel aluguel = buscarAluguelPeloId(id);
        log.info("Aluguel encontrado: {}", aluguel);
        return aluguel;
    }

    @Override
    @Transactional
    public Aluguel reservarCarro(@Valid DadosCadastroAluguel dadosCadastroAluguel) {
        log.info("Iniciando processo de aluguel: {}", dadosCadastroAluguel);

        Motorista motorista = buscarMotoristaPorEmail(dadosCadastroAluguel.emailMotorista());
        Carro carro = buscarCarroPorId(dadosCadastroAluguel.idCarro());

        validarDisponibilidadeDoCarro(carro);
        validarDatasDeAluguel(dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());

        ApoliceSeguro apoliceSeguro = new ApoliceSeguro(
                dadosCadastroAluguel.apoliceSeguro().valorFranquia(),
                dadosCadastroAluguel.apoliceSeguro().protecaoTerceiros(),
                dadosCadastroAluguel.apoliceSeguro().protecaoCausasNaturais(),
                dadosCadastroAluguel.apoliceSeguro().protecaoRoubo()
        );

        // Calcular valor total inicial
        BigDecimal valorTotalInicial = calcularValorTotalInicial(dadosCadastroAluguel, carro);

        Aluguel aluguel = criarAluguel(carro, motorista, apoliceSeguro, dadosCadastroAluguel, valorTotalInicial);

        carro.bloquearAluguel();
        carroRepository.save(carro);

        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);
        log.info("Aluguel realizado com sucesso: {}", aluguelSalvo);
        return aluguelSalvo;
    }

    @Override
    @Transactional
    public Aluguel confirmarAluguel(Long idAluguel) {

        log.info("Confirmando aluguel com ID: {}", idAluguel);
        Aluguel aluguel = buscarAluguelPeloId(idAluguel);

        if (aluguel.getStatusAluguel() != StatusAluguel.PENDENTE) {
            log.warn("Aluguel não pode ser confirmado, pois não está pendente. ID do aluguel: {}", idAluguel);
            throw new IllegalStateException("Aluguel não pode ser confirmado, pois não está pendente.");
        }

        aluguel.setStatusAluguel(StatusAluguel.CONFIRMADO);

        // Bloquear o carro (se ainda não estiver bloqueado) - otimização: verificar se é necessário
        Carro carro = aluguel.getCarro();
        if (carro.isDisponivel()) {
            carro.bloquearAluguel();
            carroRepository.save(carro);
        }

        aluguelRepository.save(aluguel); // Otimização: não precisa retornar o aluguel salvo
        log.info("Aluguel confirmado com sucesso. ID do aluguel: {}", idAluguel);
        return aluguel;
    }

    @Override
    @Transactional
    public Aluguel finalizarAluguel(Long idAluguel, LocalDate dataDevolucao) {
        log.info("Finalizando aluguel com ID: {} e data de devolução: {}", idAluguel, dataDevolucao);
        Aluguel aluguel = buscarAluguelPeloId(idAluguel);

        if (aluguel.getStatusAluguel() != StatusAluguel.CONFIRMADO) {
            log.warn("Aluguel não pode ser finalizado, pois não está confirmado. ID do aluguel: {}", idAluguel);
            throw new IllegalStateException("Aluguel não pode ser finalizado, pois não está confirmado.");
        }

        validarDataDevolucao(dataDevolucao, aluguel.getDataEntrega());

        aluguel.setStatusAluguel(StatusAluguel.FINALIZADO);
        aluguel.setDataDevolucaoEfetiva(dataDevolucao);

        // Calcular valor total final com base na data de devolução
        BigDecimal valorTotalFinal = calcularValorTotalFinal(aluguel, dataDevolucao);
        aluguel.setValorTotalFinal(valorTotalFinal);

        // Liberar o carro
        Carro carro = aluguel.getCarro();
        carro.disponibilizarAluguel();
        carroRepository.save(carro);

        aluguelRepository.save(aluguel); // Otimização: não precisa retornar o aluguel salvo
        log.info("Aluguel finalizado com sucesso. ID do aluguel: {}", idAluguel);
        return aluguel;
    }

    @Override
    @Transactional
    public Aluguel cancelarAluguel(Long idAluguel) {
        log.info("Cancelando aluguel com ID: {}", idAluguel);
        Aluguel aluguel = buscarAluguelPeloId(idAluguel);

        if (aluguel.getStatusAluguel() == StatusAluguel.FINALIZADO) {
            log.warn("Aluguel não pode ser cancelado, pois já foi finalizado. ID do aluguel: {}", idAluguel);
            throw new IllegalStateException("Aluguel não pode ser cancelado, pois já foi finalizado.");
        }

        LocalDate dataAtual = now();
        LocalDate dataLimiteCancelamento = aluguel.getDataEntrega().minusDays(2); // 48 horas antes da data de retirada

        if (dataAtual.isAfter(dataLimiteCancelamento)) {
            log.warn("Aluguel não pode ser cancelado, pois já passou do prazo de 48 horas antes da retirada. ID do aluguel: {}", idAluguel);
            throw new IllegalStateException("Aluguel não pode ser cancelado, pois já passou do prazo de 48 horas antes da retirada.");
        }

        aluguel.setStatusAluguel(StatusAluguel.CANCELADO);

        // Liberar o carro (se estiver bloqueado) - otimização: verificar se é necessário
        Carro carro = aluguel.getCarro();

        if (!carro.isDisponivel()) {
            carro.disponibilizarAluguel();
            carroRepository.save(carro);
        }

        aluguelRepository.save(aluguel); // Otimização: não precisa retornar o aluguel salvo
        log.info("Aluguel cancelado com sucesso. ID do aluguel: {}", idAluguel);
        return aluguel;
    }

    @Override
    public Page<DadosListagemAluguel> listarAlugueis(Pageable paginacao) {
        log.info("Listando alugueis com paginação: {}", paginacao);
        Page<Aluguel> alugueis = aluguelRepository.findAll(paginacao);
        log.info("Alugueis listados com sucesso: {}", alugueis);
        return alugueis.map(DadosListagemAluguel::new);
    }

    @Override
    public Page<DadosListagemAluguel> listarAlugueisPorCliente(Long idCliente, Pageable paginacao) {
        log.info("Listando alugueis do cliente com ID: {}", idCliente);

        // 1. Buscar o motorista associado ao cliente (assumindo que o cliente está relacionado ao motorista)
        Motorista motorista = motoristaRepository.findById(idCliente)
                .orElseThrow(() -> {
                            log.warn("Motorista não encontrado com o ID: {}", idCliente);
                            return new EntityNotFoundException("Motorista não encontrado com o ID: " + idCliente);
                        }
                );

        // 2. Buscar os alugueis associados ao motorista
        Page<Aluguel> alugueis = aluguelRepository.findAllByMotorista(motorista, paginacao);

        log.info("Alugueis do cliente listados com sucesso: {}", alugueis);
        return alugueis.map(DadosListagemAluguel::new);
    }

    private Aluguel buscarAluguelPeloId(Long idAluguel) {
        return aluguelRepository.findById(idAluguel)
                .orElseThrow(() -> {
                    log.warn("Aluguel não encontrado com o ID: {}", idAluguel);
                    return new EntityNotFoundException("Aluguel não encontrado com o ID: " + idAluguel);
                });
    }

    private Motorista buscarMotoristaPorEmail(String email) {
        return motoristaRepository.findByEmail(email)
                .orElseThrow(() -> {
                            log.warn("Motorista não encontrado com o e-mail: {}", email);
                            return new EntityNotFoundException("Motorista não encontrado com o e-mail: " + email);
                        }
                );
    }

    private Carro buscarCarroPorId(Long idCarro) {
        return carroRepository.findById(idCarro)
                .orElseThrow(() -> {
                            log.warn("Carro não encontrado com o ID: {}", idCarro);
                            return new EntityNotFoundException("Carro não encontrado com o ID: " + idCarro);
                        }
                );
    }

    private void validarDisponibilidadeDoCarro(Carro carro) {
        if (!carro.isDisponivel()) {
            log.warn("Carro indisponível para aluguel com o ID: {}", carro.getId());
            throw new EntityNotFoundException("Carro indisponível para aluguel com o ID: " + carro.getId());
        }
    }

    private void validarDatasDeAluguel(LocalDate dataRetirada, LocalDate dataDevolucaoPrevista) {
        LocalDate dataAtual = now();

        if (!dataRetirada.isAfter(dataAtual)) {
            log.warn("Data de retirada inválida: {}", dataRetirada);
            throw new IllegalArgumentException("Data de retirada inválida");
        }

        if (!dataDevolucaoPrevista.isAfter(dataAtual)) {
            log.warn("Data de devolução prevista inválida: {}", dataDevolucaoPrevista);
            throw new IllegalArgumentException("Data de devolução prevista inválida");
        }

        if (!dataDevolucaoPrevista.isAfter(dataRetirada)) {
            log.warn("Data de devolução prevista menor ou igual à data de retirada: {} <= {}", dataDevolucaoPrevista, dataRetirada);
            throw new IllegalArgumentException("Data de devolução prevista menor ou igual à data de retirada");
        }
    }

    private BigDecimal calcularValorTotalInicial(DadosCadastroAluguel dadosCadastroAluguel, Carro carro) {
        long diasAluguel = DAYS.between(dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());
        BigDecimal valorDiario = carro.getValorDiaria(); // Valor diário do carro
        BigDecimal valorSeguro = dadosCadastroAluguel.apoliceSeguro().valorFranquia(); // Valor da franquia do seguro
        BigDecimal valorTotalAluguel = valorDiario.multiply(BigDecimal.valueOf(diasAluguel)); // Valor total do aluguel = valor diário * dias de aluguel + valor franquia
        return valorTotalAluguel.add(valorSeguro);
    }

    private BigDecimal calcularValorTotalFinal(Aluguel aluguel, LocalDate dataDevolucao) {
        long diasAluguel = DAYS.between(aluguel.getDataEntrega(), dataDevolucao);
        BigDecimal valorDiario = aluguel.getCarro().getValorDiaria();
        BigDecimal seguro = aluguel.getApoliceSeguro().getValorFranquia();
        return valorDiario.multiply(BigDecimal.valueOf(diasAluguel)).add(seguro);
    }

    private void validarDataDevolucao(LocalDate dataDevolucao, LocalDate dataEntrega) {
        if (dataDevolucao.isBefore(dataEntrega)) {
            log.warn("Data de devolução inválida: {} (anterior à data de entrega: {})", dataDevolucao, dataEntrega);
            throw new IllegalArgumentException("Data de devolução inválida: anterior à data de entrega.");
        }
    }

    private Aluguel criarAluguel(Carro carro,
                                 Motorista motorista,
                                 ApoliceSeguro apoliceSeguro,
                                 DadosCadastroAluguel dadosCadastroAluguel,
                                 BigDecimal valorTotalInicial) {
        Aluguel aluguel = new Aluguel();
        aluguel.adicionarCarro(carro);
        aluguel.adicionarMotorista(motorista);
        aluguel.adicionarApoliceSeguro(apoliceSeguro);
        aluguel.setDataPedido(now());
        aluguel.setDataEntrega(dadosCadastroAluguel.dataRetirada());
        aluguel.setDataDevolucaoPrevista(dadosCadastroAluguel.dataDevolucaoPrevista());
        aluguel.setStatusAluguel(StatusAluguel.PENDENTE);
        aluguel.setValorTotalInicial(valorTotalInicial);
        return aluguel;
    }
}