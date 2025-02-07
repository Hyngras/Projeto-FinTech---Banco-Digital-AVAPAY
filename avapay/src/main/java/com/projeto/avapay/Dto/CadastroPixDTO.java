package com.projeto.avapay.Dto;

public class CadastroPixDTO {

    private String pix;        // Pix (chave Pix do cliente)
    private String pixType;    // Tipo de chave Pix (por exemplo, CPF, telefone, email)
    private Long pixId;        // Identificador do Cadastro Pix

    // Construtores
    public CadastroPixDTO() {}

    public CadastroPixDTO(String pix, String pixType) {
        this.pix = pix;
        this.pixType = pixType;
    }

    // Getters e Setters
    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

    public String getPixType() {
        return pixType;
    }

    public void setPixType(String pixType) {
        this.pixType = pixType;
    }

    public Long getPixId() {
        return pixId;
    }

    public void setPixId(Long pixId) {
        this.pixId = pixId;
    }

    @Override
    public String toString() {
        return "CadastroPixDTO{" +
                "pix='" + pix + '\'' +
                ", pixType='" + pixType + '\'' +'}';
               
}}