package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Role;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(ERole name); // Cambiar el tipo del argumento a ERole
}
