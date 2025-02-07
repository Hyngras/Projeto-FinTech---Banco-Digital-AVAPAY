package com.projeto.avapay.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.avapay.model.Role;
import com.projeto.avapay.model.User;
import com.projeto.avapay.repository.RoleRepository;
import com.projeto.avapay.repository.UserRepository;

import jakarta.transaction.Transactional;

// é necessario indicar que esta classe - classe java comum - precisa, para este projeto, 
// assumir o "papel" de um service
// para este proposito vamos indicar o uso da annotation @Service

// DAO (Data Access Object)

@Service
public class CustomService implements UserDetailsService {
	
	// definir as DIs (Dependency Injection)
	
	
	// 1º as entities
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	//2º a classe que dá a possibilidade de criar um Hash de senha para o usuario
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// definir uma prop/ objeto referencial para lidar com o contexto de criptografia da senha
	private final PasswordEncoder passwordEncoder;
	
	// definir o construtor da classe para gerar a DI para a criptografia da senha
	@Autowired
	public CustomService() {
		// aqui, vamos inicializar a prop para a criptografia da senha
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	// definir um método para criptografar a senha
	public String encodePassword(String password) {
		return passwordEncoder.encode(password); // aqui, estamos encriptando a senha
	}
	
	// definir o método para validar a senha
	public boolean validatePassword(String rawPassword, String encodedPassword) {
		// definir a expressão de retorno para comparar a senha que foi inserida com 
		// sua criptografia
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	
	
	/*
	 =================================================================================================
	 	AGORA, VAMOS PRATICAR A IMPLEMENTAÇÃO DOS METODOS PARA MANIPULAR EMAIL E SENHA DO USER
	 =================================================================================================
	 */
	// definir o método - este método PERTENCE AO SERVICE - que fará busca de um email
	public User findUserByEmail(String email) {
		// TAREFA QUE ESTE MÉTODO VAI EXECUTAR:
		// vai retornar algo para este Service - acesso e implementação do método do repository
		return userRepository.findByEmail(email);
	}
	
	
	// buscar, tambem, um usuario pelo Id
	public User findUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	/*
	 =================================================================================================
	 	NESTE PASSO, VAMOS DEFINIR UM METODO PARA SALVAR O USER NO DB
	 =================================================================================================
	 */
	
	// USAR UMA NOVA ANNOTATION
	 //  annotation utilizando para - especialmente - gerenciar transações de bancos 
	//de dados; usada como um "garantidor" que um conjunto de operações no banco seja tratado com uma 
	// única unidade "atômica" - ou seja, um conjunto de operações para a ser entendida como uma 
	// única operação - fazendo uso da @Transactional
	@Transactional
	public void saveUser(User user, String roleName) {
		
		// verficar se o roleName não é nulo ou vazio
		if(roleName == null || roleName.trim().isEmpty()) { // true
			// vamos lançar uma exception
			throw new IllegalArgumentException("Nome da role não pode ser nulo ou vazio!");
		}		
		
		// tentativa de criptografar a senha/hash
		user.setPassword(passwordEncoder.encode(user.getPassword())); // encriptando a senha
		user.setActive(true);// habilitando/ativando o usuario para usar o sistema
		
		// procurar a role no DB
		Optional<Role> roleOpt = roleRepository.findByRoleName(roleName);
		
		// verificar se a role foi encontrada no DB
		Role role;
		if(roleOpt.isEmpty()) { // true
			role = new Role();
			role.setRoleName(roleName); // definindo o nome da Role
			roleRepository.save(role);
		}else {
			role = roleOpt.get(); // caso a role esteja persistida no DB, será usada para
			// ser associada ao perfil do user
		}
						
		// atribuir a role adequada ao perfil do user
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		
		// salvar o usuario no db
		userRepository.save(user);
		System.out.println("Usuario salvo com sucesso: " + user.getEmail());		
		
	} // aqui, fecha o método saveUser
	
	/*
	 =================================================================================================
	 	NESTE PASSO, VAMOS "CARREGAR/BUSCAR" UM USUARIO PELO SEU EMAIL/username
	 =================================================================================================
	 */
	
	// vamos fazer uso de recursos do Spring Security
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		
		// verificar o valor da variavel user
		if(user == null) {
			throw new UsernameNotFoundException("Email do usuário não encontrado: " + email);
		}
		
		return new org.springframework.security.core.userdetails.User(
					user.getEmail(),
					user.getPassword(),
					getUserAuthority(user.getRoles())
				);
	}// fecha o método loadUserByUsername()
	
	// definir o método getUserAuthority()
	// a utilização do método getUserAuthority() é necessário porque precisamos aplicar aos roles
	// um processo de conversão - de simples "papeis" de usuario para uma contexto de autoridade
	// ou seja, os roles precisam ser convertidos em elementos authorities do Spring Security - 
	// pois somente passando por est aconversão, estas roles serão entendidas como niveis de 
	
	// acesso/ restrições de uso do sistema por usuarios com determinadas classificações
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
		
		Set<GrantedAuthority> roles = new HashSet<>();
		
		userRoles.forEach((role) -> {
			roles.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())); // usando o prefixo ROLE_
		});
		
		return new ArrayList<>(roles);
	}
	
	
}
	

