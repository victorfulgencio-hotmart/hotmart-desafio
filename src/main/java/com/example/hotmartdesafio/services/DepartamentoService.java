package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepartamentoService {
    private DepartamentoRepository departamentoRepository;
    private ProjetoRepository projetoRepository;
    private FuncionarioRepository funcionarioRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository, ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
        this.departamentoRepository = departamentoRepository;
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
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

    public List<Funcionario> getFuncionariosFromDepartamento(Long departamentoId) {
        var departamento = departamentoRepository.findById(departamentoId).orElseThrow(NoSuchElementException::new);

        List<Funcionario> allFuncionarios = new ArrayList();
        var projetos = departamento.getProjetos();
        if(projetos == null) return null;

        for (Projeto proj: projetos) {
            var funcionarios = funcionarioRepository.findFuncionariosByProjetoId(proj.getId());
            allFuncionarios.addAll(funcionarios);
        }

        return allFuncionarios;
    }
}
