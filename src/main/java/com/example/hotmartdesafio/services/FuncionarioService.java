package com.example.hotmartdesafio.services;

import com.example.hotmartdesafio.dtos.FuncionarioDto;
import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario addFuncionario(FuncionarioDto funcionarioDto) {
        var funcionario = new Funcionario(funcionarioDto);
        addSupervisor(funcionarioDto, funcionario);
        return funcionarioRepository.save(funcionario);
    }

    public void addSupervisor(FuncionarioDto funcionarioDto, Funcionario funcionario) {
        if(funcionarioDto.getSupervisor() != null) {
            var supervisor= funcionarioRepository.findById(funcionarioDto.getSupervisor()).orElseThrow(NoSuchElementException::new);
            funcionario.setSupervisor(supervisor);
        }
    }

    public Funcionario updateFuncionario(long id, FuncionarioDto funcionarioDto) {
        var current = funcionarioRepository.findById(id).orElseThrow(NoSuchElementException::new);
        current.setNome(funcionarioDto.getNome() != null ? funcionarioDto.getNome() : current.getNome());
        current.setCpf(funcionarioDto.getCpf() != null ? funcionarioDto.getCpf() : current.getCpf());
        current.setDataNascimento(funcionarioDto.getDataNascimento() != null ? funcionarioDto.getDataNascimento() : current.getDataNascimento());
        current.setSexo(funcionarioDto.getSexo() != null ? funcionarioDto.getSexo() : current.getSexo());
        addSupervisor(funcionarioDto, current);
        return funcionarioRepository.save(current);
    }
}
