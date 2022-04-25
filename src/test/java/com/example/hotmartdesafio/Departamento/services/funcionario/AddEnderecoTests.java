package com.example.hotmartdesafio.Departamento.services.funcionario;

import com.example.hotmartdesafio.dtos.FuncionarioInputDto;
import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.services.FuncionarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestComponent
public class AddEnderecoTests {
    private static FuncionarioService funcionarioService;

    private static final EnderecoRepository enderecoRepository = mock(EnderecoRepository.class);

    @BeforeAll
    public static void setup() {
        funcionarioService = new FuncionarioService(null, null, enderecoRepository);
    }

    @Test
    void addEnderecoHappyPath() {
        // Arrange
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(new Endereco()));
        var funcionario = new Funcionario();
        var payload = getSamplePayload();

        // Act
        funcionarioService.addEndereco(payload, funcionario);

        // Assert
        Assert.isTrue(funcionario.getEndereco() != null, "Endereco foi adicionado");
    }

    @Test
    void addEnderecoWithoutAValidPayload_ExpectError() {
        // Arrange
        var funcionario = new Funcionario();
        var payload = new FuncionarioInputDto(); // without an Endereco

        // Act
        funcionarioService.addEndereco(payload, funcionario);

        // Assert
        Assert.isNull(funcionario.getEndereco(), "Endereco é nulo, pois o payload não contém um Endereco válido");
    }

    FuncionarioInputDto getSamplePayload() {
        var payload = new FuncionarioInputDto();
        payload.setEndereco(Mockito.anyLong());
        return payload;
    }
}
