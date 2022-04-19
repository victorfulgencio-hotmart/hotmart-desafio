package com.example.hotmartdesafio.Departamento.services.funcionario;

import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Funcionario;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import com.example.hotmartdesafio.services.DepartamentoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestComponent
public class GetFuncionariosFromDepartamentoTests {
    private static DepartamentoService departamentoService;

    // Dependencies
    private static final DepartamentoRepository departamentoRepository = mock(DepartamentoRepository.class);
    private static final ProjetoRepository projetoRepository = mock(ProjetoRepository.class);
    private static final FuncionarioRepository funcionarioRepository = mock(FuncionarioRepository.class);

    @BeforeAll
    public static void setup() {
        departamentoService = new DepartamentoService(departamentoRepository, projetoRepository, funcionarioRepository);
    }

    @Test
    void returnNullWhenDepartmentHasNoProjects() {
        // Arrange
        var departamentoId = 1L;
        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(new Departamento()));

        // Act
        var funcionarios = departamentoService.getFuncionariosFromDepartamento(departamentoId);

        // Assert
        Assert.isNull(funcionarios, "Se o Departamento não tem projeto, não terá Funcionarios");
    }

    @Test
    void returnFuncionarioHappyPath() {
        // Arrange
        var projetoId = 1L;
        var departamentoId = 1L;
        var departamento = new Departamento();
        var projeto = new Projeto();
        projeto.setId(projetoId);
        var projetos = Set.of(projeto);
        departamento.setProjetos(projetos);

        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(departamento));
        when(funcionarioRepository.findFuncionariosByProjetoId(projetoId)).thenReturn(List.of(new Funcionario()));

        // Act
        var funcionarios = departamentoService.getFuncionariosFromDepartamento(departamentoId);

        // Assert
        Assert.isTrue(funcionarios != null, "Lista de Funcionarios nao é nula.");
        Assert.isTrue(!funcionarios.isEmpty(), "A lista nao é vazia.");
    }
}
