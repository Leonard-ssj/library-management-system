package com.biblioteca.v1.proyectobiblioteca.services;

import java.util.List;
import java.util.Optional;

import com.biblioteca.v1.proyectobiblioteca.dto.CreateUserDTO;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;

/**
 * Servicio para manejar las operaciones relacionadas con la entidad {@link User}.
 * Proporciona métodos para la gestión de usuarios, incluyendo su creación, búsqueda, eliminación y registro.
 */
public interface UserService {

    /**
     * Guarda un usuario en la base de datos.
     * 
     * @param user el usuario a guardar.
     * @return el usuario guardado.
     */
    User saveUser(User user);

    /**
     * Busca un usuario por su identificador.
     * 
     * @param id el identificador del usuario.
     * @return un {@link Optional<User>} que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<User> findById(Long id);

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username el nombre de usuario.
     * @return un {@link Optional<User>} que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email el correo electrónico.
     * @return un {@link Optional<User>} que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<User> findByEmail(String email);

    /**
     * Elimina un usuario de la base de datos por su identificador.
     * 
     * @param id el identificador del usuario a eliminar.
     */
    void deleteUser(Long id);

    /**
     * Registra un nuevo usuario.
     * 
     * @param userDTO el objeto DTO que contiene la información del nuevo usuario.
     * @throws Exception si ocurre un error durante el registro.
     */
    void registerNewUser(CreateUserDTO userDTO) throws Exception;

    /**
     * Devuelve una lista de todos los usuarios.
     * 
     * @return una lista de {@link User} que contiene todos los usuarios.
     */
    List<User> findAll();
}
