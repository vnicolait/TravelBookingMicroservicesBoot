package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.UserAlreadyExist;
import com.exceptions.UserNotFound;
import com.model.UserEntity;
import com.repository.UserJpaRepository;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
	UserJpaRepository repository;
    
	@Override
	public void registerUser(UserEntity entity) {
      Optional<UserEntity> user=repository.findByEmail(entity.getEmail());
      if(user.isPresent()) {
    	  throw new UserAlreadyExist("user with email "+user.get().getEmail() +" already exist");
      }
       repository.save(user.get());
	}

	@Override
	public void login(String email, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserEntity findUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
	}

	@Override
	public UserEntity updateUser(UserEntity user, int idUser) {
		UserEntity entity=repository.findById(idUser).orElseThrow(()->
				new UserNotFound("User by id " + idUser + "not found"));
		UserEntity existingUser=repository.findByEmail(user.getEmail()).orElseThrow();
		if(existingUser!=null && existingUser.getIdUser()!=idUser) {
			 new UserAlreadyExist("Email " + existingUser.getEmail() + "is already in use");
		}
		entity.setName(user.getName());
		entity.setEmail(user.getEmail());
		entity.setPassword(user.getPassword());
		return repository.save(entity);
	}

	@Override
	public void deleteUser(int idUser) {
		if (!repository.existsById(idUser)) {
	        throw new UserNotFound("User with ID " + idUser + " not found");
	    }
	    repository.deleteById(idUser);
	}
	
	
	
	

}
