package com.exceptions;


public class UserAlreadyExist extends RuntimeException{

	public UserAlreadyExist(String message) {
		super(message);
	}
}
