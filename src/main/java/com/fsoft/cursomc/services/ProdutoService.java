package com.fsoft.cursomc.services;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.Categoria;
import com.fsoft.cursomc.models.Produto;
import com.fsoft.cursomc.repositories.CategoriaRepository;
import com.fsoft.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> optional = repository.findById(id);
		return optional
				.orElseThrow(() -> new NotFoundException(format("Produto não encontrada para o id [%d]", id)));
	}
	
	public Page<Produto> search(String nome, List<Integer> idCategorias,
			Integer page, Integer limit, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, limit, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(idCategorias);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
