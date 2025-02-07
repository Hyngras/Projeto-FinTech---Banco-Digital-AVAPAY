package com.projeto.avapay.model;
 
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "user_name", nullable = false)
    private String name;
 
    @Column(nullable = false, unique = true)
    private String cpf;
 
 
    @Column(nullable = false, unique = true)
    private String email;
 
    @Column(nullable = false, unique = true)
    private String phone;
 
    @Column(name = "creation_date",nullable = false)
    private LocalDateTime creationDate;
 
    @Column(nullable = false)
    private String password;
 
    @Column(nullable = false)
    private boolean active = true;

    

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();



    public Set<String> getRoleNames() {
        Set<String> roleNames = new HashSet<>();
        for (Role role : this.roles) {
            roleNames.add(role.getRoleName());  // Adiciona o nome da role no Set
        }
        return roleNames;
    }
 
	
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getCpf() {
		return cpf;
	}
 
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
 
 
	public String getEmail() {
		return email;
	}
 
	public void setEmail(String email) {
		this.email = email;
	}
 
	public String getPhone() {
		return phone;
	}
 
	public void setPhone(String phone) {
		this.phone = phone;
	}
 
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
 
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	
 
 
 
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}
 
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}

	public User orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}



 
 
    

}