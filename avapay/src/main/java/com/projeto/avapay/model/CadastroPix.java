package com.projeto.avapay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CadastroPix")
public class CadastroPix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pix_id")
    private Long pixId;

    @Column(name = "pix_type", nullable = false, length = 50)
    private String pixType;

    @Column(name = "pix", length = 255)
    private String pix;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account; 

    // Getters and Setters
    public Long getPixId() {
        return pixId;
    }

    public void setPixId(Long pixId) {
        this.pixId = pixId;
    }

    public String getPixType() {
        return pixType;
    }

    public void setPixType(String pixType) {
        this.pixType = pixType;
    }

    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


  
}