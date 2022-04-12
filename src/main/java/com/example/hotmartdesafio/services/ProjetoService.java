package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProjetoService {
    private ProjetoRepository projetoRepository;

    ProjetoService(ProjetoRepository repository) {
        projetoRepository = repository;
    }

    public Projeto updateDepartamento(long id, Projeto projeto) {
        var currentProjeto = projetoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentProjeto.setNome(projeto.getNome() != null ? projeto.getNome() : currentProjeto.getNome());
        return projetoRepository.save(currentProjeto);
    }
}
