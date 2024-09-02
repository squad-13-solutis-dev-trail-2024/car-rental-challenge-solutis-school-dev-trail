package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.spec;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Acessorio;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Fabricante;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.ModeloCarro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe utilitária para criar especificações JPA para a entidade {@link Carro}.
 * <p>
 * Esta classe fornece métodos estáticos que retornam objetos {@link Specification}
 * para construir consultas dinâmicas para a entidade {@link Carro},
 * permitindo a pesquisa de carros com base em diferentes critérios.
 * </p>
 */
@Schema(description = "Classe utilitária para criar especificações JPA para a entidade Carro.")
public class CarroSpecs {

    /**
     * Cria uma especificação para pesquisar carros cujo nome contenha o valor especificado.
     *
     * @param nome O valor a ser pesquisado no nome do carro.
     * @return A especificação JPA para a pesquisa por nome.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros cujo nome contenha o valor especificado.")
    public static Specification<Carro> nomeContains(String nome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    }

    /**
     * Cria uma especificação para pesquisar carros com a placa especificada.
     *
     * @param placa A placa do carro a ser pesquisada.
     * @return A especificação JPA para a pesquisa por placa.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros com a placa especificada.")
    public static Specification<Carro> placaEquals(String placa) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("placa"), placa);
    }

    /**
     * Cria uma especificação para pesquisar carros com o chassi especificado.
     *
     * @param chassi O chassi do carro a ser pesquisado.
     * @return A especificação JPA para a pesquisa por chassi.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros com o chassi especificado.")
    public static Specification<Carro> chassiEquals(String chassi) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("chassi"), chassi);
    }

    /**
     * Cria uma especificação para pesquisar carros com a cor especificada.
     *
     * @param cor A cor do carro a ser pesquisada.
     * @return A especificação JPA para a pesquisa por cor.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros com a cor especificada.")
    public static Specification<Carro> corEquals(String cor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cor"), cor);
    }

    /**
     * Cria uma especificação para pesquisar carros com a disponibilidade especificada.
     *
     * @param disponivel A disponibilidade do carro a ser pesquisada (true para disponível, false para indisponível).
     * @return A especificação JPA para a pesquisa por disponibilidade.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros com a disponibilidade especificada.")
    public static Specification<Carro> disponivelEquals(Boolean disponivel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("disponivel"), disponivel);
    }

    /**
     * Cria uma especificação para pesquisar carros com valor da diária maior ou igual ao valor especificado.
     *
     * @param valorDiariaMin O valor mínimo da diária a ser considerado na pesquisa.
     * @return A especificação JPA para a pesquisa por valor da diária mínimo.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros com valor da diária maior ou igual ao valor especificado.")
    public static Specification<Carro> valorDiariaGreaterThanOrEqual(BigDecimal valorDiariaMin) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("valorDiaria"), valorDiariaMin);
    }

    /**
     * Cria uma especificação para pesquisar carros com valor da diária menor ou igual ao valor especificado.
     *
     * @param valorDiariaMax O valor máximo da diária a ser considerado na pesquisa.
     * @return A especificação JPA para a pesquisa por valor da diária máximo.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros com valor da diária menor ou igual ao valor especificado.")
    public static Specification<Carro> valorDiariaLessThanOrEqual(BigDecimal valorDiariaMax) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("valorDiaria"), valorDiariaMax);
    }

    /**
     * Cria uma especificação para pesquisar carros cuja descrição do modelo contenha o valor especificado.
     *
     * @param modeloDescricao O valor a ser pesquisado na descrição do modelo do carro.
     * @return A especificação JPA para a pesquisa por descrição do modelo.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros cuja descrição do modelo contenha o valor especificado.")
    public static Specification<Carro> modeloDescricaoContains(String modeloDescricao) {
        return (root, query, criteriaBuilder) -> {
            Join<Carro, ModeloCarro> modeloJoin = root.join("modelo");
            return criteriaBuilder.like(modeloJoin.get("descricao"), "%" + modeloDescricao + "%");
        };
    }

    /**
     * Cria uma especificação para pesquisar carros cujo nome do fabricante contenha o valor especificado.
     *
     * @param fabricanteNome O valor a ser pesquisado no nome do fabricante do carro.
     * @return A especificação JPA para a pesquisa por nome do fabricante.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros cujo nome do fabricante contenha o valor especificado.")
    public static Specification<Carro> fabricanteNomeContains(String fabricanteNome) {
        return (root, query, criteriaBuilder) -> {
            Join<Carro, ModeloCarro> modeloJoin = root.join("modelo");
            Join<ModeloCarro, Fabricante> fabricanteJoin = modeloJoin.join("fabricante");
            return criteriaBuilder.like(fabricanteJoin.get("nome"), "%" + fabricanteNome + "%");
        };
    }

    /**
     * Cria uma especificação para pesquisar carros cuja categoria seja igual ao valor especificado.
     *
     * @param categoriaNome O nome da categoria a ser pesquisada.
     * @return A especificação JPA para a pesquisa por categoria.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros cuja categoria seja igual ao valor especificado.")
    public static Specification<Carro> categoriaNomeEquals(String categoriaNome) {
        return (root, query, criteriaBuilder) -> {
            Join<Carro, ModeloCarro> modeloJoin = root.join("modelo");
            return criteriaBuilder.equal(modeloJoin.get("categoria").as(String.class), categoriaNome);
        };
    }

    /**
     * Cria uma especificação para pesquisar carros que possuam pelo menos um dos acessórios especificados.
     *
     * @param acessoriosNomes A lista de nomes de acessórios a serem pesquisados.
     * @return A especificação JPA para a pesquisa por acessórios.
     */
    @Schema(description = "Cria uma especificação para pesquisar carros que possuam pelo menos um dos acessórios especificados.")
    public static Specification<Carro> temAcessorios(List<String> acessoriosNomes) {
        return (root, query, criteriaBuilder) -> {
            Join<Carro, Acessorio> acessoriosJoin = root.join("acessorios");
            return criteriaBuilder.or(acessoriosNomes.stream()
                    .map(nome -> criteriaBuilder.equal(acessoriosJoin.get("nome"), nome))
                    .toArray(Predicate[]::new));
        };
    }
}