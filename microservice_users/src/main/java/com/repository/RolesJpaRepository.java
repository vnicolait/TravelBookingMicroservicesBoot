package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.RolesEntity;

public interface RolesJpaRepository extends JpaRepository<RolesEntity, Integer> {

}
