package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.exceptions.CodeNotExist;
import com.exceptions.InvalidDateRangeException;
import com.exceptions.InvalidParameterException;
import com.exceptions.UnAvailableCapacity;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<String> method1(InvalidParameterException ie){
		return new ResponseEntity<String>(ie.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDateRangeException.class)
	public ResponseEntity<String> method2(InvalidDateRangeException ie){
		return new ResponseEntity<String>(ie.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CodeNotExist.class)
	public ResponseEntity<String> method3(CodeNotExist ie){
		return new ResponseEntity<String>(ie.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UnAvailableCapacity.class)
	public ResponseEntity<String> method4(UnAvailableCapacity ie){
		return new ResponseEntity<>(ie.getMessage(),HttpStatus.NOT_FOUND);
	}
}
