package com.example.hotmartdesafio.Departamento.services.departamento;

import com.example.hotmartdesafio.dtos.StatusEnum;
import com.example.hotmartdesafio.models.Departamento;
import com.example.hotmartdesafio.models.Projeto;
import com.example.hotmartdesafio.repositories.DepartamentoRepository;
import com.example.hotmartdesafio.repositories.FuncionarioRepository;
import com.example.hotmartdesafio.repositories.ProjetoRepository;
import com.example.hotmartdesafio.services.DepartamentoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestComponent
public class GetDepartamentoStatusTests {
    private static DepartamentoService departamentoService;

    // Dependencies
    private static final DepartamentoRepository departamentoRepository = mock(DepartamentoRepository.class);
    private static final ProjetoRepository projetoRepository = mock(ProjetoRepository.class);
    private static final FuncionarioRepository funcionarioRepository = mock(FuncionarioRepository.class);
    private static final long mockedDepartamentoid = 1L;

    @BeforeAll
    public static void setup() {
        departamentoService = new DepartamentoService(departamentoRepository, projetoRepository, funcionarioRepository);
    }

    @Test
    void getStatusVerdeWhenDepartamentoDoesNotHaveProjects() {
        // Arrange
        when(departamentoRepository.findById(mockedDepartamentoid)).thenReturn(Optional.of(new Departamento()));

        // Act
        var result = departamentoService.getStatus(mockedDepartamentoid);

        // Assert
        Assert.isTrue(result.getStatus() == StatusEnum.VERDE, "Custo é nulo");
        Assert.isTrue(result.getCustoTotal() == 0, "0 é o valor default para o custo");
    }

    @Test
    void getStatusVerdeWhenCustoTotalIslowerThanForecasted() {
        // Arrange
        double totalCost = 4000;
        int departamentoCost = 5000;
        when(departamentoRepository.findById(mockedDepartamentoid))
                .thenReturn(Optional.of(getDepartamentoWithProjects(totalCost, departamentoCost)));

        // Act
        var result = departamentoService.getStatus(mockedDepartamentoid);

        // Assert
        Assert.isTrue(result.getStatus() == StatusEnum.VERDE, "Custo é menor");
        Assert.isTrue(result.getCustoTotal() == totalCost, "Custo total é igual ao informado no departamento.");
    }

    @Test
    void getStatusAmareloWhenCustoTotalIs10PerCentHigher() {
        // Arrange
        double totalCost = 4400;
        int departamentoCost = 4000;
        when(departamentoRepository.findById(mockedDepartamentoid))
                .thenReturn(Optional.of(getDepartamentoWithProjects(totalCost, departamentoCost)));

        // Act
        var result = departamentoService.getStatus(mockedDepartamentoid);

        // Assert
        Assert.isTrue(result.getStatus() == StatusEnum.AMARELO, "Custo é 10% maior");
        Assert.isTrue(result.getCustoTotal() == totalCost, "Custo total é igual ao informado no departamento.");
    }

    @Test
    void getStatusVermelhoWhenCustoTotalHigherThan10PerCent() {
        // Arrange
        double totalCost = 5000;
        int departamentoCost = 4000;
        when(departamentoRepository.findById(mockedDepartamentoid))
                .thenReturn(Optional.of(getDepartamentoWithProjects(totalCost, departamentoCost)));

        // Act
        var result = departamentoService.getStatus(mockedDepartamentoid);

        // Assert
        Assert.isTrue(result.getStatus() == StatusEnum.VERMELHO, "Custo é maior do que o previsto");
        Assert.isTrue(result.getCustoTotal() == totalCost, "Custo total é igual ao informado no departamento.");
    }

    Departamento getDepartamentoWithProjects(double custo, int departamentoNumero) {
        var projetoId = 1L;
        var departamento = new Departamento();
        departamento.setNumero(departamentoNumero);
        var projeto = new Projeto();
        projeto.setId(projetoId);
        projeto.setCusto(custo);
        var projetos = Set.of(projeto);
        departamento.setProjetos(projetos);

        return departamento;
    }
}
