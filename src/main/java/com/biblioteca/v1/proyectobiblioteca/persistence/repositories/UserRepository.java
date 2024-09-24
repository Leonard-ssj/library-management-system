package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);//Encuentra un usuario por su nombre de usuario.
    Optional<User> findByEmail(String email);//Encuentra un usuario por su correo electrónico.
    
}
