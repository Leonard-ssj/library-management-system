package com.biblioteca.v1.proyectobiblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/global/login")
    public String showLoginForm() {
        return "global/login"; // Retorna el nombre de la vista para la página de login
    }
}
