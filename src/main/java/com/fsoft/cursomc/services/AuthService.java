package com.fsoft.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsoft.cursomc.exceptions.NotFoundException;
import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.repositories.ClienteRepository;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random =new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null)
			throw new NotFoundException("Não encontrado o cliente");
		
		String newPass = newPassword();
		cliente.setSenha(encoder.encode(newPass));
		
		emailService.sendNewPasswordEmail(cliente, newPass);
		
		clienteRepository.save(cliente);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i =0; i< 10; i++) 
			vet[i] = randomChar();
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		
		if (opt == 0) {
			return (char) (random.nextInt(10) + 48);
		} else if(opt ==1) {
			return (char) (random.nextInt(26) + 65);
		} else {
			return (char) (random.nextInt(26) + 97);
		}
	}
}
