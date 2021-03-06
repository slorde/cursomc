package com.fsoft.cursomc.services;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.dto.CategoriaDTO;
import com.fsoft.cursomc.exceptions.DataIntegrityException;
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

	public Categoria create(Categoria categoria) {
		return repository.save(categoria);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getNome());
	}

	public void update(Categoria categoria) {
		find(categoria.getId());
		repository.save(categoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria com produtos");
		}
	}

	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer limit, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, limit, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
}
