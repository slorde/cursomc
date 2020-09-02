package com.fsoft.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.models.Pedido;

public abstract class AbstractMailService implements EmailService{

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromTemplate(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void sendHtmlEmail(Pedido pedido) {
		try {
			MimeMessage mm = createMimeMessage(pedido);
			sendHtmlEmail(mm);
		} catch(MessagingException e) {
			sendEmail(pedido);
		}
	}

	private MimeMessage createMimeMessage(Pedido pedido) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(pedido.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Confirmação pedido");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplate(pedido), true);
		
		return mm;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage smm = createNewPasswordSimpleMailMessage(cliente, newPass);
		sendEmail(smm);
	}

	protected SimpleMailMessage createNewPasswordSimpleMailMessage(Cliente cliente, String newPass) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(cliente.getEmail());
		smm.setFrom(sender);
		smm.setSubject("Senha alterada");
		smm.setSentDate(new Date(System.currentTimeMillis()));
		smm.setText("Senha alterada" + newPass);
		return smm;
	}
}
