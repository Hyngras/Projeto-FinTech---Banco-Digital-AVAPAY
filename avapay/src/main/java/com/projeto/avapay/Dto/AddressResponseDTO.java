package com.projeto.avapay.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseDTO {
    private Long addressId;
    private String street;
    private int number;
    private String complement;
    private int postalCode;
    private String neighborhood;
    private String city;
    private String state;

    // Getters e Setters
}
