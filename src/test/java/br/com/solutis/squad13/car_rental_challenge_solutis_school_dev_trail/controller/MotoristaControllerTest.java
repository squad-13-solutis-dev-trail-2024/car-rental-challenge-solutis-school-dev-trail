package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.MotoristaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.enums.Sexo.MASCULINO;
import static java.time.LocalDateTime.now;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ActiveProfiles("MotoristaControllerTest")
@SpringBootTest // Use @SpringBootTest em vez de @WebMvcTest
@AutoConfigureMockMvc // Adicione @AutoConfigureMockMvc
public class MotoristaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotoristaService motoristaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenMotorista_whenPostMotorista_thenCreateMotorista() throws Exception {

        //todo: implementar teste
//
//        // given
//        DadosCadastroMotorista dadosCadastroMotorista = new DadosCadastroMotorista(
//                "João Silva",
//                LocalDate.of(1985, 5, 20),
//                "938.394.390-43",
//                "viniciusdsandrade0662@gmail.com",
//                "24520236820",
//                MASCULINO,
//                now(),
//                now()
//        );
//
//        Motorista motorista = new Motorista();
//        motorista.setNome(dadosCadastroMotorista.nome());
//        motorista.setDataNascimento(dadosCadastroMotorista.dataNascimento());
//        motorista.setCpf(dadosCadastroMotorista.cpf());
//        motorista.setEmail(dadosCadastroMotorista.email());
//        motorista.setNumeroCNH(dadosCadastroMotorista.numeroCNH());
//        motorista.setSexo(dadosCadastroMotorista.sexo());
//        boolean ativo = true;
//
//        BDDMockito.given(motoristaService.cadastrarMotorista(dadosCadastroMotorista)).willReturn(motorista);
//
//        // when
//        ResultActions response = mockMvc.perform(post("/api/v1/cliente")
//                .contentType(APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(dadosCadastroMotorista)));
//
//        // then
//        response.andExpect(status().isCreated())
//                .andExpect(jsonPath("$.nome").value(dadosCadastroMotorista.nome()))
//                .andExpect(jsonPath("$.dataNascimento").value(dadosCadastroMotorista.dataNascimento().toString()))
//                .andExpect(jsonPath("$.cpf").value(dadosCadastroMotorista.cpf()))
//                .andExpect(jsonPath("$.email").value(dadosCadastroMotorista.email()))
//                .andExpect(jsonPath("$.numeroCNH").value(dadosCadastroMotorista.numeroCNH()))
//                .andExpect(jsonPath("$.sexo").value(dadosCadastroMotorista.sexo().toString()))
//                .andExpect(jsonPath("$.ativo").value(true));
    }
}
