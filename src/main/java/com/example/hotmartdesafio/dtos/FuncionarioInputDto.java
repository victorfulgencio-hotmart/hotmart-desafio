package com.example.hotmartdesafio.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class FuncionarioInputDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    @NotBlank
    private String sexo;
    private Double salario;
    private Date dataNascimento;
    private Long supervisor;
    private Long endereco;
}
