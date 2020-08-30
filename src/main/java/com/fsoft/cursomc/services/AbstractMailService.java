package com.fsoft.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.fsoft.cursomc.models.Pedido;

public abstract class AbstractMailService implements EmailService{

	@Override
	public void sendEmail(Pedido pedido) {
		SimpleMailMessage smm = createSimpleMailMessage(pedido);
		sendEmail(smm);
	}

	@Value("${default.sender}")
	private String sender;
	
	@Value("${default.recipient}")
	private String recipient;
	
	protected SimpleMailMessage createSimpleMailMessage(Pedido pedido) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(pedido.getCliente().getEmail());
		smm.setFrom(sender);
		smm.setSubject("Pedido confirmado");
		smm.setSentDate(new Date(System.currentTimeMillis()));
		smm.setText(pedido.toString());
		return smm;
	}
}
