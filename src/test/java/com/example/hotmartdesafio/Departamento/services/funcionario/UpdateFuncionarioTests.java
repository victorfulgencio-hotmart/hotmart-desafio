package com.example.hotmartdesafio.Departamento.services.funcionario;

import com.example.hotmartdesafio.dtos.FuncionarioInputDto;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.services.FuncionarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@TestComponent
public class UpdateFuncionarioTests {
    private static FuncionarioService funcionarioService;

    private static final FuncionarioRepository funcionarioRepository = mock(FuncionarioRepository.class);

    @BeforeAll
    public static void setup() {
        funcionarioService = new FuncionarioService(funcionarioRepository, null, null);
    }

    @Test
    void changeFuncionarioHappyPath() {
        // Arrange
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.of(new Funcionario()));
        doAnswer(returnsFirstArg()).when(funcionarioRepository).save(Mockito.any(Funcionario.class));

        var payload = getSamplePayload();

        // Act
        var result = funcionarioService.updateFuncionario(anyLong(), payload);

        // Assert
        Assert.isTrue(result != null, "Endereco atualizado nao é nulo");
        Assert.isTrue(Objects.equals(result.getSalario(), payload.getSalario()), "Salario é igual ao payload");
        Assert.isTrue(Objects.equals(result.getNome(), payload.getNome()), "Nome é igual ao payload");
        Assert.isTrue(Objects.equals(result.getDataNascimento(), payload.getDataNascimento()), "Nome é igual ao payload");
        Assert.isTrue(Objects.equals(result.getSexo(), payload.getSexo()), "Nome é igual ao payload");
    }

    FuncionarioInputDto getSamplePayload() {
        var payload = new FuncionarioInputDto();
        payload.setSexo("F");
        payload.setDataNascimento(new Date());
        payload.setCpf("123.355.666.12");
        payload.setSalario(1000.0);

        return payload;
    }
}
