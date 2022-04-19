package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco updateEndereco(long id, Endereco endereco) {
        var currentEndereco = enderecoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentEndereco.setCep(endereco.getCep() != null ? endereco.getCep() : currentEndereco.getCep());
        currentEndereco.setCidade(endereco.getCidade() != null ? endereco.getCidade() : currentEndereco.getCidade());
        currentEndereco.setPais(endereco.getPais() != null ? endereco.getPais() : currentEndereco.getPais());
        currentEndereco.setRua(endereco.getRua() != null ? endereco.getRua() : currentEndereco.getRua());
        currentEndereco.setUf(endereco.getUf() != null ? endereco.getUf() : currentEndereco.getUf());
        return enderecoRepository.save(currentEndereco);
    }
}
