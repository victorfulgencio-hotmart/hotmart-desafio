package com.example.hotmartdesafio.controllers;

import com.example.hotmartdesafio.dtos.ProjetoDto;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import com.example.hotmartdesafio.services.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public List<ProjetoDto> findAl() {
        var result = projetoRepository.findAll();
        return ((List<Projeto>) result).stream().map(ProjetoDto::new).collect(Collectors.toList());
    }

    @GetMapping(value="/{id}")
    public ProjetoDto find(@PathVariable("id") long id) {
        var result = projetoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return new ProjetoDto(result);
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
    public ProjetoDto update(@PathVariable("id") long id, @RequestBody @Valid Projeto projeto) {
        return projetoService.updateProjeto(id, projeto);
    }
}
