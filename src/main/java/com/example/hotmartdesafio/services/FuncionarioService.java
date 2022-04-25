package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.dtos.FuncionarioInputDto;
import com.example.hotmartdesafio.dtos.FuncionarioOutputDto;
import com.example.hotmartdesafio.dtos.ProjetoDto;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ProjetoRepository projetoRepository;
    private final EnderecoRepository enderecoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, ProjetoRepository projetoRepository, EnderecoRepository enderecoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.projetoRepository = projetoRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public FuncionarioOutputDto addFuncionario(FuncionarioInputDto funcionarioDto) {
        var funcionario = new Funcionario(funcionarioDto);
        addSupervisor(funcionarioDto, funcionario);
        addEndereco(funcionarioDto, funcionario);
        var result = funcionarioRepository.save(funcionario);
        return new FuncionarioOutputDto(result);
    }

    public void addSupervisor(FuncionarioInputDto funcionarioDto, Funcionario funcionario) {
        if(funcionarioDto.getSupervisor() != null) {
            var supervisor= funcionarioRepository.findById(funcionarioDto.getSupervisor()).orElseThrow(NoSuchElementException::new);
            funcionario.setSupervisor(supervisor);
        }
    }

    public void addEndereco(FuncionarioInputDto funcionarioDto, Funcionario funcionario) {
        if(funcionarioDto.getEndereco() != null) {
            var endereco= enderecoRepository.findById(funcionarioDto.getEndereco()).orElseThrow(NoSuchElementException::new);
            funcionario.setEndereco(endereco);
        }
    }

    public FuncionarioOutputDto updateFuncionario(Long id, FuncionarioInputDto funcionarioDto) {
        var current = funcionarioRepository.findById(id).orElseThrow(NoSuchElementException::new);
        current.setNome(funcionarioDto.getNome() != null ? funcionarioDto.getNome() : current.getNome());
        current.setCpf(funcionarioDto.getCpf() != null ? funcionarioDto.getCpf() : current.getCpf());
        current.setSalario(funcionarioDto.getSalario() != null ? funcionarioDto.getSalario() : current.getSalario());
        current.setDataNascimento(funcionarioDto.getDataNascimento() != null ? funcionarioDto.getDataNascimento() : current.getDataNascimento());
        current.setSexo(funcionarioDto.getSexo() != null ? funcionarioDto.getSexo() : current.getSexo());
        addSupervisor(funcionarioDto, current);
        addEndereco(funcionarioDto, current);
        var result = funcionarioRepository.save(current);
        return new FuncionarioOutputDto(result);
    }

    public List<FuncionarioOutputDto> getFuncionarios(String nome) {
        List<Funcionario> result;
        if(nome == null || nome.isEmpty())
            result = (List)funcionarioRepository.findAll();
        else
            result = funcionarioRepository.findByNome(nome);
        return result.stream().map(FuncionarioOutputDto::new).collect(Collectors.toList());
    }

    public List<ProjetoDto> getProjetos(Long id) {
        return projetoRepository.findProjetosByFuncionarioId(id)
                .stream().map(ProjetoDto::new)
                .collect(Collectors.toList());
    }

    public List<FuncionarioOutputDto> getFuncionariosBySupervisor(Long id) {
        return funcionarioRepository.findFuncionariosBySupervisor(id)
                .stream()
                .map(FuncionarioOutputDto::new)
                .collect(Collectors.toList());
    }
}
