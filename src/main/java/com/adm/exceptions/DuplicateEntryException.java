package com.adm.exceptions;

public class DuplicateEntryException extends Exception{

	private static final long serialVersionUID = -8878551967615073220L;
	
	public DuplicateEntryException() {
	}
	
	public DuplicateEntryException(String message) {
		super(message);
	}
}
