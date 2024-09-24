package com.biblioteca.v1.proyectobiblioteca.servicesImpl;

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


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void registerNewUser(CreateUserDTO userDTO) throws Exception {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new Exception("Nombre de usuario ya existe.");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new Exception("Email ya está en uso.");
        }

        // Codificar la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        // Crear la entidad User a partir del DTO
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(encodedPassword) // Contraseña codificada
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .lastName(userDTO.getLastName())
                .roles(Set.of(getDefaultUserRole())) // Asignar el rol USER por defecto
                .build();

        userRepository.save(user); // Guardar el usuario en la base de datos
    }

    private Role getDefaultUserRole() throws Exception {
        ERole eRole = ERole.USER; // Rol por defecto
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new Exception("Rol " + eRole + " no encontrado"));
    }

}
