package com.projeto.avapay.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.service.TransactionsService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    // Endpoint de depósito
    @PostMapping("/deposit")
    public ResponseEntity<String> depositar(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        try {
            String response = transactionsService.depositar(accountNumber, amount);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint de saque
    @PostMapping("/withdraw")
    public ResponseEntity<String> sacar(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        try {
            String response = transactionsService.sacar(accountNumber, amount);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint de transferência
    @PostMapping("/transfer")
    public ResponseEntity<String> transferir(@RequestParam String sourceAccountNumber,
                                             @RequestParam String destinationAccountNumber,
                                             @RequestParam BigDecimal amount) {
        try {
            String response = transactionsService.transferir(sourceAccountNumber, destinationAccountNumber, amount);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    //--------------------------------------
    @PostMapping("/pix")
    public ResponseEntity<String> realizarPix(@RequestParam String pix,
                                              @RequestParam String sourceAccountNumber,
                                              @RequestParam BigDecimal amount) {
        try {
            // Chama o serviço para realizar o Pix
            String result = transactionsService.realizarPix(pix, sourceAccountNumber, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);  // Retorna sucesso com status 200
        } catch (RuntimeException e) {
            // Caso haja erro, retorna uma mensagem de erro com status 400
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    

}