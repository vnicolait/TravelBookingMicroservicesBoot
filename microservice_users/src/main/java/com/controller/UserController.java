package com.controller;


import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	AuthenticationManager auth;
	
	public UserController(AuthenticationManager auth) {
		this.auth=auth;
	}
	
	ResponseEntity<String>login(@RequestParam("email")String email,
			                       @RequestParam("password")String password ){
		try {
		AuthenticationManager authentication= (AuthenticationManager) auth.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	    return new ResponseEntity<>(getToken(authentication),HttpStatus.OK);
		}catch(AuthenticationException ex) {
	    	return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
	    }
	}
	
	private String getToken(AuthenticationManager authentication) {
		return "";
	}
}
