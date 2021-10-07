package com.eduardo.communication.platform.exception.utils;

import java.util.List;

import org.springframework.validation.FieldError;

public class ErrorMessageFormatter {
	public static String format(List<FieldError> fieldErrors) {
		StringBuilder errorMessage = new StringBuilder(); 
		fieldErrors.forEach(e -> {
			errorMessage.append("Field: " + e.getField() + " - " + e.getDefaultMessage() + ",");
        });
		errorMessage.replace(errorMessage.lastIndexOf(","), errorMessage.length(), "");
		
		return errorMessage.toString();
	}
}
