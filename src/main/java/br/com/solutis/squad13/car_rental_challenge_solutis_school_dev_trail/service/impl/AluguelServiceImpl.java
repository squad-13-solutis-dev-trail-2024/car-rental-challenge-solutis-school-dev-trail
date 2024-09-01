package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.TipoPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.gen.BoletoBarras;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.gen.PixKey;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.AluguelRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.ApoliceSeguroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.AluguelService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro.calcularValorTotalApoliceSeguro;
import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel.*;
import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusPagamento.CONFIRMADO;
import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusPagamento.PENDENTE;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.slf4j.LoggerFactory.getLogger;

/*
    História de Usuário:

    Escolha de Veículo para Aluguel
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
    private final ApoliceSeguroRepository apoliceSeguroRepository;

    public AluguelServiceImpl(AluguelRepository aluguelRepository,
                              MotoristaRepository motoristaRepository,
                              CarroRepository carroRepository, ApoliceSeguroRepository apoliceSeguroRepository) {
        this.aluguelRepository = aluguelRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
        this.apoliceSeguroRepository = apoliceSeguroRepository;
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
        log.info("Iniciando processo de aluguel para o motorista: {}", dadosCadastroAluguel.emailMotorista());

        Motorista motorista = buscarMotoristaPorEmail(dadosCadastroAluguel.emailMotorista());
        log.info("Motorista encontrado: {}", motorista);

        boolean motoristaAtivo = motorista.getAtivo();

        if (!motoristaAtivo) {
            log.warn("Motorista não pode alugar veículos, pois está desativado. Email do motorista: {}", dadosCadastroAluguel.emailMotorista());
            throw new ValidationException("Motorista não pode alugar veículos, pois está desativado.");
        }

        Carro carro = buscarCarroPorId(dadosCadastroAluguel.idCarro());
        log.info("Carro encontrado: {}", carro);

        log.debug("Validando disponibilidade do carro: {}", carro);
        validarDisponibilidadeDoCarro(carro);
        log.info("Carro disponível para aluguel: {}", carro);

        log.debug("Validando datas de aluguel: retirada {} e devolução {}", dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());
        validarDatasDeAluguel(dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());
        log.info("Datas de aluguel validadas com sucesso");

        ApoliceSeguro apoliceSeguro = apoliceSeguroRepository.findByProtecaoCausasNaturaisAndProtecaoTerceiroAndProtecaoRoubo(
                dadosCadastroAluguel.apoliceSeguro().getProtecaoTerceiro(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoCausasNaturais(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoRoubo()
        );

        log.info("Apólice de seguro criada: {}", apoliceSeguro);

        log.debug("Calculando valorTotalParcial total inicial do aluguel");
        BigDecimal valorTotalInicial = calcularValorTotalInicial(dadosCadastroAluguel, carro);
        log.info("Valor total inicial calculado: {}", valorTotalInicial);

        log.debug("Criando objeto Aluguel");
        Aluguel aluguel = criarAluguel(carro, motorista, apoliceSeguro, dadosCadastroAluguel, valorTotalInicial);
        log.info("Objeto Aluguel criado: {}", aluguel);

        log.debug("Bloqueando carro para o aluguel");
        carro.bloquearAluguel();
        carroRepository.save(carro);
        log.info("Carro bloqueado e salvo: {}", carro);

        log.debug("Salvando informações de aluguel no banco de dados");
        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);
        log.info("Aluguel realizado com sucesso: {}", aluguelSalvo);

        return aluguelSalvo;
    }

    @Override
    @Transactional
    public Aluguel confirmarAluguel(Long idAluguel, DadosPagamento dadosPagamento) {
        log.info("Iniciando confirmação do aluguel com ID: {}", idAluguel);
        Aluguel aluguel = buscarAluguelPeloId(idAluguel);

        if (aluguel.getStatusAluguel() != INCOMPLETO) {
            log.warn("Aluguel não pode ser confirmado, pois não está Incompleto. ID do aluguel: {}", idAluguel);
            throw new ValidationException("Aluguel não pode ser confirmado, pois não está pendente.");
        }

        if (aluguel.getStatusPagamento() != PENDENTE) {
            log.warn("Aluguel não pode ser confirmado, pois o pagamento já foi realizado. ID do aluguel: {}", idAluguel);
            throw new ValidationException("Aluguel não pode ser confirmado, pois o pagamento já foi realizado.");
        }

        log.debug("Bloqueando carro para o aluguel. ID do carro: {}", aluguel.getCarro().getId());
        bloquearCarroParaAluguel(aluguel);

        // Lógica de pagamento
        processarPagamento(aluguel.getId(), dadosPagamento);
        log.debug("Pagamento processado com sucesso. ID do aluguel: {}", idAluguel);

        TipoPagamento tipoPagamento = dadosPagamento.tipoPagamento();

        aluguel.setTipoPagamento(tipoPagamento);
        aluguel.setStatusAluguel(EM_ANDAMENTO);
        aluguel.setStatusPagamento(CONFIRMADO);
        aluguel.setDataPagamento(now());

        aluguelRepository.save(aluguel);
        log.info("Aluguel confirmado com sucesso. ID do aluguel: {}", idAluguel);
        return aluguel;
    }

    @Override
    @Transactional
    public Aluguel finalizarAluguel(Long idAluguel, LocalDate dataDevolucaoDefinitiva) {
        log.info("Iniciando finalização do aluguel com ID: {} e data de devolução: {}", idAluguel, dataDevolucaoDefinitiva);

        Aluguel aluguel = buscarAluguelPeloId(idAluguel);

        if (aluguel.getStatusAluguel() != EM_ANDAMENTO) {
            log.warn("Aluguel não pode ser finalizado, pois não está confirmado. ID do aluguel: {}", idAluguel);
            throw new ValidationException("Aluguel não pode ser finalizado, pois não está confirmado.");
        }

        log.debug("Validando data de devolução: {} contra data de entrega: {}", dataDevolucaoDefinitiva, aluguel.getDataRetirada());
        validarDataDevolucao(dataDevolucaoDefinitiva, aluguel.getDataRetirada());

        log.debug("Finalizando o aluguel e registrando a data de devolução efetiva. ID do aluguel: {}", idAluguel);
        aluguel.setStatusAluguel(FINALIZADO);
        aluguel.setDataDevolucaoEfetiva(dataDevolucaoDefinitiva);

        log.debug("Calculando valorTotalParcial total final com base na data de devolução: {}", dataDevolucaoDefinitiva);
        BigDecimal valorTotalFinal = calcularValorTotalFinal(aluguel, dataDevolucaoDefinitiva);
        aluguel.setValorTotalFinal(valorTotalFinal);
        log.info("Valor total final calculado: {}", valorTotalFinal);

        if (valorTotalFinal.compareTo(aluguel.getValorTotalInicial()) < 0) {
            log.warn("Valor total final menor que o valorTotalParcial inicial. Valor total inicial: {}, Valor total final: {}", aluguel.getValorTotalInicial(), valorTotalFinal);
            BigDecimal diferenca = aluguel.getValorTotalInicial().subtract(valorTotalFinal);
            log.warn("Cliente deverá ser reembolsado. Diferença: {}", diferenca);
        }

        if (valorTotalFinal.compareTo(aluguel.getValorTotalInicial()) > 0) {
            log.warn("Valor total final maior que o valorTotalParcial inicial. Valor total inicial: {}, Valor total final: {}", aluguel.getValorTotalInicial(), valorTotalFinal);
            BigDecimal diferenca = valorTotalFinal.subtract(aluguel.getValorTotalInicial());
            log.warn("Cliente deverá pagar a diferença. Diferença: {}", diferenca);
        }

        Long idCarro = aluguel.getCarro().getId();

        log.debug("Liberando carro para novos aluguéis. ID do carro: {}", idCarro);
        liberarCarroParaAluguel(aluguel);

        aluguelRepository.save(aluguel);
        log.info("Aluguel finalizado com sucesso. ID do aluguel: {}", idAluguel);
        return aluguel;
    }

    @Override
    @Transactional
    public Aluguel cancelarAluguel(Long idAluguel) {
        log.info("Iniciando cancelamento do aluguel com ID: {}", idAluguel);

        Aluguel aluguel = buscarAluguelPeloId(idAluguel);
        log.debug("Aluguel encontrado: {}", aluguel);

        if (aluguel.getStatusAluguel() == FINALIZADO) {
            log.warn("Aluguel não pode ser cancelado, pois já foi finalizado. ID do aluguel: {}", idAluguel);
            throw new ValidationException("Aluguel não pode ser cancelado, pois já foi finalizado.");
        }

        log.debug("Validando prazo para cancelamento do aluguel. ID do aluguel: {}", idAluguel);
        validarPrazoCancelamentoAluguel(idAluguel, aluguel);

        log.debug("Cancelando o aluguel. Alterando status para CANCELADO. ID do aluguel: {}", idAluguel);
        aluguel.setStatusAluguel(StatusAluguel.CANCELADO);
        aluguel.setStatusPagamento(StatusPagamento.CANCELADO);
        aluguel.setDataDevolucaoEfetiva(null);
        aluguel.setValorTotalFinal(null);
        aluguel.setDataPagamento(null);
        aluguel.setDataCancelamento(now());

        log.debug("Liberando carro para novos aluguéis, se necessário. ID do carro: {}", aluguel.getCarro().getId());
        liberarCarroParaAluguel(aluguel);

        aluguelRepository.save(aluguel);
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

    private void processarPagamento(long aluguel, DadosPagamento tipoPagamento) {
        TipoPagamento modalidadePagamento = tipoPagamento.tipoPagamento();

        Optional<Aluguel> aluguelTipoPagamento = aluguelRepository.findById(aluguel);
        Aluguel aluguelEncontrado = aluguelTipoPagamento.orElseThrow(() -> new RuntimeException("Aluguel não encontrado com ID: " + aluguel));

        switch (modalidadePagamento) {
            case PIX -> aluguelEncontrado.setCampoPix(PixKey.generatePixKey());

            case BOLETO -> aluguelEncontrado.setCampoBoleto(BoletoBarras.gerarCodigoDeBarras());

            case CARTAO_CREDITO, CARTAO_DEBITO -> {
                aluguelEncontrado.setNumeroCartao(tipoPagamento.numeroCartao());
                aluguelEncontrado.setValidadeCartao(tipoPagamento.validadeCartao());
                aluguelEncontrado.setCvv(tipoPagamento.cvv());
            }

            case DINHEIRO -> aluguelEncontrado.setPagamentoDinheiro("Pagamento no local de recolhimento");

            default -> {
                log.warn("Tipo de pagamento inválido: {}", tipoPagamento);
                throw new ValidationException("Tipo de pagamento inválido.");
            }
        }

        aluguelRepository.save(aluguelEncontrado);
    }
    private void bloquearCarroParaAluguel(Aluguel aluguel) {
        Carro carro = aluguel.getCarro();
        log.debug("Verificando disponibilidade do carro para bloqueio. ID do carro: {}", carro.getId());

        boolean carroDisponivel = carro.isDisponivel();

        if (carroDisponivel) {
            log.debug("Carro disponível, realizando bloqueio. ID do carro: {}", carro.getId());
            carro.bloquearAluguel();
            carroRepository.save(carro);
            log.info("Carro bloqueado com sucesso. ID do carro: {}", carro.getId());
        } else {
            log.debug("Carro já está bloqueado. Nenhuma ação necessária. ID do carro: {}", carro.getId());
        }
    }

    private void liberarCarroParaAluguel(Aluguel aluguel) {
        Carro carro = aluguel.getCarro();
        log.debug("Verificando disponibilidade do carro para liberação. ID do carro: {}", carro.getId());

        boolean carroDisponivel = carro.isDisponivel();

        if (!carroDisponivel) {
            log.debug("Carro bloqueado, realizando liberação. ID do carro: {}", carro.getId());
            carro.disponibilizarAluguel();
            carroRepository.save(carro);
            log.info("Carro liberado com sucesso. ID do carro: {}", carro.getId());
        } else {
            log.debug("Carro já está disponível. Nenhuma ação necessária. ID do carro: {}", carro.getId());
        }
    }

    private void validarPrazoCancelamentoAluguel(Long idAluguel, Aluguel aluguel) {
        LocalDate dataAtual = now();
        LocalDate dataLimiteCancelamento = aluguel.getDataRetirada().minusDays(2); // 48 horas antes da data de retirada
        log.debug("Validando prazo para cancelamento do aluguel. Data atual: {}, Data limite: {}", dataAtual, dataLimiteCancelamento);

        if (dataAtual.isAfter(dataLimiteCancelamento)) {
            log.warn("Aluguel não pode ser cancelado, pois já passou do prazo de 48 horas antes da retirada. ID do aluguel: {}", idAluguel);
            throw new ValidationException("Aluguel não pode ser cancelado, pois já passou do prazo de 48 horas antes da retirada.");
        }
    }

    private Aluguel buscarAluguelPeloId(Long idAluguel) {
        log.debug("Buscando aluguel pelo ID: {}", idAluguel);
        return aluguelRepository.findById(idAluguel)
                .orElseThrow(() -> {
                    log.warn("Aluguel não encontrado com o ID: {}", idAluguel);
                    return new EntityNotFoundException("Aluguel não encontrado com o ID: " + idAluguel);
                });
    }

    private Motorista buscarMotoristaPorEmail(String email) {
        log.debug("Buscando motorista pelo e-mail: {}", email);
        return motoristaRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Motorista não encontrado com o e-mail: {}", email);
                    return new EntityNotFoundException("Motorista não encontrado com o e-mail: " + email);
                });
    }

    private Carro buscarCarroPorId(Long idCarro) {
        log.debug("Buscando carro pelo ID: {}", idCarro);
        return carroRepository.findById(idCarro)
                .orElseThrow(() -> {
                    log.warn("Carro não encontrado com o ID: {}", idCarro);
                    return new EntityNotFoundException("Carro não encontrado com o ID: " + idCarro);
                });
    }

    private void validarDisponibilidadeDoCarro(Carro carro) {
        log.debug("Validando disponibilidade do carro para aluguel. ID do carro: {}", carro.getId());
        if (!carro.isDisponivel()) {
            log.warn("Carro indisponível para aluguel com o ID: {}", carro.getId());
            throw new EntityNotFoundException("Carro indisponível para aluguel com o ID: " + carro.getId());
        }
    }

    private void validarDatasDeAluguel(LocalDate dataRetirada, LocalDate dataDevolucaoPrevista) {
        List<String> errosDatas = new ArrayList<>();
        LocalDate dataAtual = now();
        log.debug("Validando datas de aluguel. Data de retirada: {}, Data de devolução prevista: {}, Data atual: {}", dataRetirada, dataDevolucaoPrevista, dataAtual);

        if (!dataRetirada.isAfter(dataAtual)) {
            log.warn("Data de retirada inválida: {}", dataRetirada);
            errosDatas.add("A data de retirada deve ser posterior à data atual");
        }

        if (!dataDevolucaoPrevista.isAfter(dataAtual)) {
            log.warn("Data de devolução prevista inválida: {}", dataDevolucaoPrevista);
            errosDatas.add("A data de devolução prevista deve ser posterior à data atual");
        }

        if (!dataDevolucaoPrevista.isAfter(dataRetirada)) {
            log.warn("Data de devolução prevista menor ou igual à data de retirada: {} <= {}", dataDevolucaoPrevista, dataRetirada);
            errosDatas.add("A data de devolução prevista deve ser posterior à data de retirada");
        }

        if (!errosDatas.isEmpty()) {
            String mensagemErro = String.join("\n", errosDatas); // Junta as mensagens de erro com quebra de linha
            throw new ValidationException(mensagemErro);
        }
    }

    private BigDecimal calcularValorTotalInicial(@Valid DadosCadastroAluguel dadosCadastroAluguel, Carro carro) {
        log.debug("Calculando valorTotalParcial total inicial para o aluguel. ID do carro: {}", carro.getId());
        long diasParciaisDeAluguel = DAYS.between(dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());
        BigDecimal valorDiario = carro.getValorDiaria(); // Valor diário do carro

        ApoliceSeguro apoliceSeguro = apoliceSeguroRepository.findByProtecaoCausasNaturaisAndProtecaoTerceiroAndProtecaoRoubo(
                dadosCadastroAluguel.apoliceSeguro().getProtecaoTerceiro(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoCausasNaturais(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoRoubo()
        );

        boolean protecaoTerceiro = apoliceSeguro.getProtecaoTerceiro();
        boolean protecaoCausasNaturais = apoliceSeguro.getProtecaoCausasNaturais();
        boolean protecaoRoubo = apoliceSeguro.getProtecaoRoubo();

        BigDecimal valorApoliceSeguro = calcularValorTotalApoliceSeguro(protecaoTerceiro, protecaoCausasNaturais, protecaoRoubo);

        BigDecimal valorTotalParcialAluguel = valorDiario.multiply(BigDecimal.valueOf(diasParciaisDeAluguel)); // Valor total do aluguel = valorTotalParcial diário * dias de aluguel + valorTotalParcial franquia
        BigDecimal valorTotalParcialInicial = valorTotalParcialAluguel.add(valorApoliceSeguro);
        log.debug("Valor da diária: {}, Dias de aluguel: {}, Valor total do aluguel: {}", valorDiario, diasParciaisDeAluguel, valorTotalParcialAluguel);
        log.debug("Valor total inicial calculado: {}", valorTotalParcialInicial);
        return valorTotalParcialInicial;
    }

    private BigDecimal calcularValorTotalFinal(Aluguel aluguel, LocalDate dataDevolucaoEfetiva) {
        log.debug("Calculando valorTotalParcial total final para o aluguel. ID do aluguel: {}", aluguel.getId());
        long diasAluguel = DAYS.between(aluguel.getDataRetirada(), dataDevolucaoEfetiva);
        BigDecimal valorDiario = aluguel.getCarro().getValorDiaria();
        BigDecimal seguro = aluguel.getApoliceSeguro().getValorFranquia();
        BigDecimal valorTotalFinal = valorDiario.multiply(BigDecimal.valueOf(diasAluguel)).add(seguro);
        log.debug("Valor total final calculado: {}", valorTotalFinal);
        return valorTotalFinal;
    }

    private void validarDataDevolucao(LocalDate dataDevolucaoEfetiva, LocalDate dataRetirada) {
        log.debug("Validando data de devolução. Data de devolução: {}, Data de entrega: {}", dataDevolucaoEfetiva, dataRetirada);
        if (dataDevolucaoEfetiva.isBefore(dataRetirada)) {
            log.warn("Data de devolução inválida: {} (anterior à data de entrega: {})", dataDevolucaoEfetiva, dataRetirada);
            throw new ValidationException("Data de devolução inválida: anterior à data de entrega.");
        }
    }

    private Aluguel criarAluguel(Carro carro,
                                 Motorista motorista,
                                 ApoliceSeguro apoliceSeguro,
                                 DadosCadastroAluguel dadosCadastroAluguel,
                                 BigDecimal valorTotalInicial) {
        log.debug("Criando novo aluguel. ID do carro: {}, ID do motorista: {}", carro.getId(), motorista.getId());
        Aluguel aluguel = new Aluguel();
        aluguel.adicionarCarro(carro);
        aluguel.adicionarMotorista(motorista);
        aluguel.adicionarApoliceSeguro(apoliceSeguro);
        aluguel.setDataPedido(now());
        aluguel.setDataRetirada(dadosCadastroAluguel.dataRetirada());
        aluguel.setDataDevolucaoPrevista(dadosCadastroAluguel.dataDevolucaoPrevista());
        aluguel.setValorTotalInicial(valorTotalInicial);
        aluguel.setStatusAluguel(INCOMPLETO);
        aluguel.setStatusPagamento(PENDENTE);
        aluguel.setTipoPagamento(null);
        aluguel.setDataPagamento(null);
        aluguel.setDataDevolucaoEfetiva(null);
        aluguel.setDataCancelamento(null);
        log.debug("Aluguel criado com sucesso. ID do aluguel: {}", aluguel.getId());
        return aluguel;
    }
}