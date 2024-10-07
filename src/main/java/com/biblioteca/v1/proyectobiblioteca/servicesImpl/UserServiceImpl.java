package com.biblioteca.v1.proyectobiblioteca.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.v1.proyectobiblioteca.dto.CreateUserDTO;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.ERole;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Role;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.RoleRepository;
import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.UserRepository;
import com.biblioteca.v1.proyectobiblioteca.services.UserService;



@Service
@Transactional
public class UserServiceImpl implements UserService {

    // Inyección de dependencias: UserRepository para acceder a los datos de los usuarios,
    // RoleRepository para acceder a los roles, y PasswordEncoder para cifrar contraseñas.
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor con anotación @Autowired para la inyección automática de las dependencias.
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Guardar un usuario en la base de datos.
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Buscar un usuario por su ID.
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Buscar un usuario por su nombre de usuario.
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Buscar un usuario por su email.
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Eliminar un usuario por su ID.
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Registro de un nuevo usuario:
    // Verifica que el nombre de usuario y el email no existan en la base de datos.
    // Codifica la contraseña antes de almacenarla.
    // Asigna un rol por defecto (USER) al nuevo usuario.
    @Override
    public void registerNewUser(CreateUserDTO userDTO) throws Exception {
        // Verificar si el nombre de usuario ya existe en la base de datos.
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new Exception("Nombre de usuario ya existe.");
        }

        // Verificar si el email ya está en uso.
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new Exception("Email ya está en uso.");
        }

        // Codificar la contraseña antes de guardarla en la base de datos.
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        // Crear la entidad User a partir del DTO (Data Transfer Object) recibido.
        User user = User.builder()
                .username(userDTO.getUsername()) // Nombre de usuario
                .password(encodedPassword) // Contraseña codificada
                .email(userDTO.getEmail()) // Email del usuario
                .name(userDTO.getName()) // Nombre del usuario
                .lastName(userDTO.getLastName()) // Apellido del usuario
                .roles(Set.of(getDefaultUserRole())) // Asignar el rol USER por defecto
                .build();

        // Guardar el usuario en la base de datos.
        userRepository.save(user);
    }

    // Método para obtener el rol de usuario por defecto.
    // Si no se encuentra el rol, lanza una excepción.
    private Role getDefaultUserRole() throws Exception {
        ERole eRole = ERole.USER; // Rol por defecto
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new Exception("Rol " + eRole + " no encontrado"));
    }

    // Obtener todos los usuarios de la base de datos.
    @Override
    public List<User> findAll() {
        return userRepository.findAll();

        
    }
}
