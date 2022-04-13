package com.example.hotmartdesafio.repositories;

import com.example.hotmartdesafio.models.Projeto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjetoRepository extends CrudRepository<Projeto, Long> {
    @Query(value =
            "SELECT p.id, p.nome, p.custo FROM funcionario_projetos fp\n" +
                    "LEFT JOIN projeto p ON fp.funcionario_id=?1 AND p.id=fp.projeto_id",
            nativeQuery = true)
    List<Projeto> findProjetosByFuncionarioId(Long id);
}
