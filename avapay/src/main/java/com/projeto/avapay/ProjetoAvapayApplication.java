package com.projeto.avapay;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.projeto.avapay.model.Role;
import com.projeto.avapay.repository.RoleRepository;


@SpringBootApplication
public class ProjetoAvapayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoAvapayApplication.class, args);
	}
	  @Bean
	    CommandLineRunner init(RoleRepository roleRepository) {
	        return args -> {
	            // Verifica se a role "ADMIN" já existe
	            Optional<Role> adminRole = roleRepository.findByRoleName("ADMIN");
	            if (adminRole.isEmpty()) {
	                Role newAdminRole = new Role();
	                newAdminRole.setRoleName("ADMIN"); // Usando setRoleName
	                roleRepository.save(newAdminRole);
	            }

	            // Verifica se a role "USER" já existe
	            Optional<Role> userRole = roleRepository.findByRoleName("USER");
	            if (userRole.isEmpty()) {
	                Role newUserRole = new Role();
	                newUserRole.setRoleName("USER"); // Usando setRoleName
	                roleRepository.save(newUserRole);
	            }
	        };
	    }


	}
