package com.cjc.app.exception;

public class DataCollectionException extends RuntimeException{
    
	public DataCollectionException(String msg)
	{
		super("Applicant Data Not Found For Id : " + msg);
	}
}
