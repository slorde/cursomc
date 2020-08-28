package com.fsoft.cursomc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.cursomc.controllers.utils.QueryParams;
import com.fsoft.cursomc.dto.ProdutoDTO;
import com.fsoft.cursomc.models.Produto;
import com.fsoft.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoController {
	
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto produto = service.find(id);
		return ResponseEntity.ok(produto);
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue = "") String nome,
			@RequestParam(value="categorias", defaultValue ="") String categorias,
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="limit", defaultValue = "10") Integer limit, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		
		List<Integer> categoriasConverted = QueryParams.convertIntegers(categorias);
		String nomeConverted = QueryParams.decodeParam(nome);		
		
		Page<Produto> produtos = service.search(nomeConverted, categoriasConverted, page, limit, direction, orderBy);
		Page<ProdutoDTO> categoriasDTO = produtos.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok(categoriasDTO);
	}

}
