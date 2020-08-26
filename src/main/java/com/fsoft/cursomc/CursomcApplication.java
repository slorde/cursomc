package com.fsoft.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fsoft.cursomc.models.Categoria;
import com.fsoft.cursomc.models.Cidade;
import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.models.Endereco;
import com.fsoft.cursomc.models.Estado;
import com.fsoft.cursomc.models.Produto;
import com.fsoft.cursomc.models.enums.TipoCliente;
import com.fsoft.cursomc.repositories.CategoriaRepository;
import com.fsoft.cursomc.repositories.CidadeRepository;
import com.fsoft.cursomc.repositories.ClienteRepository;
import com.fsoft.cursomc.repositories.EnderecoRespository;
import com.fsoft.cursomc.repositories.EstadoRepository;
import com.fsoft.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRespository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRespository enderecoRespository;
	
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
		
		Estado estado1 = new Estado("Paraná");
		Estado estado2 = new Estado("Goiás");
		
		Cidade cidade1 = new Cidade("Jandaia do Sul", estado1);
		Cidade cidade2 = new Cidade("Portelândia", estado2);
		Cidade cidade3 = new Cidade("Maringá", estado1);
		
		estado1.addCidade(cidade1);
		estado1.addCidade(cidade3);
		estado2.addCidade(cidade2);
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente("FISICA", "teste@teste", "55555555555", TipoCliente.FISICA.getCodigo());
		cliente1.addTelefone("999999999");
		
		Cliente cliente2 = new Cliente("JURIDICA", "EMP@tESA", "12345678901234", TipoCliente.JURIDICA.getCodigo());
		cliente2.addTelefone("555555555");
		cliente2.addTelefone("34321234");
		
		Endereco endereco1 = new Endereco("Rua josé", "123", "fundo", "centro", "86900000", cliente1, cidade1);
		cliente1.addEndereco(endereco1);
		
		Endereco endereco2 = new Endereco("Rua teste", "4444", "", "Jardim", "69483858", cliente2, cidade2);
		cliente2.addEndereco(endereco2);
		
		Endereco endereco3 = new Endereco("Rua asadf", "22", "", "Teste", "111", cliente2, cidade3);
		cliente2.addEndereco(endereco3);

		clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
		enderecoRespository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));
	}

}
