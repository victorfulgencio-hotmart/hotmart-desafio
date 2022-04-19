package com.example.hotmartdesafio.controllers;

import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.services.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping({"/enderecos"})
public class EnderecoController {

    private EnderecoRepository enderecoRepository;
    private EnderecoService enderecoService;

    EnderecoController(EnderecoRepository enderecoRepository, EnderecoService enderecoService) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public List findAllEnderecos() { return (List)enderecoRepository.findAll(); }

    @GetMapping(value="/{id}")
    public Endereco findEndereco(@PathVariable("id") long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @PutMapping(value="/{id}")
    public Endereco updateEndereco(@PathVariable("id") long id, @RequestBody Endereco endereco) {
        return enderecoService.updateEndereco(id, endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEndereco(@PathVariable("id") long id) {
        return enderecoRepository.findById(id)
                .map(record -> {
                    enderecoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
