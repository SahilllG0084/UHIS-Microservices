package com.cjc.app.exception;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
	@ExceptionHandler(value = SSANotFoundException.class)
	public ResponseEntity<ApiError> handleApplication(SSANotFoundException ex, HttpServletRequest request) {
		ApiError apiError = new ApiError();
		apiError.setMessage("Data Not Present In DB..!");
		apiError.setTimeStamp(LocalDateTime.now());
		apiError.setPath(request.getRequestURI());
		apiError.setStatus(HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}
}
