package com.fsoft.cursomc.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.cursomc.model.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaController {

	@GetMapping
	public List<Categoria> listar() {
		Categoria cat1 = new Categoria("CAT1");
		Categoria cat2 = new Categoria("OUTRA CAT");
		
		List<Categoria> categorias = Arrays.asList(cat1, cat2);
		
		return categorias;
	}
}
