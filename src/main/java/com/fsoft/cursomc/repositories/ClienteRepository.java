package com.fsoft.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.cursomc.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
