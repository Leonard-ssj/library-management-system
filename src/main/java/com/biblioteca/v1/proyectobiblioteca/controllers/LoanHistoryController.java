package com.biblioteca.v1.proyectobiblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;

@Controller
public class LoanHistoryController {

    // Servicio que maneja la lógica de los préstamos
    private final LoanService loanService;

    // Constructor para la inyección de dependencias
    @Autowired
    public LoanHistoryController(LoanService loanService) {
        this.loanService = loanService;
    }

    // Endpoint para mostrar el historial de préstamos que han sido devueltos
    @GetMapping("/librarian/loan-history")
    public String showLoanHistory(Model model) {
        // Buscar todos los préstamos con estado "RETURNED" (devueltos)
        List<Loan> returnedLoans = loanService.findByStatus(Loan.LoanStatus.RETURNED);

        // Agregar los préstamos devueltos al modelo para pasarlos a la vista
        model.addAttribute("returnedLoans", returnedLoans);

        // Retornar la vista correspondiente al historial de préstamos
        return "librarian/loan-history";
    }
}
