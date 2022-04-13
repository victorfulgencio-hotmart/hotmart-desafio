package com.example.hotmartdesafio.controllers;

import com.example.hotmartdesafio.dtos.FuncionarioDto;
import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.services.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping({"/funcionarios"})
public class FuncionarioController {
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioRepository funcionarioRepository, FuncionarioService funcionarioService) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public List findAll(@RequestParam(name="nome", required = false) String nome) {
        return funcionarioService.getFuncionarios(nome);
    }

    @GetMapping(value="/{id}")
    public Funcionario find(@PathVariable("id") long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @PostMapping
    public Funcionario create(@RequestBody FuncionarioDto funcionario) {
        return funcionarioService.addFuncionario(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        return funcionarioRepository.findById(id)
                .map(record -> {
                    funcionarioRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Funcionario update(@PathVariable("id") long id, @RequestBody FuncionarioDto funcionarioDto) {
        return funcionarioService.updateFuncionario(id, funcionarioDto);
    }

    @GetMapping("/{id}/projetos")
    public List getProjetos(@PathVariable("id") long id) {
        return funcionarioService.getProjetos(id);
    }
}
