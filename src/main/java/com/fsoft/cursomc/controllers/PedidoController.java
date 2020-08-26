package com.fsoft.cursomc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.cursomc.models.Pedido;
import com.fsoft.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoController {
	
	
	@Autowired
	private PedidoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido pedido = service.find(id);
		return ResponseEntity.ok(pedido);
	}

}
