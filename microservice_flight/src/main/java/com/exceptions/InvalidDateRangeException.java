package com.exceptions;

public class InvalidDateRangeException extends RuntimeException{

	public InvalidDateRangeException(String message) {
        super(message);
    }
}
