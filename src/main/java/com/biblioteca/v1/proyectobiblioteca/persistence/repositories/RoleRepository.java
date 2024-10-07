package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.ERole;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Role;

/**
 * Repositorio para la entidad {@link Role}.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas,
 * y un método personalizado para buscar roles por su nombre (ERole).
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Encuentra un rol por su nombre.
     * 
     * @param name el nombre del rol, representado como un valor de {@link ERole}.
     * @return un {@link Optional<Role>} que contiene el rol si existe, o vacío si no se encuentra.
     */
    Optional<Role> findByName(ERole name); // Cambiar el tipo del argumento a ERole
}
