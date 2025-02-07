package com.projeto.avapay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.avapay.Dto.AddressDTO;
import com.projeto.avapay.Dto.ClientCreationDTO;
import com.projeto.avapay.Dto.UserUpdateDTO;
import com.projeto.avapay.model.Account;
import com.projeto.avapay.model.Address;
import com.projeto.avapay.model.Role;
import com.projeto.avapay.model.User;
import com.projeto.avapay.repository.AccountRepository;
import com.projeto.avapay.repository.AddressRepository;
import com.projeto.avapay.repository.RoleRepository;
import com.projeto.avapay.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	
	
	@Autowired // DI que auxilia nas operações de autenticação do usuario
	private AuthenticationManager authenticationManager;
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	
	 @Autowired
	    private RoleRepository roleRepository;
	 

	 
	 
	
	  @Autowired
	    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private AddressService addressService; // Serviço de endereço para reutilizar as operações
    
    // Criar um usuário com endereço
    
    
    
    

    @Transactional
    public User createClient(ClientCreationDTO clientCreationDTO) {
        // 1. Criar o usuário
        User user = new User();
        user.setName(clientCreationDTO.getName());
        user.setCpf(clientCreationDTO.getCpf());
        user.setEmail(clientCreationDTO.getEmail());
        user.setPhone(clientCreationDTO.getPhone());
        user.setPassword(clientCreationDTO.getPassword());
        user.setCreationDate(LocalDateTime.now());
        user.setActive(true);

        // Criptografando a senha
        String encryptedPassword = passwordEncoder.encode(clientCreationDTO.getPassword());
        user.setPassword(encryptedPassword);

        // Salvando o usuário
        user = userRepository.save(user);

        // 2. Criar o endereço associado ao usuário
        Address address = new Address();
        address.setStreet(clientCreationDTO.getStreet());
        address.setNumber(clientCreationDTO.getNumber());
        address.setComplement(clientCreationDTO.getComplement());
        address.setPostalCode(clientCreationDTO.getPostalCode());
        address.setNeighborhood(clientCreationDTO.getNeighborhood());
        address.setCity(clientCreationDTO.getCity());
        address.setState(clientCreationDTO.getState());
        address.setUser(user);

        address = addressRepository.save(address); // Salvando o endereço
        user.setAddress(address); // Associando o endereço ao usuário

        // Salvando o usuário com o endereço atualizado
        user = userRepository.save(user); 

        // 3. Criar a conta bancária associada ao usuário
        Account account = new Account();
        account.setAccountType("Simples"); // Tipo da conta
        account.setAgency(1704); // Agência (ajuste conforme seu sistema)
        account.setBalance(BigDecimal.ZERO); // Saldo inicial
        account.setCpf(user.getCpf());
        account.setAccountNumber(accountService.createAccountNumber()); // Gerando número da conta
        account.setUser(user); // Associando a conta ao usuário
        account = accountRepository.save(account); // Salvando a conta no banco

        // 4. Retornar o usuário com o endereço e a conta criados
        return user;
    }

    
    
    
    // Atualizar dados do usuário
    @Transactional
    public User updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        // Verifica se o usuário existe
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));

        // Atualiza os dados do usuário
        user.setName(userUpdateDTO.getName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPhone(userUpdateDTO.getPhone());
     

        // Salva o usuário atualizado
        user = userRepository.save(user);

        // Retorna o usuário atualizado
        return user;
    }

    // Deletar usuário
    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com o ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    

    // Buscar todos os usuários
    public List<User> findAll() {
        return userRepository.findAll(); // Retorna todos os usuários
    }

    // Buscar usuário por ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id); // Retorna um usuário pelo ID
    }

    // Buscar usuário por CPF
    public Optional<User> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf); // Retorna um usuário pelo CPF
    }
   
 }






	

