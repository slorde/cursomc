package com.fsoft.cursomc.services;

import static java.lang.String.format;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.Pedido;
import com.fsoft.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public Pedido find(Integer id) {
		Optional<Pedido> optional = repository.findById(id);
		return optional
				.orElseThrow(() -> new NotFoundException(format("Pedido não encontrada para o id [%d]", id)));
	}
}
