package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.carro;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.acessorios.DadosListagemAcessorios;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.CarrinhoAluguel;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Carro;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.util.stream.Collectors.toList;

@Schema(description = "Dados detalhados de um carro, incluindo histórico de aluguéis.")
public record DadosDetalhamentoCarro(

        @Schema(description = "ID do carro.")
        Long id,

        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @Schema(description = "Placa do carro.")
        String placa,

        @Schema(description = "Chassi do carro.")
        String chassi,

        @Schema(description = "Cor do carro.")
        String cor,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiaria,

        @Schema(description = "Indica se o carro está disponível para aluguel.")
        boolean disponivel,

        @Schema(description = "Nome do modelo do carro.")
        String modeloCarro,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de cadastro do carro.")
        LocalDateTime dataCadastro,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de atualização do carro.")
        LocalDateTime dataAtualizacao,

        @Schema(description = "Lista de nomes dos acessórios do carro.")
        List<String> acessorios,

        @Schema(description = "Lista de aluguéis realizados com este carro.")
        List<DadosListagemAluguelDeCarro> alugueis
) {
    public DadosDetalhamentoCarro(Carro carro) {
        this(
                carro.getId(),
                carro.getNome(),
                carro.getPlaca(),
                carro.getChassi(),
                carro.getCor(),
                carro.getValorDiaria(),
                carro.isDisponivel(),
                carro.getModelo().getDescricao(),
                carro.getDataCreated(),
                carro.getLastUpdated(),
                carro.getAcessorios()
                        .stream()
                        .map(DadosListagemAcessorios::new)
                        .map(DadosListagemAcessorios::nome)
                        .collect(toList()),
                carro.getCarrinhosAluguel().stream()
                        .map(CarrinhoAluguel::getAluguel) // Obtém o aluguel de cada carrinho
                        .filter(Objects::nonNull) // Filtra aluguéis não nulos (opcional)
                        .map(DadosListagemAluguelDeCarro::new) // Mapeia para DadosListagemAluguelDeCarro
                        .collect(toList())
        );
    }
}

