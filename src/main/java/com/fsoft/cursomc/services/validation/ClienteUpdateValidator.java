package com.fsoft.cursomc.services.validation;

import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fsoft.cursomc.controllers.exception.FieldMessage;
import com.fsoft.cursomc.dto.ClienteDTO;
import com.fsoft.cursomc.models.Cliente;
import com.fsoft.cursomc.repositories.ClienteRepository;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private HttpServletRequest req;
	
	@Override
	public void initialize(ClienteUpdate constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(ClienteDTO dto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		@SuppressWarnings("unchecked")
		Map<String, String> map =  (Map<String, String>) req.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		Cliente clienteEmail = repository.findByEmail(dto.getEmail());
		if (clienteEmail != null && !clienteEmail.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "email já existente"));
		}
		
		for (FieldMessage fieldMessage : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
			.addPropertyNode(fieldMessage.getFieldName())
			.addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
