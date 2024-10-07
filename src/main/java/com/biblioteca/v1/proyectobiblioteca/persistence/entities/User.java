package com.biblioteca.v1.proyectobiblioteca.persistence.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase User representa a un usuario en el sistema, quien puede tener roles específicos 
 * que le otorgan distintos permisos. Implementa la interfaz UserDetails de Spring Security 
 * para gestionar la autenticación y autorización de usuarios.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del usuario

    /**
     * Nombre de usuario del usuario, debe ser único y tener entre 6 y 30 caracteres.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 6, max = 30, message = "El nombre de usuario debe tener entre 6 y 30 caracteres, incluyendo letras y números")
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Contraseña del usuario, debe tener al menos 8 caracteres.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String password;

    /**
     * Correo electrónico del usuario, debe ser único y tener un formato válido.
     */
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true)
    @Size(max = 80)
    private String email;

    /**
     * Nombre del usuario.
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres.")
    private String name;

    /**
     * Apellido del usuario.
     */
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres.")
    private String lastName;

    /**
     * Lista de préstamos asociados al usuario.
     * Relación uno a muchos con la entidad Loan.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;

    /**
     * Relación muchos a muchos con la entidad Role.
     * Representa los roles que tiene el usuario, y se almacena en la tabla intermedia 'user_roles'.
     */
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    /**
     * Obtiene las autoridades (roles) del usuario. Se utiliza para la autorización.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : this.roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())); // Añade el prefijo ROLE_ al nombre del rol
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;  // Devuelve la contraseña del usuario
    }

    @Override
    public String getUsername() {
        return this.username;  // Devuelve el nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Indica que la cuenta no ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Indica que la cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Indica que las credenciales no han expirado
    }

    @Override
    public boolean isEnabled() {
        return true;  // Indica que la cuenta está habilitada
    }
}
