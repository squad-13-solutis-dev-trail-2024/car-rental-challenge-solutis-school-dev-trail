package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosDetalhamentoAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.aluguel.DadosPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.*;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.TipoPagamento;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.generator.BoletoBarras;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.generator.PixKey;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.*;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.AluguelService;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.spec.AluguelSpecs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ApoliceSeguro.calcularValorTotalApoliceSeguro;
import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel.*;
import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusPagamento.CONFIRMADO;
import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusPagamento.PENDENTE;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.jpa.domain.Specification.where;

@Service("AluguelService")
@Schema(description = "Serviço que implementa as operações de aluguel")
public class AluguelServiceImpl implements AluguelService {

    private static final Logger log = getLogger(AluguelServiceImpl.class);

    private final AluguelRepository aluguelRepository;
    private final ApoliceSeguroRepository apoliceSeguroRepository;
    private final MotoristaRepository motoristaRepository;
    private final CarroRepository carroRepository;
    private final CarrinhoAluguelRepository carrinhoAluguelRepository;

    public AluguelServiceImpl(AluguelRepository aluguelRepository,
                              MotoristaRepository motoristaRepository,
                              CarroRepository carroRepository,
                              ApoliceSeguroRepository apoliceSeguroRepository,
                              CarrinhoAluguelRepository carrinhoAluguelRepository
    ) {
        this.aluguelRepository = aluguelRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
        this.apoliceSeguroRepository = apoliceSeguroRepository;
        this.carrinhoAluguelRepository = carrinhoAluguelRepository;
    }

    @Override
    public Aluguel buscarPorId(Long id) {
        log.info("Buscando aluguel por ID: {}", id);
        Aluguel aluguel = buscarAluguelPeloId(id);
        log.info("Aluguel encontrado: {}", aluguel);
        return aluguel;
    }

