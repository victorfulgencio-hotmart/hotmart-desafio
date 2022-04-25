package com.example.hotmartdesafio.dtos;

import com.example.hotmartdesafio.models.Departamento;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class DepartamentoDto {

    public DepartamentoDto(Departamento departamento) {
        nome = departamento.getNome();
        numero = departamento.getNumero();
        id = departamento.getId();
        if(departamento.getProjetos() != null)
            projetos = departamento.getProjetos()
                    .stream()
                    .map(ProjetoDto::new)
                    .collect(Collectors.toSet());
    }

    private Long id;

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    private Integer numero;

    private Set<ProjetoDto> projetos;
}
