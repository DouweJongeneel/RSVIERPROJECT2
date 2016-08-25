package com.adm.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.hibernate.exception.ConstraintViolationException;
import com.adm.exceptions.DuplicateEntryException;

@ControllerAdvice
public class AppWideExceptionHandler {
	
	@ExceptionHandler({DuplicateEntryException.class, ConstraintViolationException.class})
	public String duplicateEntryFound() {
	    return "error/duplicaat";
	  }

}
