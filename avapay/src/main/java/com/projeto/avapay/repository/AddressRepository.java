package com.projeto.avapay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.avapay.model.Address;
import com.projeto.avapay.model.User;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	Address findByUserName(String userName);
	Optional<Address> findByUser(User user);
	Optional<Address> findByUserCpf(String cpf);
	

}
