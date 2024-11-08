package com.management.hotel.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleRoomNameAlreadyExistsException(DataIntegrityViolationException ex) {
		return new ResponseEntity<>("Room name already exists", HttpStatus.BAD_REQUEST);
	}
}