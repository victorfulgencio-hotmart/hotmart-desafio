package com.example.hotmartdesafio.repositories;

import com.example.hotmartdesafio.models.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
    @Query(value = "SELECT f FROM Funcionario f JOIN f.projetos p WHERE p.id=?1")
    List<Funcionario> findFuncionariosByProjetoId(Long projetoId);

    @Query(value = "SELECT f FROM Funcionario f WHERE f.nome LIKE %:nome%")
    List<Funcionario> findByNome(String nome);

    @Query(value = "SELECT f FROM Funcionario f WHERE f.supervisor.id=?1")
    List<Funcionario> findFuncionariosBySupervisor(Long supervisorId);
}
