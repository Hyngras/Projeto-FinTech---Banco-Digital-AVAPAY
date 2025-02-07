package com.projeto.avapay.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionsDTO {
	private Long transactionId;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;
    private float tfee;
 
    // Para Pix, vamos precisar das chaves Pix
    private String sourcePixKey;
    private String destinationPixKey;
 
    // Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }
 
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
 
    public Long getSourceAccountId() {
        return sourceAccountId;
    }
 
    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }
 
    public Long getDestinationAccountId() {
        return destinationAccountId;
    }
 
    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }
 
    public String getTransactionType() {
        return transactionType;
    }
 
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
 
    public BigDecimal getAmount() {
        return amount;
    }
 
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
 
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
 
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public float getTfee() {
        return tfee;
    }
 
    public void setTfee(float tfee) {
        this.tfee = tfee;
    }
 
    public String getSourcePixKey() {
        return sourcePixKey;
    }
 
    public void setSourcePixKey(String sourcePixKey) {
        this.sourcePixKey = sourcePixKey;
    }
 
    public String getDestinationPixKey() {
        return destinationPixKey;
    }
 
    public void setDestinationPixKey(String destinationPixKey) {
        this.destinationPixKey = destinationPixKey;
    }
}

