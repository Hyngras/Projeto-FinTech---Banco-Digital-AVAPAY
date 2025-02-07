package com.projeto.avapay.service;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.Account;
import com.projeto.avapay.model.CadastroPix;
import com.projeto.avapay.model.Transactions;
import com.projeto.avapay.repository.AccountRepository;
import com.projeto.avapay.repository.CadastroPixRepository;
import com.projeto.avapay.repository.TransactionsRepository;



@Service
public class TransactionsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private CadastroPixRepository cadastroPixRepository;

    // Método de depósito
    @Transactional
    public String depositar(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        account.setBalance(account.getBalance().add(amount));  // Adiciona o valor ao balance da conta
        accountRepository.save(account);

        // Registrar a transação
        Transactions transaction = new Transactions();
        transaction.setSourceAccount(account);  // Fonte da transação é a própria conta
        transaction.setAmount(amount);  // Valor do depósito
        transaction.setTransactionType("DEPOSITO");
        transaction.setTimestamp(LocalDateTime.now());
        transactionsRepository.save(transaction);

        return "Depósito realizado com sucesso!";
    }

    // Método de saque
    @Transactional
    public String sacar(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        account.setBalance(account.getBalance().subtract(amount));  // Subtrai o valor do balance da conta
        accountRepository.save(account);

        // Registrar a transação
        Transactions transaction = new Transactions();
        transaction.setSourceAccount(account);  // Fonte da transação é a própria conta
        transaction.setAmount(amount);  // Valor do saque
        transaction.setTransactionType("SAQUE");
        transaction.setTimestamp(LocalDateTime.now());
        transactionsRepository.save(transaction);

        return "Saque realizado com sucesso!";
    }

    // Método de transferência
    @Transactional
    public String transferir(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount) {
        Account sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber)
            .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

        Account destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
            .orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Transfere o valor
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        // Registrar a transação
        Transactions transaction = new Transactions();
        transaction.setSourceAccount(sourceAccount);  // Conta origem
        transaction.setDestinationAccount(destinationAccount);  // Conta destino
        transaction.setAmount(amount);  // Valor da transferência
        transaction.setTransactionType("TRANSFERENCIA");
        transaction.setTimestamp(LocalDateTime.now());
        transactionsRepository.save(transaction);

        return "Transferência realizada com sucesso!";
    }

    //pix 
   @Transactional
    public String realizarPix(String pix, String sourceAccountNumber, BigDecimal amount) {
        Account sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber)
            .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

        CadastroPix cadastroPix = cadastroPixRepository.findByPix(pix)
            ;

        Account destinationAccount = cadastroPix.getAccount();  // Recupera a conta associada à chave Pix

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Realiza o Pix
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        // Registrar a transação
        Transactions transaction = new Transactions();
        transaction.setSourceAccount(sourceAccount);  // Conta origem
        transaction.setDestinationAccount(destinationAccount);  // Conta destino
        transaction.setAmount(amount);  // Valor do Pix
        transaction.setTransactionType("PIX");
        transaction.setTimestamp(LocalDateTime.now());
        transactionsRepository.save(transaction);

        return "Pix realizado com sucesso!";
    

    
}
   



}