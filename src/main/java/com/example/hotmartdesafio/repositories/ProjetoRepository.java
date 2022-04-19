package com.example.hotmartdesafio.repositories;

import com.example.hotmartdesafio.models.Projeto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjetoRepository extends CrudRepository<Projeto, Long> {
    @Query(value = "SELECT p FROM Funcionario f JOIN f.projetos p WHERE f.id=?1")
    List<Projeto> findProjetosByFuncionarioId(Long id);
}
