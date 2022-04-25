package com.example.hotmartdesafio.models;

import com.example.hotmartdesafio.dtos.FuncionarioInputDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Funcionario {

    public Funcionario(FuncionarioInputDto funcionarioDto) {
        this.cpf = funcionarioDto.getCpf();
        this.dataNascimento = funcionarioDto.getDataNascimento();
        this.sexo = funcionarioDto.getSexo();
        this.nome = funcionarioDto.getNome();
        this.salario = funcionarioDto.getSalario();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String nome;
    @NotEmpty
    @NotNull
    private String cpf;
    private String sexo;
    private Date dataNascimento;
    private Double salario;

    @ManyToOne()
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(
            name = "funcionario_projetos",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "projeto_id"))
    private Set<Projeto> projetos;

    @ManyToOne
    private Funcionario supervisor;
}
