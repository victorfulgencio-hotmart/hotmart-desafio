package com.example.hotmartdesafio.Departamento.services.endereco;

import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
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
public class UpdateEnderecoTests {
    private static EnderecoService enderecoService;

    private static final EnderecoRepository enderecoRepository = mock(EnderecoRepository.class);
    private static final long mockedDepartamentoid = 1L;

    @BeforeAll
    public static void setup() {
        enderecoService = new EnderecoService(enderecoRepository);
    }

    @Test
    void changeEnderecoHappyPath() {
        // Arrange
        when(enderecoRepository.findById(mockedDepartamentoid)).thenReturn(Optional.of(new Endereco()));
        doAnswer(returnsFirstArg()).when(enderecoRepository).save(Mockito.any(Endereco.class));

        var payload = getSampleEndereco();

        // Act
        var result = enderecoService.updateEndereco(mockedDepartamentoid, payload);

        // Assert
        Assert.isTrue(result != null, "Endereco atualizado nao é nulo");
        Assert.isTrue(Objects.equals(result.getRua(), payload.getRua()), "Rua igual ao payload");
        Assert.isTrue(Objects.equals(result.getCep(), payload.getCep()), "Não foi passado no payload, deve ser nulo");
        Assert.isTrue(Objects.equals(result.getPais(), payload.getPais()), "País é igual ao payload");
        Assert.isTrue(Objects.equals(result.getCidade(), payload.getCidade()), "Cidade é igual ao payload");
        Assert.isTrue(Objects.equals(result.getUf(), payload.getUf()), "UF é igual ao payload");
    }

    Endereco getSampleEndereco() {
        var payload = new Endereco();
        payload.setUf("MG");
        payload.setPais("Brasil");
        payload.setRua("Rua t.");
        payload.setCidade("Cidade");

        return payload;
    }
}
