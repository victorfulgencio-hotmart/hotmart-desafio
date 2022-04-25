package com.example.hotmartdesafio.dtos;

import com.example.hotmartdesafio.models.Projeto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ProjetoDto {

    public ProjetoDto(Projeto projeto) {
        id = projeto.getId();
        nome = projeto.getNome();
        custo = projeto.getCusto();
    }

    @NotBlank
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private Double custo;
}
