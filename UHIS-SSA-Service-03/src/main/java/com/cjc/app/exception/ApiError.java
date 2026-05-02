package com.cjc.app.exception;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiError {
	
	private LocalDateTime timeStamp;
	private String message;
	private String path;
	private int status;
}
