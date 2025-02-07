package com.projeto.avapay.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Account;
import com.projeto.avapay.model.Transactions;
 
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

	List<Transactions> findTop10BySourceAccountOrderByTimestampDesc(Account account);
	
//------------------------------------------------------
	 List<Transactions> findBySourceAccount(Account sourceAccount);
	    List<Transactions> findByDestinationAccount(Account destinationAccount);

		

	
	
	
}