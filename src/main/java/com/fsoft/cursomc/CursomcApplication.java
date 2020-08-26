package com.fsoft.cursomc;

import static com.fsoft.cursomc.models.enums.EstadoPagamento.PENDENTE;

import java.text.SimpleDateFormat;
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
import com.fsoft.cursomc.models.ItemPedido;
import com.fsoft.cursomc.models.Pagamento;
import com.fsoft.cursomc.models.PagamentoComBoleto;
import com.fsoft.cursomc.models.PagamentoComCartao;
import com.fsoft.cursomc.models.Pedido;
import com.fsoft.cursomc.models.Produto;
import com.fsoft.cursomc.models.enums.EstadoPagamento;
import com.fsoft.cursomc.models.enums.TipoCliente;
import com.fsoft.cursomc.repositories.CategoriaRepository;
import com.fsoft.cursomc.repositories.CidadeRepository;
import com.fsoft.cursomc.repositories.ClienteRepository;
import com.fsoft.cursomc.repositories.EnderecoRepository;
import com.fsoft.cursomc.repositories.EstadoRepository;
import com.fsoft.cursomc.repositories.ItemPedidoRepository;
import com.fsoft.cursomc.repositories.PagamentoRepository;
import com.fsoft.cursomc.repositories.PedidoRepository;
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
	private EnderecoRepository enderecoRespository;

	@Autowired
	private PedidoRepository pedidoRespository;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

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

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido pedido1 = new Pedido(sdf.parse("30/09/2017 10:33"), cliente1, endereco1);
		cliente1.addPedido(pedido1);

		Pedido pedido2 = new Pedido(sdf.parse("10/10/2017 11:44"), cliente2, endereco3);
		cliente2.addPedido(pedido2);

		Pagamento pagamento1 = new PagamentoComCartao(EstadoPagamento.QUITADO.getCodigo(), pedido1, 3);
		pedido1.setPagamento(pagamento1);

		Pagamento pagamento2 = new PagamentoComBoleto(PENDENTE.getCodigo(), pedido2, sdf.parse("01/01/2020 13:50"),
				null);
		pedido2.setPagamento(pagamento2);

		pedidoRespository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

		ItemPedido item1 = new ItemPedido(pedido1, produto1, 10.2, 4, 123.52);
		pedido1.addItem(item1);
		produto1.addItem(item1);
		
		ItemPedido item2 = new ItemPedido(pedido1, produto2, 11.2, 55, 321.1);
		pedido1.addItem(item2);
		produto2.addItem(item2);
		
		ItemPedido item3 = new ItemPedido(pedido2, produto3, 0d, 1, 10d);
		pedido2.addItem(item3);
		produto3.addItem(item3);
		
		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
	}

}
