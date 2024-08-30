package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro.DadosAlugarCarro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Aluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.AluguelRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.CarroRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.MotoristaRepository;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.AluguelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

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
@Log4j2
public class AluguelServiceImpl implements AluguelService {

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
    public Aluguel findByid(Long id) {
        Optional<Aluguel> aluguel = aluguelRepository.findById(id);
        if (aluguel.isEmpty()) log.warn("Informações do aluguel não encontrado!");
        return aluguel.orElse(null);
    }

    @Override
    @Transactional
    public Aluguel alugar(@Valid DadosAlugarCarro alugar) {

        Aluguel aluguel = new Aluguel();

        if (motoristaRepository.existsByEmail(alugar.emailMotorista())) {
            Motorista motorista = motoristaRepository.findByEmail(alugar.emailMotorista());
            Carro carro = carroRepository.findById(alugar.idCarro())
                    .orElseThrow(() -> new RuntimeException("Carro não encontrado com o ID: " + alugar.idCarro()));
            if (carro.isDisponivel()) {
                carro.bloquearAluguel();
                carroRepository.save(carro);
                aluguel.setMotorista(motorista);
                aluguel.setCarro(carro);
                aluguel.setDataEntrega(alugar.dataEntrega());
                aluguel.setDataDevolucaoPrevista(alugar.dataDevolucao());
                aluguel.setDataPedido(LocalDate.from(Instant.now()));
                return aluguelRepository.save(aluguel);
            } else {
                throw new RuntimeException("Carro indisponivel para aluguel");
            }
        } else {
            throw new RuntimeException("Email invalido ou inexistente");
        }
    }
}