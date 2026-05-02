package com.cjc.app.exception;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiError {
    
	private String message;
	
	private String path;
	
	private int status;
	
	private LocalDateTime timeStamp; 
}
