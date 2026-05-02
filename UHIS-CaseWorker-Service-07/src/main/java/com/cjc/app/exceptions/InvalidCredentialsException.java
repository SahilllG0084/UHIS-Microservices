package com.cjc.app.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    
	public InvalidCredentialsException(String msg) {
		super(msg);
	}
}
