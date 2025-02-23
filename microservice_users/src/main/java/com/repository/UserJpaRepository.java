package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.UserEntity;
import java.util.List;
import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByEmail(String email);
}
