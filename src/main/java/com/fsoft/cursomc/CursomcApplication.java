package com.fsoft.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fsoft.cursomc.models.Categoria;
import com.fsoft.cursomc.models.Produto;
import com.fsoft.cursomc.repositories.CategoriaRepository;
import com.fsoft.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRespository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria1 = new Categoria("opa");
		Categoria categoria2 = new Categoria("categ");
		
		Produto produto1 = new Produto("produto 1", 11.2d);
		Produto produto2 = new Produto("produto 2", 10000.2d);
		Produto produto3 = new Produto("produto 3", 55551.25d);
		
		categoria1.addProduto(produto1);
		categoria1.addProduto(produto2);
		categoria1.addProduto(produto3);

		categoria2.addProduto(produto2);
		
		produto1.addCategoria(categoria1);
		produto2.addCategoria(categoria1);
		produto3.addCategoria(categoria2);
		produto3.addCategoria(categoria1);
		
		categoriaRespository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
	}

}
