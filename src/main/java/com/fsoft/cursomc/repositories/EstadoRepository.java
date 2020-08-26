package com.fsoft.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.cursomc.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{

}
