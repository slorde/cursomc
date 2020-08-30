package com.fsoft.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.fsoft.cursomc.models.Pedido;

public interface EmailService {

	void sendEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
