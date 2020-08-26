package com.fsoft.cursomc.controllers.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fsoft.cursomc.exceptions.NotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<StandardError> notFound(NotFoundException e, HttpServletRequest req) {
		StandardError error = new StandardError(NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(NOT_FOUND).body(error);
	}
}
