package com.cjc.app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = DataNotAvailableException.class)
	public ResponseEntity<ApiError> handleApplication(DataNotAvailableException ex, HttpServletRequest request) {
		ApiError apiError = new ApiError();
		apiError.setMessage("Data Not Present In DB..!");
		apiError.setTimeStamp(LocalDateTime.now());
		apiError.setPath(request.getRequestURI());
		apiError.setStatus(HttpStatus.NO_CONTENT.value());

		return new ResponseEntity<ApiError>(apiError, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(value = StateNotValidException.class)
	public ResponseEntity<ApiError> handleApplication(StateNotValidException ex, HttpServletRequest request) {
		ApiError apiError = new ApiError();
		apiError.setMessage("No Such State Valid, Plz Enter Valid State..!");
		apiError.setTimeStamp(LocalDateTime.now());
		apiError.setPath(request.getRequestURI());
		apiError.setStatus(HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidations(MethodArgumentNotValidException ex, HttpServletRequest request) 
	{
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
