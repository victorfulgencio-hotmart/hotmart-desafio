package com.example.hotmartdesafio.models;

import com.example.hotmartdesafio.dtos.FuncionarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Funcionario {

    public Funcionario(FuncionarioDto funcionarioDto) {
        this.cpf = funcionarioDto.getCpf();
        this.dataNascimento = funcionarioDto.getDataNascimento();
        this.sexo = funcionarioDto.getSexo();
        this.nome = funcionarioDto.getNome();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String sexo;
    private Date dataNascimento;

    @ManyToOne()
    @JoinColumn(name = "idEndereco", insertable = false, updatable = false)
    private Endereco endereco;

    @ManyToMany
    private Set<Projeto> projetos;

    @ManyToOne
    private Funcionario supervisor;
}
