package com.example.hotmartdesafio.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEndereco;

    @NotNull
    @NotEmpty
    private String pais;

    @NotNull
    @NotEmpty
    private String cidade;

    @NotEmpty
    @NotNull
    private String uf;

    @NotEmpty
    @NotNull
    private String rua;

    @NotEmpty
    @NotNull
    private String cep;
}
