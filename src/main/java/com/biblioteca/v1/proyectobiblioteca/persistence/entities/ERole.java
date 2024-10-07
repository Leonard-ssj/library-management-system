package com.biblioteca.v1.proyectobiblioteca.persistence.entities;

/**
 * La enumeración ERole define los diferentes roles que un usuario puede tener en el sistema.
 * Los roles permiten la gestión de permisos y el control de acceso a las diferentes funcionalidades
 * del sistema.
 */
public enum ERole {
    ADMIN,       // Administrador: Tiene acceso completo a todas las funcionalidades y configuración del sistema.
    LIBRARIAN,   // Bibliotecario: Puede gestionar libros y préstamos, pero no tiene acceso a la configuración del sistema.
    USER         // Usuario: Puede ver y solicitar préstamos de libros, pero no tiene acceso a la gestión del sistema.
}
