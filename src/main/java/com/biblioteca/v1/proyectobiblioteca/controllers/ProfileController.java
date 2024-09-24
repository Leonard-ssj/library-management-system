package com.biblioteca.v1.proyectobiblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.v1.proyectobiblioteca.exceptions.UserNotFoundException;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import com.biblioteca.v1.proyectobiblioteca.services.UserService;

@Controller
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile/actualizar")
    public String updateProfile(@RequestParam("email") String email,
            @RequestParam("username") String username,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
            String currentUsername = currentUserDetails.getUsername();

            User user = userService.findByUsername(currentUsername)
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

            // Validar si el nombre de usuario es válido
            if (username == null || username.trim().isEmpty()) {
                model.addAttribute("usernameError", "El nombre de usuario no puede estar vacío.");
                model.addAttribute("user", user);
                return "user/profile";
            }

            // Validar que el username contenga al menos 5 letras y 1 número
            if (!username.matches("^(?=.*[a-zA-Z]{5,})(?=.*\\d)[a-zA-Z0-9]{6,30}$")) {
                model.addAttribute("usernameError",
                        "El nombre de usuario debe tener al menos 6 caracteres, contener al menos 5 letras y 1 número, y solo letras y números.");
                model.addAttribute("user", user);
                return "user/profile";
            }

            // Validar si el email es válido
            if (email == null || email.trim().isEmpty()) {
                model.addAttribute("emailError", "El correo electrónico no puede estar vacío.");
                model.addAttribute("user", user);
                return "user/profile";
            }

            boolean usernameExists = userService.findByUsername(username).isPresent();
            boolean emailExists = userService.findByEmail(email).isPresent();

            if (usernameExists && !username.equals(currentUsername)) {
                model.addAttribute("usernameError", "El nombre de usuario ya está en uso.");
                model.addAttribute("user", user);
                return "user/profile";
            }

            if (emailExists && !email.equals(user.getEmail())) {
                model.addAttribute("emailError", "El correo electrónico ya está en uso.");
                model.addAttribute("user", user);
                return "user/profile";
            }

            user.setEmail(email);
            user.setUsername(username);

            userService.saveUser(user);

            // Actualizar la autenticación con los nuevos detalles del usuario
            Authentication newAuth = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            return "redirect:/profile";
        } catch (UserNotFoundException e) {
            model.addAttribute("error", "Error al encontrar el usuario: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar la información del perfil: " + e.getMessage());
        }

        User updatedUser = userService.findByUsername(authentication.getName()).orElse(new User());
        model.addAttribute("user", updatedUser);
        return "user/profile";
    }

}
