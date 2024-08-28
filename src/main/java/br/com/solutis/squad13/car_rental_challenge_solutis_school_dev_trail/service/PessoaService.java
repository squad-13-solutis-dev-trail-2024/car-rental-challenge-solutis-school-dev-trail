package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    List<Pessoa> buscarTodasPessoas();
    Optional<Pessoa> buscarPessoaPorId(Long id);
    Pessoa criarPessoa(Pessoa pessoa);
    Pessoa atualizarPessoa(Long id, Pessoa pessoaAtualizada);
    void deletarPessoa(Long id);
}
