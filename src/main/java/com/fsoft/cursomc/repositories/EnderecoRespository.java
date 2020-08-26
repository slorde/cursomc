package com.fsoft.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.cursomc.models.Endereco;

@Repository
public interface EnderecoRespository extends JpaRepository<Endereco, Integer>{
}
