package com.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static util.JwtConstants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.UserEntity;
import com.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
public class AuthController {

  AuthenticationManager authManager;
  @Autowired
  UserService userService;
	
	public AuthController(AuthenticationManager authManager) {
		this.authManager=authManager;
	}
	//@PostMapping(value="login",produces=MediaType.TEXT_PLAIN_VALUE)
	@PostMapping(value="login", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> login(@RequestParam("user") String user,@RequestParam("pwd") String pwd){
		System.out.println("USER " +user);
		System.out.println("PWD " +pwd);
		try {
			Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(user, pwd));
			String token=getToken(authentication);
			Map<String, String> response = new HashMap<>();
	        response.put("token", token);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(AuthenticationException ex) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	private String getToken(Authentication autentication) {
		       UserEntity user= userService.findUserByEmail(autentication.getName());
		String token = Jwts.builder()
				.setIssuedAt(new Date()) //fecha creación
				//.setSubject(autentication.getName()) //usuario
				.setSubject(user.getEmail())
				.claim("id_usuario", user.getIdUser())
				.claim("authorities",autentication.getAuthorities().stream() //roles
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setExpiration(new Date(System.currentTimeMillis() + TIME_TOKEN)) //fecha caducidad
				.signWith(Keys.hmacShaKeyFor(KEY.getBytes()))//clave y algoritmo para firma				
				.compact(); //generación del token
		return token;
	}

}