    @Transactional
    @Override
    public Aluguel reservarCarro(@Valid DadosCadastroAluguel dadosCadastroAluguel) {
        log.info("Iniciando processo de aluguel para o motorista: {}", dadosCadastroAluguel.emailMotorista());

        Motorista motorista = buscarMotoristaPorEmail(dadosCadastroAluguel.emailMotorista());
        log.info("Motorista encontrado: {}", motorista);

        if (!motorista.getAtivo()) {
            log.warn("Motorista não pode alugar veículos, pois está desativado. Email do motorista: {}", dadosCadastroAluguel.emailMotorista());
            throw new ValidationException("Motorista não pode alugar veículos, pois está desativado.");
        }

        log.debug("Validando disponibilidade dos carros: {}", dadosCadastroAluguel.carrosIds());

        List<Carro> carrosParaAluguel = new ArrayList<>();

        for (Long carroId : dadosCadastroAluguel.carrosIds()) {
            Carro carro = buscarCarroPorId(carroId);
            validarDisponibilidadeDoCarro(carro);
            carrosParaAluguel.add(carro);
        }
        log.info("Carros disponíveis para aluguel: {}", dadosCadastroAluguel.carrosIds());

        log.debug("Validando datas de aluguel: retirada {} e devolução {}", dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());
        validarDatasDeAluguel(dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());
        log.info("Datas de aluguel validadas com sucesso");

        // Verifica se o motorista já possui um carrinho
        Optional<CarrinhoAluguel> carrinhoExistente = carrinhoAluguelRepository.findByMotoristaId(motorista.getId());

        CarrinhoAluguel carrinhoAluguel = carrinhoExistente.orElse(new CarrinhoAluguel());
        carrinhoAluguel.setMotorista(motorista);

        // Limpa o carrinho se já existir
        if (carrinhoExistente.isPresent()) {
            carrinhoAluguel.getVeiculos().clear();
            log.info("Carrinho de aluguel do motorista com ID {} foi limpo.", motorista.getId());
        }

        // Adiciona os carros ao carrinho (cria um novo se não existir)
        for (Carro carro : carrosParaAluguel) {
            if (!carrinhoAluguel.getVeiculos().contains(carro)) {
                carrinhoAluguel.adicionarCarro(carro);
                log.info("Carro adicionado ao carrinho: {}", carro.getId());
            } else {
                log.warn("O carro já está no carrinho: {}", carro.getId());
                throw new ValidationException("O veículo já está no carrinho.");
            }
        }

        carrinhoAluguelRepository.save(carrinhoAluguel);

        ApoliceSeguro apoliceSeguro = apoliceSeguroRepository.findByProtecaoCausasNaturaisAndProtecaoTerceiroAndProtecaoRoubo(
                dadosCadastroAluguel.apoliceSeguro().getProtecaoTerceiro(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoCausasNaturais(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoRoubo()
        );

        log.info("Apólice de seguro criada: {}", apoliceSeguro);

        log.debug("Calculando valor total inicial do aluguel");
        BigDecimal valorTotalInicial = calcularValorTotalInicial(dadosCadastroAluguel, carrosParaAluguel);
        log.info("Valor total inicial calculado: {}", valorTotalInicial);

        log.debug("Criando objeto Aluguel");
        Aluguel aluguel = criarAluguel(carrosParaAluguel, motorista, apoliceSeguro, dadosCadastroAluguel, valorTotalInicial);
        log.info("Objeto Aluguel criado: {}", aluguel);

        aluguel.adicionarCarrinhoAluguel(carrinhoAluguel);

        for (Carro carro : carrosParaAluguel) {
            log.debug("Bloqueando carro para o aluguel");
            carro.bloquearAluguel();
            carroRepository.save(carro);
            log.info("Carro bloqueado e salvo: {}", carro);
        }

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

        // Obtém os carros do carrinho do aluguel
        List<Carro> carros = aluguel.getCarrinhoAluguel().getVeiculos().stream().toList();

        log.debug("Bloqueando carro para o aluguel. ID do carro: {}", aluguel.getCarrinhoAluguel().getVeiculos().stream().toList());
        // Bloqueia os carros para o aluguel
        carros.forEach(this::bloquearCarrosParaAluguel);

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

        List<Carro> carros = aluguel.getCarrinhoAluguel().getVeiculos().stream().toList();

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

        Long idCarro = aluguel.getCarrinhoAluguel().getVeiculos().stream().findFirst().get().getId();

        log.debug("Liberando carro(s) para novos aluguéis. ID do carro: {}", idCarro);
        carros.forEach(this::liberarCarrosParaAluguel);

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

        List<Carro> carros = aluguel.getCarrinhoAluguel().getVeiculos().stream().toList();

        log.debug("Cancelando o aluguel. Alterando status para CANCELADO. ID do aluguel: {}", idAluguel);
        aluguel.setStatusAluguel(StatusAluguel.CANCELADO);
        aluguel.setStatusPagamento(StatusPagamento.CANCELADO);
        aluguel.setDataDevolucaoEfetiva(null);
        aluguel.setValorTotalFinal(null);
        aluguel.setDataPagamento(null);
        aluguel.setDataCancelamento(now());

        log.debug("Liberando carro para novos aluguéis, se necessário. ID do carro: {}", aluguel.getCarrinhoAluguel().getId());
        // Libera os carros para novos aluguéis, se necessário
        carros.forEach(this::liberarCarrosParaAluguel);

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

    @Override
    public Page<DadosDetalhamentoAluguel> pesquisarAlugueis(
            LocalDate dataPedido,
            LocalDate dataRetirada,
            LocalDate dataDevolucaoPrevista,
            LocalDate dataDevolucaoEfetiva,
            BigDecimal valorTotalInicialMin,
            BigDecimal valorTotalInicialMax,
            BigDecimal valorTotalFinalMin,
            BigDecimal valorTotalFinalMax,
            StatusAluguel status,
            String motoristaNome,
            String motoristaCpf,
            String motoristaCnh,
            String carroNome,
            String carroPlaca,
            String carroCor,
            String modeloDescricaoCarro,
            String fabricanteNomeCarro,
            String categoriaNomeCarro,
            List<String> acessoriosNomesCarro,
            Pageable paginacao
    ) {
        log.info("Iniciando pesquisa de aluguéis com os seguintes critérios:");
        log.info("Data do pedido: {}", dataPedido);
        log.info("Data de retirada: {}", dataRetirada);
        log.info("Data de devolução prevista: {}", dataDevolucaoPrevista);
        log.info("Data de devolução efetiva: {}", dataDevolucaoEfetiva);
        log.info("Valor total inicial mínimo: {}", valorTotalInicialMin);
        log.info("Valor total inicial máximo: {}", valorTotalInicialMax);
        log.info("Valor total final mínimo: {}", valorTotalFinalMin);
        log.info("Valor total final máximo: {}", valorTotalFinalMax);
        log.info("Status: {}", status);
        log.info("Nome do motorista: {}", motoristaNome);
        log.info("CPF do motorista: {}", motoristaCpf);
        log.info("CNH do motorista: {}", motoristaCnh);
        log.info("Nome do carro: {}", carroNome);
        log.info("Placa do carro: {}", carroPlaca);
        log.info("Cor do carro: {}", carroCor);
        log.info("Descrição do modelo do carro: {}", modeloDescricaoCarro);
        log.info("Nome do fabricante do carro: {}", fabricanteNomeCarro);
        log.info("Nome da categoria do carro: {}", categoriaNomeCarro);
        log.info("Nomes dos acessórios do carro: {}", acessoriosNomesCarro);

        Specification<Aluguel> spec = where(null);

        spec = addSpecificationIfNotNull(spec, dataPedido, AluguelSpecs::dataPedidoEquals);
        spec = addSpecificationIfNotNull(spec, dataRetirada, AluguelSpecs::dataRetiradaEquals);
        spec = addSpecificationIfNotNull(spec, dataDevolucaoPrevista, AluguelSpecs::dataDevolucaoPrevistaEquals);
        spec = addSpecificationIfNotNull(spec, dataDevolucaoEfetiva, AluguelSpecs::dataDevolucaoEfetivaEquals);
        spec = addSpecificationIfNotNull(spec, valorTotalInicialMin, AluguelSpecs::valorTotalInicialGreaterThanOrEqual);
        spec = addSpecificationIfNotNull(spec, valorTotalInicialMax, AluguelSpecs::valorTotalInicialLessThanOrEqual);
        spec = addSpecificationIfNotNull(spec, valorTotalFinalMin, AluguelSpecs::valorTotalFinalGreaterThanOrEqual);
        spec = addSpecificationIfNotNull(spec, valorTotalFinalMax, AluguelSpecs::valorTotalFinalLessThanOrEqual);
        spec = addSpecificationIfNotNull(spec, status, AluguelSpecs::statusEquals);
        spec = addSpecificationIfNotNull(spec, motoristaNome, AluguelSpecs::motoristaNomeContains);
        spec = addSpecificationIfNotNull(spec, motoristaCpf, AluguelSpecs::motoristaCpfEquals);
        spec = addSpecificationIfNotNull(spec, motoristaCnh, AluguelSpecs::motoristaCnhEquals);
        spec = addSpecificationIfNotNull(spec, carroNome, AluguelSpecs::carroNomeContains);
        spec = addSpecificationIfNotNull(spec, carroPlaca, AluguelSpecs::carroPlacaEquals);
        spec = addSpecificationIfNotNull(spec, carroCor, AluguelSpecs::carroCorEquals);
        spec = addSpecificationIfNotNull(spec, modeloDescricaoCarro, AluguelSpecs::modeloDescricaoCarroContains);
        spec = addSpecificationIfNotNull(spec, fabricanteNomeCarro, AluguelSpecs::fabricanteNomeCarroContains);
        spec = addSpecificationIfNotNull(spec, categoriaNomeCarro, AluguelSpecs::categoriaNomeCarroEquals);

        if (acessoriosNomesCarro != null && !acessoriosNomesCarro.isEmpty()) {
            spec = spec.and(AluguelSpecs.carroTemAcessorios(acessoriosNomesCarro));
        }

        Page<DadosDetalhamentoAluguel> resultados = aluguelRepository.findAll(spec, paginacao).map(DadosDetalhamentoAluguel::new);
        log.info("Pesquisa de aluguéis concluída. Número de resultados encontrados: {}", resultados.getTotalElements());
        return resultados;
    }

    private <T, E> Specification<E> addSpecificationIfNotNull(
            Specification<E> spec,
            T value,
            Function<T, Specification<E>> specBuilder
    ) {
        return value != null ? spec.and(specBuilder.apply(value)) : spec;
    }

    @Override
    @Transactional
    public Aluguel trocarCarro(@Valid Long idAluguel, Long idCarroNovo) {
        log.info("Iniciando a troca de carro para o aluguel com ID: {}", idAluguel);
        Aluguel aluguel = buscarAluguelPeloId(idAluguel);
        log.info("Aluguel encontrado: {}", aluguel);

        if (aluguel.getStatusPagamento() != PENDENTE) {
            log.warn("Troca de carro não permitida para o aluguel com ID: {}, status do pagamento diferente de PENDENTE.", idAluguel);
            throw new ValidationException("Troca permitida somente quando o status estiver pendente");
        }

        CarrinhoAluguel carrinhoAluguel = aluguel.getCarrinhoAluguel();

        // Valida se o novo carro já está no carrinho
        if (carrinhoAluguel.getVeiculos().stream().anyMatch(carro -> carro.getId().equals(idCarroNovo))) {
            log.warn("O carro com ID {} já está no carrinho do aluguel com ID {}.", idCarroNovo, idAluguel);
            throw new ValidationException("O carro já está no carrinho do aluguel.");
        }

        // Encontra o carro a ser removido (primeiro carro do carrinho)
        Carro carroParaRemover = carrinhoAluguel.getVeiculos().iterator().next();
        log.info("Carro a ser removido do aluguel: {}", carroParaRemover);

        // Remove o carro do carrinho
        carrinhoAluguel.retirarCarro(carroParaRemover);

        // Disponibiliza o carro removido
        carroParaRemover.disponibilizarAluguel();
        carroRepository.save(carroParaRemover);

        // Busca o novo carro a ser adicionado
        Carro carroNovo = buscarCarroPorId(idCarroNovo);
        log.info("Novo carro a ser adicionado ao aluguel: {}", carroNovo);

        // Valida a disponibilidade do novo carro
        if (!carroNovo.isDisponivel()) throw new ValidationException("Carro esta indisponivel");


        // Adiciona o novo carro ao carrinho
        carrinhoAluguel.adicionarCarro(carroNovo);
        carrinhoAluguelRepository.save(carrinhoAluguel);

        // Bloqueia o novo carro
        carroNovo.bloquearAluguel();
        carroRepository.save(carroNovo);

        // Recalcula o valor total inicial do aluguel
        BigDecimal valorTotalInicialAluguel = calcularValorTotalInicial(aluguel, carrinhoAluguel.getVeiculos().stream().toList());
        BigDecimal apolice = calcularApolice(aluguel.getApoliceSeguro());
        aluguel.setValorTotalInicial(valorTotalInicialAluguel.add(apolice));
        aluguel.setValorTotalFinal(null);
        aluguelRepository.save(aluguel);

        log.info("Troca de carro concluída com sucesso para o aluguel com ID: {}", idAluguel);
        return aluguel;
    }


    private void processarPagamento(long aluguel, DadosPagamento tipoPagamento) {
        log.info("Iniciando o processamento do pagamento para o aluguel com ID: {}", aluguel);
        TipoPagamento modalidadePagamento = tipoPagamento.tipoPagamento();

        Optional<Aluguel> aluguelTipoPagamento = aluguelRepository.findById(aluguel);
        Aluguel aluguelEncontrado = aluguelTipoPagamento.orElseThrow(() -> new RuntimeException("Aluguel não encontrado com ID: " + aluguel));
        log.info("Aluguel encontrado para processamento do pagamento: {}", aluguelEncontrado);

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

        log.info("Processamento do pagamento concluído com sucesso para o aluguel com ID: {}", aluguel);
        aluguelRepository.save(aluguelEncontrado);
    }

    private void liberarCarrosParaAluguel(Carro carros) {
        log.debug("Verificando disponibilidade do carro para liberação. ID do carro: {}", carros.getId());

        if (!carros.isDisponivel()) {
            log.debug("Carro bloqueado, realizando liberação. ID do carro: {}", carros.getId());
            carros.disponibilizarAluguel();
            carroRepository.save(carros);
            log.info("Carro liberado com sucesso. ID do carro: {}", carros.getId());
        } else {
            log.debug("Carro já está disponível. Nenhuma ação necessária. ID do carro: {}", carros.getId());
        }
    }

    private void bloquearCarrosParaAluguel(Carro carros) {
        log.debug("Verificando disponibilidade do carro para bloqueio. ID do carro: {}", carros.getId());

        if (carros.isDisponivel()) {
            log.debug("Carro disponível, realizando bloqueio. ID do carro: {}", carros.getId());
            carros.bloquearAluguel();
            carroRepository.save(carros);
            log.info("Carro bloqueado com sucesso. ID do carro: {}", carros.getId());
        } else {
            log.debug("Carro já está bloqueado. Nenhuma ação necessária. ID do carro: {}", carros.getId());
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
                .orElseThrow(
                        () -> {
                            log.warn("Aluguel não encontrado com o ID: {}", idAluguel);
                            return new EntityNotFoundException("Aluguel não encontrado com o ID: " + idAluguel);
                        }
                );
    }

    private Motorista buscarMotoristaPorEmail(String email) {
        log.debug("Buscando motorista pelo e-mail: {}", email);
        return motoristaRepository.findByEmail(email)
                .orElseThrow(
                        () -> {
                            log.warn("Motorista não encontrado com o e-mail: {}", email);
                            return new EntityNotFoundException("Motorista não encontrado com o e-mail: " + email);
                        }
                );
    }

    private Carro buscarCarroPorId(Long idCarro) {
        log.debug("Buscando carro pelo ID: {}", idCarro);
        return carroRepository.findById(idCarro)
                .orElseThrow(
                        () -> {
                            log.warn("Carro não encontrado com o ID: {}", idCarro);
                            return new EntityNotFoundException("Carro não encontrado com o ID: " + idCarro);
                        }
                );
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


    private BigDecimal calcularApolice(ApoliceSeguro apoliceSeguro) {
        boolean protecaoTerceiro = apoliceSeguro.getProtecaoTerceiro();
        boolean protecaoCausasNaturais = apoliceSeguro.getProtecaoCausasNaturais();
        boolean protecaoRoubo = apoliceSeguro.getProtecaoRoubo();

        return calcularValorTotalApoliceSeguro(protecaoTerceiro, protecaoCausasNaturais, protecaoRoubo);
    }

    private BigDecimal calcularValorTotalInicial(Aluguel aluguel, List<Carro> listaDeCarros) {
        BigDecimal valorTotalInicial = BigDecimal.ZERO;

        for (Carro carro : listaDeCarros) {
            valorTotalInicial = valorTotalInicial.add(carro.getValorDiaria());
        }

        BigDecimal apolice = calcularApolice(aluguel.getApoliceSeguro());

        return valorTotalInicial.add(apolice);
    }


    private BigDecimal calcularValorTotalInicial(@Valid DadosCadastroAluguel dadosCadastroAluguel, List<Carro> carros) {
        log.debug("Calculando valor total inicial para o aluguel. IDs dos carros: {}",
                carros.stream().map(Carro::getId).toList());

        long diasParciaisDeAluguel = DAYS.between(dadosCadastroAluguel.dataRetirada(), dadosCadastroAluguel.dataDevolucaoPrevista());

        // Calcula o valor total das diárias de todos os carros
        BigDecimal valorTotalDiarias = carros.stream()
                .map(carro -> carro.getValorDiaria().multiply(BigDecimal.valueOf(diasParciaisDeAluguel)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ApoliceSeguro apoliceSeguro = apoliceSeguroRepository.findByProtecaoCausasNaturaisAndProtecaoTerceiroAndProtecaoRoubo(
                dadosCadastroAluguel.apoliceSeguro().getProtecaoTerceiro(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoCausasNaturais(),
                dadosCadastroAluguel.apoliceSeguro().getProtecaoRoubo()
        );

        BigDecimal valorApoliceSeguro = calcularValorTotalApoliceSeguro(
                apoliceSeguro.getProtecaoTerceiro(),
                apoliceSeguro.getProtecaoCausasNaturais(),
                apoliceSeguro.getProtecaoRoubo()
        );

        BigDecimal valorTotalParcialInicial = valorTotalDiarias.add(valorApoliceSeguro);

        log.debug("Valor total das diárias: {}, Dias de aluguel: {}, Valor da apólice: {}", valorTotalDiarias, diasParciaisDeAluguel, valorApoliceSeguro);
        log.debug("Valor total inicial calculado: {}", valorTotalParcialInicial);

        return valorTotalParcialInicial;
    }

    private BigDecimal calcularValorTotalFinal(Aluguel aluguel, LocalDate dataDevolucaoEfetiva) {
        log.debug("Calculando valor total final para o aluguel. ID do aluguel: {}", aluguel.getId());

        long diasAluguel = DAYS.between(aluguel.getDataRetirada(), dataDevolucaoEfetiva);

        // Obtém a lista de carros do carrinho do aluguel
        List<Carro> carros = aluguel.getCarrinhoAluguel().getVeiculos().stream().toList();

        // Calcula o valor total das diárias de todos os carros
        BigDecimal valorTotalDiarias = carros.stream()
                .map(carro -> carro.getValorDiaria().multiply(BigDecimal.valueOf(diasAluguel)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ApoliceSeguro apoliceSeguro = aluguel.getApoliceSeguro();

        BigDecimal valorApoliceSeguro = calcularValorTotalApoliceSeguro(
                apoliceSeguro.getProtecaoTerceiro(),
                apoliceSeguro.getProtecaoCausasNaturais(),
                apoliceSeguro.getProtecaoRoubo()
        );

        BigDecimal valorTotalFinal = valorTotalDiarias.add(valorApoliceSeguro);

        log.debug("Valor total das diárias: {}, Dias de aluguel: {}, Valor da apólice: {}", valorTotalDiarias, diasAluguel, valorApoliceSeguro);
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

    private Aluguel criarAluguel(
            List<Carro> carros, // Recebe uma lista de carros
            Motorista motorista,
            ApoliceSeguro apoliceSeguro,
            DadosCadastroAluguel dadosCadastroAluguel,
            BigDecimal valorTotalInicial
    ) {
        log.debug("Criando novo aluguel. IDs dos carros: {}, ID do motorista: {}",
                carros.stream().map(Carro::getId).toList(), // Obtém a lista de IDs dos carros
                motorista.getId());

        Aluguel aluguel = new Aluguel();
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