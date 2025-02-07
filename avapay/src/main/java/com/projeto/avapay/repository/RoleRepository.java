package com.projeto.avapay.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.avapay.model.Role;



public interface RoleRepository extends JpaRepository <Role, Long> {
	//UserType findByName (String userTypeName);
	
	Optional<Role> findByRoleName(String roleName);
	
	
}
