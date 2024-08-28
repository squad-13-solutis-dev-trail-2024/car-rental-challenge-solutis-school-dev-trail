package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.impl;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.PessoaService;
import org.springframework.stereotype.Service;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Pessoa;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.repository.PessoaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public List<Pessoa> buscarTodasPessoas() {
        return pessoaRepository.findAll();
    }

    @Override
    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    @Override
    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa atualizarPessoa(Long id, Pessoa pessoaAtualizada) {
        return pessoaRepository.findById(id)
                .map(pessoaExistente -> {
                    pessoaExistente.setNome(pessoaAtualizada.getNome());
                    pessoaExistente.setEmail(pessoaAtualizada.getEmail());
                    pessoaExistente.setDataNascimento(pessoaAtualizada.getDataNascimento());
                    pessoaExistente.setCpf(pessoaAtualizada.getCpf());
                    pessoaExistente.setSexo(pessoaAtualizada.getSexo());
                    return pessoaRepository.save(pessoaExistente);
                })
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    @Override
    public void deletarPessoa(Long id) {
        pessoaRepository.deleteById(id);
    }
}