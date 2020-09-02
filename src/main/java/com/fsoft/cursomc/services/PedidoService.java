package com.fsoft.cursomc.services;

import static com.fsoft.cursomc.models.enums.EstadoPagamento.PENDENTE;
import static java.lang.String.format;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.cursomc.exceptions.AuthorizationException;
import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.models.ItemPedido;
import com.fsoft.cursomc.models.PagamentoComBoleto;
import com.fsoft.cursomc.models.Pedido;
import com.fsoft.cursomc.models.Produto;
import com.fsoft.cursomc.repositories.ItemPedidoRepository;
import com.fsoft.cursomc.repositories.PagamentoRepository;
import com.fsoft.cursomc.repositories.PedidoRepository;
import com.fsoft.cursomc.security.UserSS;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> optional = repository.findById(id);
		return optional
				.orElseThrow(() -> new NotFoundException(format("Pedido não encontrada para o id [%d]", id)));
	}

	@Transactional
	public Pedido create(@Valid Pedido pedido) {	
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, 7);
			pagamentoComBoleto.setDataVencimento(cal.getTime());
		}
		
		repository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		for (ItemPedido itemPedido: pedido.getItens()) {
			itemPedido.setDesconto(0d);
			Produto produto = produtoService.find(itemPedido.getProduto().getId());
			itemPedido.setProduto(produto);

			itemPedido.setPreco(produto.getPreco());
			itemPedido.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		
		emailService.sendHtmlEmail(pedido);
		
		return pedido;
	}
	
	public Page<Pedido> findPage(Integer page, Integer limit, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null)
			throw new AuthorizationException("Acesso Negado");
		
		PageRequest pageRequest = PageRequest.of(page, limit, Direction.valueOf(direction), orderBy);
		
		Cliente cliente = clienteService.find(user.getId());
		
		return repository.findByCliente(cliente, pageRequest);
	}
}
