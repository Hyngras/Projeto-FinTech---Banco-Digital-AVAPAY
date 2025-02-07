package com.projeto.avapay.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.model.Account;
import com.projeto.avapay.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Criar nova conta vinculada a um usuário existente pelo CPF
 

    // Obter conta por ID
    @GetMapping("/{accountId}")
    public Optional<Account> getAccount(@PathVariable Long accountId) {
        return accountService.getAccountById(accountId);
    }

    // Obter conta por número da conta
    @GetMapping("/number/{accountNumber}")
    public Optional<Account> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    // Obter conta por CPF do cliente
    @GetMapping("/cpf/{cpf}")
    public Optional<Account> getAccountByCpf(@PathVariable String cpf) {
        return accountService.getAccountByCustomerCpf(cpf);
    }



    // Deletar conta
    @DeleteMapping("/{accountId}")
    public boolean deleteAccount(@PathVariable Long accountId) {
        return accountService.deleteAccount(accountId);
    }
}
