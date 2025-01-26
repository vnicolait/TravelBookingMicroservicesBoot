package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import exceptions.FieldsCannotBeNullException;
import exceptions.FlightNotFoundException;
import exceptions.InvalidDateRangeException;
import exceptions.UnAvailableCapacity;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FieldsCannotBeNullException.class)
	public ResponseEntity<String> method1(FieldsCannotBeNullException ie){
		return new ResponseEntity<String>(ie.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDateRangeException.class)
	public ResponseEntity<String> method2(InvalidDateRangeException ie){
		return ResponseEntity.badRequest().body(ie.getMessage());
	}
	
	//services external
	@ExceptionHandler(FlightNotFoundException.class)
	public ResponseEntity<String> method3(FlightNotFoundException ie){
		return ResponseEntity.badRequest().body(ie.getMessage());
	}
	
	@ExceptionHandler(UnAvailableCapacity.class)
	public ResponseEntity<String> method4(UnAvailableCapacity ie){
		return ResponseEntity.badRequest().body(ie.getMessage());
	}
}
