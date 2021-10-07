package com.eduardo.communication.platform.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
	    ErrorResponse message = new ErrorResponse(
	        HttpStatus.NOT_FOUND.value(),
	        new Date(),
	        ex.getMessage(),
	        request.getDescription(false));
	    
	    return message;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {		
	    ErrorResponse message = new ErrorResponse(
	        HttpStatus.BAD_REQUEST.value(),
	        new Date(),
	        ErrorMessageFormatter.format(ex.getBindingResult().getFieldErrors()),
	        request.getDescription(false));
	    
	    return message;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse illegalArgumentExceptionException(IllegalArgumentException ex, WebRequest request) {		
	    ErrorResponse message = new ErrorResponse(
	        HttpStatus.BAD_REQUEST.value(),
	        new Date(),
	        ex.getMessage(),
	        request.getDescription(false));
	    
	    return message;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse globalExceptionHandler(Exception ex, WebRequest request) {
	    ErrorResponse message = new ErrorResponse(
	        HttpStatus.INTERNAL_SERVER_ERROR.value(),
	        new Date(),
	        ex.getMessage(),
	        request.getDescription(false));
	    
	    return message;
	}
}
