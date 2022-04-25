package com.example.hotmartdesafio.controllers;

import com.example.hotmartdesafio.dtos.DepartamentoDto;
import com.example.hotmartdesafio.dtos.DepartamentoStatusDto;
import com.example.hotmartdesafio.dtos.ProjetoDto;
import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.services.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/departamentos"})
public class DepartamentoController {

    private final DepartamentoRepository departamentoRepository;
    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoRepository departamentoRepository, DepartamentoService departamentoService) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public List<DepartamentoDto> findAll() {
        return ((List<Departamento>) departamentoRepository.findAll())
                .stream().map(DepartamentoDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value="/{id}")
    public DepartamentoDto find(@PathVariable("id") long id) {
        return new DepartamentoDto(departamentoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @PostMapping
    public DepartamentoDto create(@RequestBody Departamento departamento) {
        return new DepartamentoDto(departamentoRepository.save(departamento));
    }

    @PutMapping("/{id}")
    public DepartamentoDto update(@PathVariable("id") long id, @RequestBody Departamento departamento) {
        return departamentoService.updateDepartamento(id, departamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        return departamentoRepository.findById(id)
                .map(record -> {
                    departamentoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/projetos")
    public ProjetoDto addProjeto(@PathVariable("id") long departamentoId, @RequestBody Projeto projeto) {
        return departamentoService.addProjeto(departamentoId, projeto);
    }

    /*
    * Atividade 2: Funcionario e Departamento nao estao associadas no diagrama UML
    * entao vou considerar pegar os funcionarios cujos projetos estao associados aquele departamento
    */
    @GetMapping("/{id}/funcionarios")
    public List getFuncionariosFromDepartamento(@PathVariable("id") long departamentoId) {
        return departamentoService.getFuncionariosFromDepartamento(departamentoId);
    }

    @GetMapping("/{id}/status")
    public DepartamentoStatusDto getStatus(@PathVariable("id") long id) {
        return departamentoService.getStatus(id);
    }
}
