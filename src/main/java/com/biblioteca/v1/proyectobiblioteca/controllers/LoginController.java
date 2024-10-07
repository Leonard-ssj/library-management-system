package com.biblioteca.v1.proyectobiblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Método para mostrar el formulario de inicio de sesión
    @GetMapping("/global/login")
    public String showLoginForm() {
        // Retorna el nombre de la vista para la página de login
        return "global/login";
    }
}
