package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.dtos.*;
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
import java.util.Objects;
import java.util.stream.Collectors;

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

    public DepartamentoDto updateDepartamento(Long id, Departamento departamento) {
        var currentDepartamento = departamentoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentDepartamento.setNome(departamento.getNome() != null ? departamento.getNome() : currentDepartamento.getNome());
        currentDepartamento.setNumero(departamento.getNumero() != null ? departamento.getNumero() : currentDepartamento.getNumero());
        return new DepartamentoDto(departamentoRepository.save(currentDepartamento));
    }

    public ProjetoDto addProjeto(long id, Projeto projeto) {
        var currentDepartamento = departamentoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        currentDepartamento.getProjetos().add(projeto);
        return new ProjetoDto(projetoRepository.save(projeto));
    }

    public List<FuncionarioOutputDto> getFuncionariosFromDepartamento(Long departamentoId) {
        var departamento = departamentoRepository.findById(departamentoId).orElseThrow(NoSuchElementException::new);

        List<Funcionario> allFuncionarios = new ArrayList();
        var projetos = departamento.getProjetos();
        if(projetos == null) return null;

        for (Projeto proj: projetos) {
            var funcionarios = funcionarioRepository.findFuncionariosByProjetoId(proj.getId());
            allFuncionarios.addAll(funcionarios);
        }

        return allFuncionarios.stream().map(FuncionarioOutputDto::new).collect(Collectors.toList());
    }

    public DepartamentoStatusDto getStatus(Long id) {
        var departamento = departamentoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        var projetos = departamento.getProjetos();
        if(projetos == null)
            return new DepartamentoStatusDto(StatusEnum.VERDE);

        double totalCost = 0;
        for (Projeto proj: projetos)
            totalCost += proj.getCusto() == null ? 0 : proj.getCusto();

        var funcionariosFromDepartamento = getFuncionariosFromDepartamento(id);
        if(funcionariosFromDepartamento != null) {
            funcionariosFromDepartamento.removeIf(Objects::isNull);
            for (FuncionarioOutputDto func: funcionariosFromDepartamento)
                totalCost += func.getSalario() == null ? 0 : func.getSalario();
        }

        return new DepartamentoStatusDto(totalCost, departamento.getNumero());
    }
}
