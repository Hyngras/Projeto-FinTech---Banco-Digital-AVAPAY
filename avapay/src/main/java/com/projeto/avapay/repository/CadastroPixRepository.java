package com.projeto.avapay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.avapay.model.Account;
import com.projeto.avapay.model.CadastroPix;

public interface CadastroPixRepository extends JpaRepository <CadastroPix, Long>{

	
	    List<CadastroPix> findByAccount(Account account);

		
		Optional<CadastroPix> findByPix(String pix);
	}

