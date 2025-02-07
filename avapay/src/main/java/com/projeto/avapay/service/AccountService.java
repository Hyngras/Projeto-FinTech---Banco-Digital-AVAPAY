package com.projeto.avapay.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.avapay.model.Account;
import com.projeto.avapay.repository.AccountRepository;
import com.projeto.avapay.repository.UserRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;

    public String createAccountNumber() {
        int min = 1000000;
        int max = 9999999;

        Random random = new Random();
        int randNumber;

        do {
            randNumber = random.nextInt((max - min) + 1) + min;
        } while (accountRepository.existsByAccountNumber(String.valueOf(randNumber)));  // Método correto

        return String.valueOf(randNumber);
    }
    
    
 


    // Obter uma conta por ID
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    // Obter uma conta por número da conta
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    // Obter uma conta por CPF do cliente
    public Optional<Account> getAccountByCustomerCpf(String cpf) {
        return accountRepository.findByCpf(cpf);
    }


    // Deletar conta
    public boolean deleteAccount(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }
}





