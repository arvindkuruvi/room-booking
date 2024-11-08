package com.management.event.handler;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
		return new ResponseEntity<>("Date time format has to be in YYYY-MM-DD HH:mm:ss", HttpStatus.BAD_REQUEST);
	}
}