package com.projeto.avapay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.avapay.Dto.AddressDTO;
import com.projeto.avapay.model.Address;
import com.projeto.avapay.model.User;
import com.projeto.avapay.repository.AddressRepository;
import com.projeto.avapay.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;
    
    // Criar endereço para o usuário
 
  

   
    
    @Transactional
    public Address updateUserAddress(Long userId, AddressDTO addressDTO) {
        // Verifica se o usuário existe
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));

        // Verifica se o endereço do usuário já existe
        Address address = addressRepository.findByUser(user)
            .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado para o usuário com ID: " + userId));  // Retorna erro se não encontrar o endereço

        // Atualiza os dados do endereço com as novas informações
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());

        // Salva o endereço atualizado no banco
        return addressRepository.save(address);
    }
    
    @Transactional
    public Address getAddressByUserId(Long userId) {
        // Verifica se o usuário existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));

        // Retorna o endereço associado a esse usuário
        return addressRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado para o usuário com ID: " + userId));
    }
    
    @Transactional
    public void deleteAddress(Long userId) {
        // Verifica se o usuário existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));

        // Verifica se o endereço existe para o usuário
        Address address = addressRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado para o usuário com ID: " + userId));

        // Deleta o endereço
        addressRepository.delete(address);
    }
    public Address findAddressByUserCpf(String cpf) {
        // Primeiro, buscamos o usuário pelo CPF
        User user = userRepository.findByCpf(cpf)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o CPF: " + cpf));

        // Agora, encontramos o endereço do usuário
        Optional<Address> address = addressRepository.findByUserCpf(cpf);

        // Retorna o endereço, ou lança exceção caso não encontre
        return address.orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado para o usuário com CPF: " + cpf));
    }

}
