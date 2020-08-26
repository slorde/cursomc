package com.fsoft.cursomc.services;

import static java.lang.String.format;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.Categoria;
import com.fsoft.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria find(Integer id) {
		Optional<Categoria> optional = repository.findById(id);
		return optional
				.orElseThrow(() -> new NotFoundException(format("Categoria não encontrada para o id [%d]", id)));
	}
}
