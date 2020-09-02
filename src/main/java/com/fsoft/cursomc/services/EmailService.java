package com.fsoft.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.models.Pedido;

public interface EmailService {

	void sendEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage smm);

	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
