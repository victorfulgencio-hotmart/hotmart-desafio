package com.example.hotmartdesafio.repositories;

import com.example.hotmartdesafio.models.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
}
