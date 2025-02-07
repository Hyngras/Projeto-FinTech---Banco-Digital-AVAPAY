package com.projeto.avapay.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.avapay.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	//User findByEmail(String email);
	 boolean existsByCpf(String cpf);
	 List<User> findByName(String name);
	User findUserById(User id);

	Optional<User> findById(Long id);
	Optional<User> findByCpf(String cpf);

	 User findByEmail(String email); 
}
