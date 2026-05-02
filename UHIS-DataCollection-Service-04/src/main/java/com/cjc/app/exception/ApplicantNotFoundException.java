package com.cjc.app.exception;

public class ApplicantNotFoundException extends RuntimeException {
    
	public ApplicantNotFoundException(String msg)
	{
		super("Applicant Not Found For Id : " + msg);
	}
}
