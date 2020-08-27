package com.fsoft.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fsoft.cursomc.controllers.exception.FieldMessage;
import com.fsoft.cursomc.dto.ClienteNewDTO;
import com.fsoft.cursomc.models.enums.TipoCliente;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO dto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (dto.getTipoCliente() == null) 
			list.add(new FieldMessage("tipo", "Tipo não pode ser nulo"));
		
		if (dto.getTipoCliente().equals(TipoCliente.FISICA) && dto.getCpfOuCnpj().length() != 11)
			list.add(new FieldMessage("cpfOuCnpj", "cpf inválido"));
		
		if (dto.getTipoCliente().equals(TipoCliente.JURIDICA) && dto.getCpfOuCnpj().length() != 14)
			list.add(new FieldMessage("cpfOuCnpj", "cnpj inválido"));
		
		for (FieldMessage fieldMessage : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
			.addPropertyNode(fieldMessage.getFieldName())
			.addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
