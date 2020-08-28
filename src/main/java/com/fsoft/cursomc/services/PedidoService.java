package com.fsoft.cursomc.services;

import static com.fsoft.cursomc.models.enums.EstadoPagamento.PENDENTE;
import static java.lang.String.format;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.ItemPedido;
import com.fsoft.cursomc.models.PagamentoComBoleto;
import com.fsoft.cursomc.models.Pedido;
import com.fsoft.cursomc.repositories.ItemPedidoRepository;
import com.fsoft.cursomc.repositories.PagamentoRepository;
import com.fsoft.cursomc.repositories.PedidoRepository;

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

	public Pedido find(Integer id) {
		Optional<Pedido> optional = repository.findById(id);
		return optional
				.orElseThrow(() -> new NotFoundException(format("Pedido não encontrada para o id [%d]", id)));
	}

	public Pedido create(@Valid Pedido pedido) {	
		pedido.setId(null);
		pedido.setInstante(new Date());
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
			itemPedido.setPreco(produtoService.find(itemPedido.getProduto().getId()).getPreco());
			itemPedido.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		
		return pedido;
	}
}
