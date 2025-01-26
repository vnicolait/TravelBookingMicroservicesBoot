package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.UserEntity;
import java.util.List;


public interface UserJpaRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findByEmail(String email);
}
