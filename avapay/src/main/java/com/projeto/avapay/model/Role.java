package com.projeto.avapay.model;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    
    @Column(name = "role_name",nullable = false, unique = true)
    private String roleName;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();
  


	public Role() {
		
	}


	public Role(String roleName) {
		this.roleName = roleName;
	}



	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getRoleName() {
		return roleName;
	}




	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}






}