package com.example.hotmartdesafio.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class FuncionarioDto {
    private String nome;
    private String cpf;
    private String sexo;
    private Date dataNascimento;
    private Long supervisor;
}
