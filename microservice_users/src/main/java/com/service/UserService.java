package com.service;

import com.model.UserEntity;

public interface UserService {

	void registerUser(UserEntity entity);
	void login(String email, String password);
    UserEntity findUserByEmail(String email); // Nuevo método para buscar usuario

}