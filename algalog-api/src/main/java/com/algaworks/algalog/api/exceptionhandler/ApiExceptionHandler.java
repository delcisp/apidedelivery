package com.algaworks.algalog.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalog.domain.exception.NegocioException;




@ControllerAdvice

//a linha 5 diz que a classe eh um componente spring porém com um propósito específico de tratar exceções de forma global(pra todos os controladores da aplicação)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
public ApiExceptionHandler(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}
//extendendo uma classe do spring que ja faz alguns tratamentos de exceção
	
	private MessageSource messageSource;
	//essa é uma interface para resolver mensagens
	
	
	//o comando da linha abaixo eh "handlemethodargumentnotvalid"
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<Problema.Campo> campos = new ArrayList<>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			//como nao tem o nome do campo é preciso fazer esse cast na linha acima (p fazer tem que ter certeza que o objecterror eh um fielderror
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		Problema problema = new Problema(null, null, null, null);
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	//CASO VÁ UTILIZAR OS CARACTERES ESPECIAIS TEM QUE MUDAR AS PREFERENCIAS DO STS PARA UTF-8
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)	
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request ) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema(null, null, null, null);
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)	
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request ) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema(null, null, null, null);
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
}
