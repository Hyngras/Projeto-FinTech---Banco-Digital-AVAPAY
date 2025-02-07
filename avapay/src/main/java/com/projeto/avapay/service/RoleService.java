package com.projeto.avapay.service;
 
import com.projeto.avapay.model.Role;
import com.projeto.avapay.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
import java.util.Set;
 
@Service
public class RoleService {
 
    @Autowired
    private RoleRepository roleRepository;
 
    // Método para encontrar roles por IDs
    public Set<Role> findRolesByIds(Set<Long> roleIds) {
        return Set.copyOf(roleRepository.findAllById(roleIds));
    }
 
    // Método para buscar todos os papéis
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
 
    // Método para buscar um papel pelo seu nome
    public Optional<Role> findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName); // Aqui chama o findByName do RoleRepository
    }
 
    // Método para criar um novo papel
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
 
    // Método para atualizar um papel existente
    public Role updateRole(Long roleId, Role updatedRole) {
        if (roleRepository.existsById(roleId)) {
            updatedRole.setId(roleId);
            return roleRepository.save(updatedRole);
        }
        return null; // Ou você pode lançar uma exceção personalizada
    }
 
    // Método para deletar um papel
    public void deleteRole(Long roleId) {
        if (roleRepository.existsById(roleId)) {
            roleRepository.deleteById(roleId);
        }
    }
}

