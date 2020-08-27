package com.fsoft.cursomc.services;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.dto.ClienteDTO;
import com.fsoft.cursomc.dto.ClienteNewDTO;
import com.fsoft.cursomc.exceptions.DataIntegrityException;
import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.Cidade;
import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.models.Endereco;
import com.fsoft.cursomc.repositories.ClienteRepository;
import com.fsoft.cursomc.repositories.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);

		return cliente.orElseThrow(() -> new NotFoundException(format("Cliente não encontrada para o id [%d]", id)));
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}

	@Transactional
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(),
				clienteNewDTO.getTipoCliente());
		
		Cidade cidade = new Cidade();
		cidade.setId(clienteNewDTO.getCidadeId());
		cliente.addEndereco(new Endereco(clienteNewDTO.getLogradouro(), 
				clienteNewDTO.getNumero() , 
				clienteNewDTO.getComplemento(), 
				clienteNewDTO.getBairro(), 
				clienteNewDTO.getCep(), 
				cliente, 
				cidade));
		
		cliente.addTelefone(clienteNewDTO.getTelefone1());
		return cliente;
	}

	public void update(Cliente cliente) {
		Cliente clienteUpdate = find(cliente.getId());
		clienteUpdate.setEmail(cliente.getEmail());
		clienteUpdate.setNome(cliente.getNome());
		repository.save(clienteUpdate);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente com dependencias");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer limit, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, limit, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente create(Cliente cliente) {
		repository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}
}
