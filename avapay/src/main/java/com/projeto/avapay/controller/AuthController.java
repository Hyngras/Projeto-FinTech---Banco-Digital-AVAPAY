package com.projeto.avapay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.Dto.LoginDTO;
import com.projeto.avapay.Dto.RegisterDTO;
import com.projeto.avapay.model.User;
import com.projeto.avapay.service.CustomService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


	@RestController // indica que esta classe é um controller que lida com requisições http e 
	// retorna repostas em JSON ou XML
	@RequestMapping("/api/users")
	public class AuthController {
		
		// praticar as injeções de dependencia
		@Autowired
		private CustomService userService;
		
		@Autowired // DI que auxilia nas operações de autenticação do usuario
		private AuthenticationManager authenticationManager;
		
		// endpoint para login
		@PostMapping("/login")
		public ResponseEntity<?> login(@RequestBody LoginDTO loginDto){
			// fzer do try/catch para establecer a tentativa de autenticar e autorizar um usuario
			// a acessar "pedaços" restritos da aplicação]
			try {
				// receber os dados de autenticação do usuario e tentar autentica-lo com estes valores
				// para este proposito, vamos definir uma prop com a classe Authentication
				Authentication authentication = authenticationManager
						.authenticate(
									new UsernamePasswordAuthenticationToken(
											 loginDto.getEmail(), // aqui, recebendo o email do usuario
											 loginDto.getPassword() // aqui, recebendo a senha do usuario
											)
								);
				
				// definir a autenticação no contexto de segurança
				/* SecurityContextHolder: armazena a autenticação no contexto de segurança
					para que o usuario permaneça autenticado durante a sessão
					se for sucesso: retornar uma mensagem de sucesso
					caso de falha: retorna algo como 401 UNAUTHORIZED
				*/
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				// bloco de retorno da resposta JSON de sucesso
				Map<String, String> response = new HashMap<>();
				response.put("message", "Login feito com sucesso");
				return ResponseEntity.ok(response);			
				
			}catch(Exception e){
				// retornar uma resposta JSON
				Map<String, String> errorResponse = new HashMap<>();
				errorResponse.put("error", "Falhou o login: " + e.getMessage());
				
				// é a indicadação de ausencia de autorização - caso o login seja feito e, ainda assim, 
				// o usuario ainda tenah algum tipo de restrição
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
			}
		}
		
		// definir o endpoint para o cadastro de usuarios
		@PostMapping("/register")
		public ResponseEntity<?> createNewUser(
					@Valid @RequestBody RegisterDTO registerDto, 
					//auxilia na captura de erros de validação
					BindingResult bindingResult, 
					// receber o nome da role(papel) do usuario como parametro da requisição
					@RequestParam String roleName){
			
			// verificar se já existe um usuario com o mesmo email que está sendo cadastrado
			User userExists = userService.findUserByEmail(registerDto.getEmail());
			
			// verificar a existencia do email
			if(userExists != null) {
				bindingResult
				.rejectValue("email", "error.user", "Já existe um usuário cadastrado com este email!");
			}
			
			
			// verificar erros em relação a validação dos dados
			if(bindingResult.hasErrors()) {
				// vamos criar um mapeamento para armazenar  os erros de validação
				Map<String, String> errors = new HashMap<>();
				for(FieldError error: bindingResult.getFieldErrors()) {
					errors.put(error.getField(), error.getDefaultMessage());
				}
				// definir  o retorno do if
				return ResponseEntity.badRequest().body(errors); // aqui, retornando os erros de validação que foram
				// encontrados; caso ocorram
			}
			
			// bloco de criação do novo registro de usuario
			// este registro será gerado com o uso do DTO
			User user = new User();
			user.setEmail(registerDto.getEmail());
			user.setPassword(registerDto.getPassword());
			user.setName(registerDto.getName());
			user.setCpf(registerDto.getCpf());
			user.setCreationDate(registerDto.getCreationDate());
			user.setPhone(registerDto.getPhone());
			
			
			// salvar os dados do usuario (novo user) no db
			userService.saveUser(user, roleName); // passando o valor da prop roleName selecionado
			
			// definir a resposta JSON de sucesso
			Map<String, String> response = new HashMap<>();
			response.put("message", "Usuario cadastrado com sucesso!");
			
			return ResponseEntity.ok(response);
		}
		
		// definir o endpoint point logout
		@PostMapping("/logout")
		public ResponseEntity<Void> logout(
					HttpServletRequest request, 
					HttpServletResponse response
				){
			// obter a autenticação atual
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			// verificar essa autenticaticação - se foi obtida
			if(authentication != null) {
				// se o usuario estiver autenticado, invalidar a sessão
				new SecurityContextLogoutHandler().logout(request, response, authentication);
				System.out.println("Logout efetuada com sucesso para o usuario: " + authentication
						.getName());
			}else {
				System.out.println("Tentativa de logout sem usaurio autenticado!");
			}
			// retornando uma resposta 204 (No content) para indicar que o logut ocorreu com sucesso
			return ResponseEntity.noContent().build();
		}
		
		// ********************** DEFINIR O MÉTODO QUE CAPTURA O USUARIO LOGAD NA SESSÃO
		@GetMapping("/current-user")
		public String getLoggedInUser() {
		// obter a autenticação atual do contexto de segurança
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			// definir a expressão de retorno do método
			return authentication.getName();
		}
		
	}


