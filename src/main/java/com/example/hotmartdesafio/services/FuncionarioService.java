package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.dtos.FuncionarioDto;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Funcionario addFuncionario(FuncionarioDto funcionarioDto) {
        var funcionario = new Funcionario(funcionarioDto);
        addSupervisor(funcionarioDto, funcionario);
        addEndereco(funcionarioDto, funcionario);
        return funcionarioRepository.save(funcionario);
    }

    public void addSupervisor(FuncionarioDto funcionarioDto, Funcionario funcionario) {
        if(funcionarioDto.getSupervisor() != null) {
            var supervisor= funcionarioRepository.findById(funcionarioDto.getSupervisor()).orElseThrow(NoSuchElementException::new);
            funcionario.setSupervisor(supervisor);
        }
    }

    public void addEndereco(FuncionarioDto funcionarioDto, Funcionario funcionario) {
        if(funcionarioDto.getEndereco() != null) {
            var endereco= enderecoRepository.findById(funcionarioDto.getEndereco()).orElseThrow(NoSuchElementException::new);
            funcionario.setEndereco(endereco);
        }
    }

    public Funcionario updateFuncionario(Long id, FuncionarioDto funcionarioDto) {
        var current = funcionarioRepository.findById(id).orElseThrow(NoSuchElementException::new);
        current.setNome(funcionarioDto.getNome() != null ? funcionarioDto.getNome() : current.getNome());
        current.setCpf(funcionarioDto.getCpf() != null ? funcionarioDto.getCpf() : current.getCpf());
        current.setSalario(funcionarioDto.getSalario() != null ? funcionarioDto.getSalario() : current.getSalario());
        current.setDataNascimento(funcionarioDto.getDataNascimento() != null ? funcionarioDto.getDataNascimento() : current.getDataNascimento());
        current.setSexo(funcionarioDto.getSexo() != null ? funcionarioDto.getSexo() : current.getSexo());
        addSupervisor(funcionarioDto, current);
        addEndereco(funcionarioDto, current);
        return funcionarioRepository.save(current);
    }

    public List<Funcionario> getFuncionarios(String nome) {
        if(nome == null || nome.isEmpty())
            return (List)funcionarioRepository.findAll();
        else
            return funcionarioRepository.findByNome(String.valueOf(nome));
    }

    public List<Projeto> getProjetos(Long id) {
        return projetoRepository.findProjetosByFuncionarioId(id);
    }

    public List getFuncionariosBySupervisor(Long id) {
        return funcionarioRepository.findFuncionariosBySupervisor(id);
    }
}
