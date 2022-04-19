package com.example.hotmartdesafio.Departamento.services.funcionario;

import com.example.hotmartdesafio.dtos.FuncionarioDto;
import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.services.FuncionarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.Assert;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestComponent
public class AddSupervisorTests {
    private static FuncionarioService funcionarioService;

    private static final FuncionarioRepository funcionarioRepository = mock(FuncionarioRepository.class);

    @BeforeAll
    public static void setup() {
        funcionarioService = new FuncionarioService(funcionarioRepository, null, null);
    }

    @Test
    void addSupervisorHappyPath() {
        // Arrange
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.of(new Funcionario()));
        var funcionario = new Funcionario();
        var payload = getSamplePayload();

        // Act
        funcionarioService.addSupervisor(payload, funcionario);

        // Assert
        Assert.isTrue(funcionario.getSupervisor() != null, "Supervisor foi adicionado");
    }

    @Test
    void addSupervisorWithoutAValidPayload_ExpectError() {
        // Arrange
        var funcionario = new Funcionario();
        var payload = new FuncionarioDto(); // without a Supervisor

        // Act
        funcionarioService.addEndereco(payload, funcionario);

        // Assert
        Assert.isNull(funcionario.getSupervisor(), "Supervisor é nulo, pois o payload não contém um Supervisor válido");
    }

    @Test
    void addUnexistingSupervisor_ExpectError() {
        // Arrange
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.empty());
        var funcionario = new Funcionario();
        var payload = getSamplePayload();

        // Act
        try {
            funcionarioService.addSupervisor(payload, funcionario);
        } catch (NoSuchElementException e) {
            // Assert
            Assert.isTrue(e != null, "Exception nao nula");
        }
    }

    FuncionarioDto getSamplePayload() {
        var payload = new FuncionarioDto();
        payload.setSupervisor(Mockito.anyLong());
        return payload;
    }
}
