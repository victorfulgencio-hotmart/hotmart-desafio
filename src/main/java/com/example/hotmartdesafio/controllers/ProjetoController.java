package com.example.hotmartdesafio.controllers;

import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import com.example.hotmartdesafio.services.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping({"/projetos"})
public class ProjetoController {
    private ProjetoRepository projetoRepository;
    private ProjetoService projetoService;

    ProjetoController(ProjetoRepository projetoRepository, ProjetoService projetoService) {
        this.projetoRepository = projetoRepository;
        this.projetoService = projetoService;
    }

    @GetMapping
    public List findAl() { return (List)projetoRepository.findAll(); }

    @GetMapping(value="/{id}")
    public Projeto find(@PathVariable("id") long id) {
        return projetoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        return projetoRepository.findById(id)
                .map(record -> {
                    projetoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Projeto update(@PathVariable("id") long id, @RequestBody Projeto projeto) {
        return projetoService.updateDepartamento(id, projeto);
    }
}
