package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.exception.BlockExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BlockExpiredException.class)
	public ResponseEntity<String> method1(BlockExpiredException ie){
		return new ResponseEntity<String>(ie.getMessage(),HttpStatus.UNAUTHORIZED);
	}  
	
//	@ExceptionHandler(InvalidDateRangeException.class)
//	public ResponseEntity<String> method2(InvalidDateRangeException ie){
//		return new ResponseEntity<String>(ie.getMessage(),HttpStatus.BAD_REQUEST);
//	}
//	
//	@ExceptionHandler(CodeNotExist.class)
//	public ResponseEntity<String> method3(CodeNotExist ie){
//		return new ResponseEntity<String>(ie.getMessage(),HttpStatus.CONFLICT);
//	}
//	
//	@ExceptionHandler(UnAvailableCapacity.class)
//	public ResponseEntity<String> method4(UnAvailableCapacity ie){
//		return new ResponseEntity<>(ie.getMessage(),HttpStatus.NOT_FOUND);
//	}
}
