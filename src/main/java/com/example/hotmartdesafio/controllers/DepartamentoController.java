package com.example.hotmartdesafio.controllers;

import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.services.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping({"/departamentos"})
public class DepartamentoController {

    private DepartamentoRepository departamentoRepository;
    private DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoRepository departamentoRepository, DepartamentoService departamentoService) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public List findAll() { return (List)departamentoRepository.findAll(); }

    @GetMapping(value="/{id}")
    public Departamento find(@PathVariable("id") long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @PostMapping
    public Departamento create(@RequestBody Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @PutMapping("/{id}")
    public Departamento update(@PathVariable("id") long id, @RequestBody Departamento departamento) {
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
}
