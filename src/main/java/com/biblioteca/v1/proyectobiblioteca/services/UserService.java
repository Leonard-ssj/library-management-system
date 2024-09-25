package com.biblioteca.v1.proyectobiblioteca.services;

import java.util.List;
import java.util.Optional;

import com.biblioteca.v1.proyectobiblioteca.dto.CreateUserDTO;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;

public interface UserService {
    User saveUser(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void deleteUser(Long id);
    void registerNewUser(CreateUserDTO userDTO) throws Exception; // Método para registrar un nuevo usuario
    List<User> findAll(); // Método para obtener todos los usuarios
    
}
