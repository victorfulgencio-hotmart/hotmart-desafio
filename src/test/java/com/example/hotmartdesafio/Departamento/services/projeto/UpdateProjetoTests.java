package com.example.hotmartdesafio.Departamento.services.projeto;

import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import com.example.hotmartdesafio.services.ProjetoService;
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
public class UpdateProjetoTests {
    private static ProjetoService projetoService;

    private static final ProjetoRepository projetoRepository = mock(ProjetoRepository.class);
    private static final long mockedDepartamentoid = 1L;

    @BeforeAll
    public static void setup() {
        projetoService = new ProjetoService(projetoRepository);
    }

    @Test
    void changeProjetoHappyPath() {
        // Arrange
        when(projetoRepository.findById(mockedDepartamentoid)).thenReturn(Optional.of(new Projeto()));
        doAnswer(returnsFirstArg()).when(projetoRepository).save(Mockito.any(Projeto.class));

        var payload = new Projeto();
        payload.setCusto(1000.0);
        payload.setNome("New");

        // Act
        var result = projetoService.updateProjeto(mockedDepartamentoid, payload);

        // Assert
        Assert.isTrue(result != null, "Projeto atualizado nao é nulo");
        Assert.isTrue(Objects.equals(result.getNome(), payload.getNome()), "Nome igual ao payload");
        Assert.isTrue(Objects.equals(result.getCusto(), payload.getCusto()), "Custo é igual ao payload");
    }
}
