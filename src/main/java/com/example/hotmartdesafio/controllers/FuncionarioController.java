package com.example.hotmartdesafio.controllers;

import com.example.hotmartdesafio.dtos.FuncionarioInputDto;
import com.example.hotmartdesafio.dtos.FuncionarioOutputDto;
import com.example.hotmartdesafio.dtos.ProjetoDto;
import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.services.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/funcionarios"})
public class FuncionarioController {
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioService funcionarioService;
    private final EnderecoRepository enderecoRepository;

    public FuncionarioController(FuncionarioRepository funcionarioRepository, FuncionarioService funcionarioService, EnderecoRepository enderecoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioService = funcionarioService;
        this.enderecoRepository = enderecoRepository;
    }

    @GetMapping
    public List<FuncionarioOutputDto> findAll(@RequestParam(name="nome", required = false) String nome) {
        return funcionarioService.getFuncionarios(nome);
    }

    @GetMapping(value="/{id}")
    public FuncionarioOutputDto find(@PathVariable("id") long id) {
        var funcionario = funcionarioRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return new FuncionarioOutputDto(funcionario);
    }

    @PostMapping
    public FuncionarioOutputDto create(@RequestBody @Valid FuncionarioInputDto funcionario) {
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
    public FuncionarioOutputDto update(@PathVariable("id") long id, @RequestBody FuncionarioInputDto funcionarioDto) {
        return funcionarioService.updateFuncionario(id, funcionarioDto);
    }

    @GetMapping("/{id}/projetos")
    public List<ProjetoDto> getProjetos(@PathVariable("id") long id) {
        return funcionarioService.getProjetos(id);
    }

    @GetMapping("/supervisor/{id}")
    public List<FuncionarioOutputDto> getFuncionariosBySupervisor(@PathVariable("id") long id) {
        return funcionarioService.getFuncionariosBySupervisor(id);
    }

    @PostMapping("/{id}/endereco")
    public FuncionarioOutputDto createEndereco(@PathVariable("id") long id, @RequestBody Endereco endereco) {
        var savedEndereco = enderecoRepository.save(endereco);
        var funcionario = funcionarioRepository.findById(id).orElseThrow(NoSuchElementException::new);
        funcionario.setEndereco(savedEndereco);
        return new FuncionarioOutputDto(funcionarioRepository.save(funcionario));
    }
}
