package com.biblioteca.v1.proyectobiblioteca.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/global/login", "/user/register", "/css/**", "/js/**").permitAll()

                        // Rutas accesibles por el rol ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Rutas accesibles por el rol USER
                        .requestMatchers("/user/**", "/global/index").hasRole("USER")

                        // Rutas accesibles por el rol LIBRARIAN
                        .requestMatchers("/librarian/**").hasRole("LIBRARIAN")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())
                // Configuración de login
                .formLogin(form -> form
                        .loginPage("/global/login") // Página de login personalizada
                        .successHandler(customAuthenticationSuccessHandler()) // Redirección según el rol del usuario
                        .permitAll())
                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/global/login?logout=true") // Agrega parámetro de logout
                        .permitAll())
                .build();
    }

    // Manejador de éxito de autenticación personalizado
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    response.sendRedirect("/admin/dashboard"); // Ajusta la URL según tu diseño
                } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
                    response.sendRedirect("/librarian/list-books"); // Redirige a la página principal del bibliotecario
                } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                    response.sendRedirect("/global/index"); // Redirige a la página principal del usuario
                }
            }
        };
    }

    // Codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                .collect(Collectors.toList())))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
