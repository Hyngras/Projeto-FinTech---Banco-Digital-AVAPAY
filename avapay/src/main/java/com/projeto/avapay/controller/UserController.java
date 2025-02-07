package com.projeto.avapay.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.Dto.ClientCreationDTO;
import com.projeto.avapay.Dto.UserUpdateDTO;
import com.projeto.avapay.model.User;
import com.projeto.avapay.service.UserService;

    @RestController
    @RequestMapping("/api/clients")
    public class UserController {

        @Autowired
        private UserService userService;

        // Criar um novo usuário, com endereço e conta bancária
        @PostMapping("/createClient")
        public ResponseEntity<User> createClient(@RequestBody ClientCreationDTO clientCreationDTO) {
            User newUser = userService.createClient(clientCreationDTO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        
      
        
        

        // Atualizar usuário
        @PutMapping("/updateUser/{userId}")
        public ResponseEntity<User> updateUser(@PathVariable Long userId,
                                               @RequestBody UserUpdateDTO userUpdateDTO) {
            // Chama o serviço para atualizar o usuário
            User updatedUser = userService.updateUser(userId, userUpdateDTO);

            // Retorna o usuário atualizado como resposta
            return ResponseEntity.ok(updatedUser);
        }

        // Buscar todos os usuários
        @GetMapping("/findAll")
        public ResponseEntity<List<User>> findAllUsers() {
            List<User> users = userService.findAll(); // Chama o serviço para buscar todos os usuários
            return ResponseEntity.ok(users); // Retorna a lista de usuários
        }

        // Buscar um usuário por ID
        @GetMapping("/findById/{id}")
        public ResponseEntity<User> findUserById(@PathVariable Long id) {
            Optional<User> user = userService.findById(id); // Chama o serviço para buscar o usuário pelo ID
            return user.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // Retorna 404 se não encontrar
        }

        // Buscar um usuário por CPF
        @GetMapping("/findByCpf/{cpf}")
        public ResponseEntity<User> findUserByCpf(@PathVariable String cpf) {
            Optional<User> user = userService.findByCpf(cpf); // Chama o serviço para buscar o usuário pelo CPF
            return user.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // Retorna 404 se não encontrar
        }

        // Deletar um usuário
        @DeleteMapping("/{userId}")
        public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
            userService.deleteUser(userId);
            return ResponseEntity.ok("Usuário deletado com sucesso!");  // Retorna uma mensagem de sucesso
        }
    }

    
    
    /*

    // Criar um novo usuário, com endereço e conta bancária
    @PostMapping("/createClient/{id}")
    public ResponseEntity<User> createClient(@RequestBody ClientCreationDTO clientCreationDTO) {
        User newUser = userService.createClient(clientCreationDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    
    
 
    
    //Atualizar usuário
    @PutMapping("updateUser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, 
                                           @RequestBody UserUpdateDTO userUpdateDTO) {
        // Chama o serviço para atualizar o usuário
        User updatedUser = userService.updateUser(userId, userUpdateDTO);

        // Retorna o usuário atualizado como resposta
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAll(); // Chama o serviço para buscar todos os usuários
        return ResponseEntity.ok(users); // Retorna a lista de usuários
    }

    // Buscar um usuário por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id); // Chama o serviço para buscar o usuário pelo ID
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // Retorna 404 se não encontrar
    }

    // Buscar um usuário por CPF
    @GetMapping("/findByCpf/{cpf}")
    public ResponseEntity<User> findUserByCpf(@PathVariable String cpf) {
        Optional<User> user = userService.findByCpf(cpf); // Chama o serviço para buscar o usuário pelo CPF
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // Retorna 404 se não encontrar
    }
  
    
   
    

    // Deletar um usuário
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Usuário deletado com sucesso!");  // Retorna uma mensagem de sucesso
    }
}
*/
