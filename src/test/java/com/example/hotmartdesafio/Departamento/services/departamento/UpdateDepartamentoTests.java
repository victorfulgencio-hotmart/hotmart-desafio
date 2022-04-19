package com.example.hotmartdesafio.Departamento.services.departamento;

import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.services.DepartamentoService;
import com.example.hotmartdesafio.services.EnderecoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@TestComponent
public class UpdateDepartamentoTests {
    private static DepartamentoService departamentoService;

    private static final DepartamentoRepository departamentoRepository = mock(DepartamentoRepository.class);

    @BeforeAll
    public static void setup() {
        departamentoService = new DepartamentoService(departamentoRepository, null, null);
    }

    @Test
    void changeDepartamentoHappyPath() {
        // Arrange
        when(departamentoRepository.findById(anyLong())).thenReturn(Optional.of(new Departamento()));
        doAnswer(returnsFirstArg()).when(departamentoRepository).save(Mockito.any(Departamento.class));
        var payload = getSamplePayload();

        // Act
        var result = departamentoService.updateDepartamento(anyLong(), payload);

        // Assert
        Assert.isTrue(Objects.equals(result.getNumero(), payload.getNumero()), "Numero é igual");
        Assert.isTrue(Objects.equals(result.getNome(), payload.getNome()), "Nome é igual");
    }

    Departamento getSamplePayload() {
        var departamento = new Departamento();
        departamento.setNumero(10000);
        departamento.setNome("Nome");

        return departamento;
    }
}
