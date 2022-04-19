package com.example.hotmartdesafio.Departamento.services.departamento;

import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Endereco;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.repositories.EnderecoRepository;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import com.example.hotmartdesafio.services.DepartamentoService;
import com.example.hotmartdesafio.services.FuncionarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestComponent
public class AddProjetoTests {
    private static DepartamentoService departamentoService;

    private static final DepartamentoRepository departamentoRepository = mock(DepartamentoRepository.class);
    private static final ProjetoRepository projetoRepository = mock(ProjetoRepository.class);

    @BeforeAll
    public static void setup() {
        departamentoService = new DepartamentoService(departamentoRepository, projetoRepository, null);
    }

    @Test
    void addProjetoToDepartamentoHappyPath() {
        // Arrange
        var departamento = getSampleDepartamento();
        when(departamentoRepository.findById(anyLong())).thenReturn(Optional.of(departamento));
        doAnswer(returnsFirstArg()).when(projetoRepository).save(Mockito.any(Projeto.class));

        // Act
        var result = departamentoService.addProjeto(anyLong(), new Projeto());

        // Assert
        Assert.isTrue(result != null, "Projeto não é nulo");
    }

    Departamento getSampleDepartamento() {
        var departamento = new Departamento();
        departamento.setProjetos(new HashSet<>());
        return departamento;
    }
}
