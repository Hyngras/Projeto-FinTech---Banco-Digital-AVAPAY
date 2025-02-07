package com.projeto.avapay.controller;

import com.projeto.avapay.Dto.AddressDTO;
import com.projeto.avapay.model.Address;
import com.projeto.avapay.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Endpoint para criar um endereço para o usuário
    @PostMapping("UpdateOrCreateAddress/{userId}")
    public ResponseEntity<Address> createAddress(@PathVariable Long userId, @RequestBody AddressDTO addressDTO) {
        Address address = addressService.updateUserAddress(userId, addressDTO);
        return ResponseEntity.ok(address);
    }

 

    // Endpoint para obter o endereço de um usuário pelo ID
    @GetMapping("getAddress/{userId}")
    public ResponseEntity<Address> getAddress(@PathVariable Long userId) {
        Address address = addressService.getAddressByUserId(userId);
        return ResponseEntity.ok(address);
    }

    // Endpoint para deletar o endereço de um usuário
    @DeleteMapping("deleteAddress/{userId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long userId) {
        addressService.deleteAddress(userId);
        return ResponseEntity.ok("Endereço deletado com sucesso!");
    }

    // Endpoint para buscar o endereço de um usuário pelo CPF
   /* @GetMapping("getAddressByCpf/cpf")
    public ResponseEntity<Address> getAddressByCpf(@RequestParam String cpf) {
        Address address = addressService.findAddressByUserCpf(cpf);
        return ResponseEntity.ok(address);
    }*/
    
    @GetMapping("/address/{cpf}")
    public ResponseEntity<AddressDTO> getAddressByCpf(@PathVariable String cpf) {
        // Buscar o endereço pelo CPF
        Address address = addressService.findAddressByUserCpf(cpf);

        // Mapear para o DTO, sem incluir o usuário
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setComplement(address.getComplement());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setNeighborhood(address.getNeighborhood());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());

        return ResponseEntity.ok(addressDTO);
    }
}
