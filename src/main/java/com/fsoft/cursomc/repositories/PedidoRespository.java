package com.fsoft.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.cursomc.models.Pedido;

@Repository
public interface PedidoRespository extends JpaRepository<Pedido, Integer>{
	
	
	
}
