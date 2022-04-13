package com.example.hotmartdesafio.repositories;

import com.example.hotmartdesafio.models.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
    @Query(value =
                "SELECT * FROM funcionario_projetos fp\n" +
                "LEFT JOIN funcionario f ON fp.projeto_id=?1 AND f.id=fp.funcionario_id;",
            nativeQuery = true)
    List<Funcionario> findFuncionariosByProjetoId(Long projetoId);
}
