package com.biblioteca.v1.proyectobiblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.biblioteca.v1.proyectobiblioteca.dto.CreateUserDTO;
import com.biblioteca.v1.proyectobiblioteca.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("createUserDTO", new CreateUserDTO());
        return "user/register"; // La vista de registro
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("createUserDTO") CreateUserDTO createUserDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        // Validar si el nombre de usuario o el email ya existen en la base de datos
        if (userService.findByUsername(createUserDTO.getUsername()).isPresent()) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "user/register";
        }

        if (userService.findByEmail(createUserDTO.getEmail()).isPresent()) {
            model.addAttribute("error", "El correo electrónico ya está en uso.");
            return "user/register";
        }

        try {
            userService.registerNewUser(createUserDTO);
            return "redirect:/login"; // Redirigir al login después del registro
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "user/register";
        }
    }
}
