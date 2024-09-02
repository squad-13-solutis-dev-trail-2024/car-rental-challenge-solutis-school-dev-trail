package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.spec;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.*;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.StatusAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe utilitária para criar especificações JPA para a entidade {@link Aluguel}.
 * <p>
 * Esta classe fornece métodos estáticos que retornam objetos {@link Specification}
 * para construir consultas dinâmicas para a entidade {@link Aluguel},
 * permitindo a pesquisa de aluguéis com base em diferentes critérios.
 * </p>
 */
@Schema(description = "Classe utilitária para criar especificações JPA para a entidade Aluguel.")
public class AluguelSpecs {

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de pedido igual à especificada.
     *
     * @param dataPedido A data de pedido a ser pesquisada.
     * @return A especificação JPA para a pesquisa por data de pedido.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de pedido igual à especificada.")
    public static Specification<Aluguel> dataPedidoEquals(LocalDate dataPedido) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataPedido"), dataPedido);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de retirada igual à especificada.
     *
     * @param dataRetirada A data de retirada a ser pesquisada.
     * @return A especificação JPA para a pesquisa por data de retirada.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de retirada igual à especificada.")
    public static Specification<Aluguel> dataRetiradaEquals(LocalDate dataRetirada) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataRetirada"), dataRetirada);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de devolução prevista igual à especificada.
     *
     * @param dataDevolucaoPrevista A data de devolução prevista a ser pesquisada.
     * @return A especificação JPA para a pesquisa por data de devolução prevista.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de devolução prevista igual à especificada.")
    public static Specification<Aluguel> dataDevolucaoPrevistaEquals(LocalDate dataDevolucaoPrevista) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataDevolucaoPrevista"), dataDevolucaoPrevista);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de devolução efetiva igual à especificada.
     *
     * @param dataDevolucaoEfetiva A data de devolução efetiva a ser pesquisada.
     * @return A especificação JPA para a pesquisa por data de devolução efetiva.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de devolução efetiva igual à especificada.")
    public static Specification<Aluguel> dataDevolucaoEfetivaEquals(LocalDate dataDevolucaoEfetiva) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataDevolucaoEfetiva"), dataDevolucaoEfetiva);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com valor total inicial maior ou igual ao valor especificado.
     *
     * @param valorTotalInicialMin O valor mínimo do valor total inicial a ser considerado na pesquisa.
     * @return A especificação JPA para a pesquisa por valor total inicial mínimo.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com valor total inicial maior ou igual ao valor especificado.")
    public static Specification<Aluguel> valorTotalInicialGreaterThanOrEqual(BigDecimal valorTotalInicialMin) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("valorTotalInicial"), valorTotalInicialMin);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com valor total inicial menor ou igual ao valor especificado.
     *
     * @param valorTotalInicialMax O valor máximo do valor total inicial a ser considerado na pesquisa.
     * @return A especificação JPA para a pesquisa por valor total inicial máximo.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com valor total inicial menor ou igual ao valor especificado.")
    public static Specification<Aluguel> valorTotalInicialLessThanOrEqual(BigDecimal valorTotalInicialMax) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("valorTotalInicial"), valorTotalInicialMax);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com valor total final maior ou igual ao valor especificado.
     *
     * @param valorTotalFinalMin O valor mínimo do valor total final a ser considerado na pesquisa.
     * @return A especificação JPA para a pesquisa por valor total final mínimo.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com valor total final maior ou igual ao valor especificado.")
    public static Specification<Aluguel> valorTotalFinalGreaterThanOrEqual(BigDecimal valorTotalFinalMin) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("valorTotalFinal"), valorTotalFinalMin);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com valor total final menor ou igual ao valor especificado.
     *
     * @param valorTotalFinalMax O valor máximo do valor total final a ser considerado na pesquisa.
     * @return A especificação JPA para a pesquisa por valor total final máximo.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com valor total final menor ou igual ao valor especificado.")
    public static Specification<Aluguel> valorTotalFinalLessThanOrEqual(BigDecimal valorTotalFinalMax) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("valorTotalFinal"), valorTotalFinalMax);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com o status especificado.
     *
     * @param status O status do aluguel a ser pesquisado.
     * @return A especificação JPA para a pesquisa por status.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com o status especificado.")
    public static Specification<Aluguel> statusEquals(StatusAluguel status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("statusAluguel"), status);
    }


    /**
     * Cria uma especificação para pesquisar aluguéis cujo nome do motorista contenha o valor especificado.
     *
     * @param motoristaNome O valor a ser pesquisado no nome do motorista.
     * @return A especificação JPA para a pesquisa por nome do motorista.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cujo nome do motorista contenha o valor especificado.")
    public static Specification<Aluguel> motoristaNomeContains(String motoristaNome) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Motorista> motoristaJoin = root.join("motorista");
            return criteriaBuilder.like(motoristaJoin.get("nome"), "%" + motoristaNome + "%");
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cuja placa do carro seja igual ao valor especificado.
     *
     * @param carroPlaca A placa do carro a ser pesquisada.
     * @return A especificação JPA para a pesquisa por placa do carro.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cuja placa do carro seja igual ao valor especificado.")
    public static Specification<Aluguel> carroPlacaEquals(String carroPlaca) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            return criteriaBuilder.equal(carroJoin.get("placa"), carroPlaca);
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cujo CPF do motorista seja igual ao valor especificado.
     *
     * @param motoristaCpf O CPF do motorista a ser pesquisado.
     * @return A especificação JPA para a pesquisa por CPF do motorista.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cujo CPF do motorista seja igual ao valor especificado.")
    public static Specification<Aluguel> motoristaCpfEquals(String motoristaCpf) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Motorista> motoristaJoin = root.join("motorista");
            return criteriaBuilder.equal(motoristaJoin.get("cpf"), motoristaCpf);
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cuja CNH do motorista seja igual ao valor especificado.
     *
     * @param motoristaCnh A CNH do motorista a ser pesquisada.
     * @return A especificação JPA para a pesquisa por CNH do motorista.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cuja CNH do motorista seja igual ao valor especificado.")
    public static Specification<Aluguel> motoristaCnhEquals(String motoristaCnh) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Motorista> motoristaJoin = root.join("motorista");
            return criteriaBuilder.equal(motoristaJoin.get("numeroCNH"), motoristaCnh);
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cujo nome do carro contenha o valor especificado.
     *
     * @param carroNome O valor a ser pesquisado no nome do carro.
     * @return A especificação JPA para a pesquisa por nome do carro.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cujo nome do carro contenha o valor especificado.")
    public static Specification<Aluguel> carroNomeContains(String carroNome) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            return criteriaBuilder.like(carroJoin.get("nome"), "%" + carroNome + "%");
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cuja cor do carro seja igual ao valor especificado.
     *
     * @param carroCor A cor do carro a ser pesquisada.
     * @return A especificação JPA para a pesquisa por cor do carro.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cuja cor do carro seja igual ao valor especificado.")
    public static Specification<Aluguel> carroCorEquals(String carroCor) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            return criteriaBuilder.equal(carroJoin.get("cor"), carroCor);
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cuja descrição do modelo do carro contenha o valor especificado.
     *
     * @param modeloDescricaoCarro O valor a ser pesquisado na descrição do modelo do carro.
     * @return A especificação JPA para a pesquisa por descrição do modelo do carro.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cuja descrição do modelo do carro contenha o valor especificado.")
    public static Specification<Aluguel> modeloDescricaoCarroContains(String modeloDescricaoCarro) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            Join<Carro, ModeloCarro> modeloJoin = carroJoin.join("modelo");
            return criteriaBuilder.like(modeloJoin.get("descricao"), "%" + modeloDescricaoCarro + "%");
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cujo nome do fabricante do carro contenha o valor especificado.
     *
     * @param fabricanteNomeCarro O valor a ser pesquisado no nome do fabricante do carro.
     * @return A especificação JPA para a pesquisa por nome do fabricante do carro.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cujo nome do fabricante do carro contenha o valor especificado.")
    public static Specification<Aluguel> fabricanteNomeCarroContains(String fabricanteNomeCarro) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            Join<Carro, ModeloCarro> modeloJoin = carroJoin.join("modelo");
            Join<ModeloCarro, Fabricante> fabricanteJoin = modeloJoin.join("fabricante");
            return criteriaBuilder.like(fabricanteJoin.get("nome"), "%" + fabricanteNomeCarro + "%");
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cuja categoria do carro seja igual ao valor especificado.
     *
     * @param categoriaNomeCarro O nome da categoria do carro a ser pesquisada.
     * @return A especificação JPA para a pesquisa por categoria do carro.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cuja categoria do carro seja igual ao valor especificado.")
    public static Specification<Aluguel> categoriaNomeCarroEquals(String categoriaNomeCarro) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            Join<Carro, ModeloCarro> modeloJoin = carroJoin.join("modelo");
            return criteriaBuilder.equal(modeloJoin.get("categoria").as(String.class), categoriaNomeCarro);
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis cujo carro possua pelo menos um dos acessórios especificados.
     *
     * @param acessoriosNomesCarro A lista de nomes de acessórios do carro a serem pesquisados.
     * @return A especificação JPA para a pesquisa por acessórios do carro.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis cujo carro possua pelo menos um dos acessórios especificados.")
    public static Specification<Aluguel> carroTemAcessorios(List<String> acessoriosNomesCarro) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            Join<Carro, Acessorio> acessoriosJoin = carroJoin.join("acessorios");
            return criteriaBuilder.or(acessoriosNomesCarro.stream()
                    .map(nome -> criteriaBuilder.equal(acessoriosJoin.get("nome"), nome))
                    .toArray(Predicate[]::new));
        };
    }
}