package com.projeto.avapay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.avapay.model.Account;
import com.projeto.avapay.model.User;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findByAccountNumber(String accountNumber);

	boolean existsByAccountNumber(String accountNumber);

	Optional<Account> findByCpf(String customerCpf);



	//boolean existsByAccountNumber(String accountNumber);
	
	
}
