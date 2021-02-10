package com.marcony.osmarcony.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marcony.osmarcony.domain.exception.AppException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(AppException.class)
	public ResponseEntity<Object> handleApp(AppException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		
		var problema = new Problema(status.value(), OffsetDateTime.now(),ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
		
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var campos = new ArrayList<Problema.Campo>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String mensagem = messageSource.getMessage(error,LocaleContextHolder.getLocale());
			String nome  =((FieldError) error).getField();
			campos.add(new Problema.Campo(nome, mensagem) );
		}
		
		var problema   = new Problema(status.value(),
				OffsetDateTime.now(),
				"Um ou mais campos estão inválidos, preencha todos corretamente",
				campos);
		
		
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

}
