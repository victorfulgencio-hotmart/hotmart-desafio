package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DepartamentoService {
    private DepartamentoRepository departamentoRepository;

    DepartamentoService(DepartamentoRepository enderecoRepository) {
        this.departamentoRepository = enderecoRepository;
    }

    public Departamento updateDepartamento(long id, Departamento endereco) {
        var currentEndereco = departamentoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentEndereco.setNome(endereco.getNome() != null ? endereco.getNome() : currentEndereco.getNome());
        currentEndereco.setNumero(endereco.getNumero() != null ? endereco.getNumero() : currentEndereco.getNumero());
        return departamentoRepository.save(currentEndereco);
    }
}
