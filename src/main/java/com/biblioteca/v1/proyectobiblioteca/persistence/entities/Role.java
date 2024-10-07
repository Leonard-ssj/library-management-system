package com.biblioteca.v1.proyectobiblioteca.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase Role representa un rol de usuario en el sistema.
 * Utiliza JPA para mapear esta clase a la tabla 'roles' en la base de datos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único para el rol

    /**
     * El nombre del rol, representado por un valor de la enumeración ERole.
     * Utilizado para definir el tipo de rol del usuario (e.g., ADMIN, LIBRARIAN, USER).
     */
    @Enumerated(EnumType.STRING)
    private ERole name; // Rol del usuario (ADMIN, LIBRARIAN, USER)
}
