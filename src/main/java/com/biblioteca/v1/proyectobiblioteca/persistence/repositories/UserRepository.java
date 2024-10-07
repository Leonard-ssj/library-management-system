package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;

/**
 * Repositorio para la entidad {@link User}.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y métodos personalizados para encontrar usuarios por nombre de usuario o correo electrónico.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Encuentra un usuario por su nombre de usuario.
     * 
     * @param username el nombre de usuario.
     * @return un {@link Optional<User>} que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<User> findByUsername(String username); // Encuentra un usuario por su nombre de usuario.

    /**
     * Encuentra un usuario por su correo electrónico.
     * 
     * @param email el correo electrónico del usuario.
     * @return un {@link Optional<User>} que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<User> findByEmail(String email); // Encuentra un usuario por su correo electrónico.
}
