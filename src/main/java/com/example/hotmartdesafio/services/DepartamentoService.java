package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DepartamentoService {
    private DepartamentoRepository departamentoRepository;
    private ProjetoRepository projetoRepository;

    DepartamentoService(DepartamentoRepository enderecoRepository, ProjetoRepository projetoRepository) {
        this.departamentoRepository = enderecoRepository;
        this.projetoRepository = projetoRepository;
    }

    public Departamento updateDepartamento(long id, Departamento departamento) {
        var currentDepartamento = departamentoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentDepartamento.setNome(departamento.getNome() != null ? departamento.getNome() : currentDepartamento.getNome());
        currentDepartamento.setNumero(departamento.getNumero() != null ? departamento.getNumero() : currentDepartamento.getNumero());
        return departamentoRepository.save(currentDepartamento);
    }

    public Projeto addProjeto(long id, Projeto projeto) {
        var currentDepartamento = departamentoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentDepartamento.getProjetos().add(projeto);
        return projetoRepository.save(projeto);
    }
}
