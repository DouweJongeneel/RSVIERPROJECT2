package com.adm.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adm.exceptions.DuplicateEntryException;

@ControllerAdvice
public class AppWideExceptionHandler {
	
	@ExceptionHandler(DuplicateEntryException.class)
	public String duplicateEntryFound() {
	    return "error/duplicaat";
	  }

}
