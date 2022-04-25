package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.dtos.ProjetoDto;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProjetoService {
    private ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository repository) {
        projetoRepository = repository;
    }

    public ProjetoDto updateProjeto(long id, Projeto projeto) {
        var currentProjeto = projetoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentProjeto.setNome(projeto.getNome() != null ? projeto.getNome() : currentProjeto.getNome());
        currentProjeto.setCusto(projeto.getNome() != null ? projeto.getCusto() : currentProjeto.getCusto());
        return new ProjetoDto(projetoRepository.save(currentProjeto));
    }
}
