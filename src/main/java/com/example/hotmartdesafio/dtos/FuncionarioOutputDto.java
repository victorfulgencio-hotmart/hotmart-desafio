package com.example.hotmartdesafio.dtos;

import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.models.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class FuncionarioOutputDto {

    public FuncionarioOutputDto(Funcionario funcionario) {
        id = funcionario.getId();
        nome = funcionario.getNome();
        cpf = funcionario.getCpf();
        sexo = funcionario.getSexo();
        dataNascimento = funcionario.getDataNascimento();
        salario = funcionario.getSalario();
        endereco = funcionario.getEndereco();
        projetos = funcionario.getProjetos() != null ?
                funcionario.getProjetos().stream().map(ProjetoDto::new).collect(Collectors.toSet()) :
                null;

        if(funcionario.getSupervisor() != null)
            setSupervisor(funcionario.getSupervisor());
    }

    public void setSupervisor(Funcionario supervisor) {
        this.supervisor = new FuncionarioOutputDto(supervisor);
    }

    @NotBlank
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    private String sexo;
    private Date dataNascimento;
    private Double salario;
    private Endereco endereco;
    private Set projetos;
    private FuncionarioOutputDto supervisor;
}
