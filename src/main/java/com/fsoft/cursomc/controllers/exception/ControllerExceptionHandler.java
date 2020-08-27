package com.fsoft.cursomc.controllers.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fsoft.cursomc.exceptions.DataIntegrityException;
import com.fsoft.cursomc.exceptions.NotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<StandardError> notFound(NotFoundException e, HttpServletRequest req) {
		StandardError error = new StandardError(NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest req) {
		StandardError error = new StandardError(BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> beanValidation(MethodArgumentNotValidException e, HttpServletRequest req) {
		ValidationError error = new ValidationError(BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		
		for(FieldError fieldError: e.getBindingResult().getFieldErrors()) {
			error.addError(fieldError.getField() , fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(BAD_REQUEST).body(error);
	}
}
