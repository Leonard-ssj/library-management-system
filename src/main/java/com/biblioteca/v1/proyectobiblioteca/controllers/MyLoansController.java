// MyLoansController.java
package com.biblioteca.v1.proyectobiblioteca.controllers;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;
import com.biblioteca.v1.proyectobiblioteca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MyLoansController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/my-loans")
    public String mostrarMisPrestamos(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Actualizar el estado de los préstamos vencidos
        loanService.updateOverdueLoans(user);

        // Obtener todos los préstamos del usuario
        List<Loan> loans = loanService.findLoansByUser(user);
        model.addAttribute("loans", loans);

        // Mensajes de éxito o error si existen
        if (redirectAttributes.getFlashAttributes().containsKey("error")) {
            model.addAttribute("error", redirectAttributes.getFlashAttributes().get("error"));
        }
        if (redirectAttributes.getFlashAttributes().containsKey("success")) {
            model.addAttribute("success", redirectAttributes.getFlashAttributes().get("success"));
        }

        // Agregar formateador de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        model.addAttribute("dateFormatter", formatter);

        return "user/my-loans"; // Retorna la vista de Mis Préstamos
    }
}