package com.fsoft.cursomc.services;

import static java.lang.String.format;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		
		return cliente.orElseThrow(() -> new NotFoundException(format("Cliente não encontrada para o id [%d]", id)));	
	}

}
