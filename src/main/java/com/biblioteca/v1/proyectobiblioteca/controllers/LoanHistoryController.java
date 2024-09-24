package com.biblioteca.v1.proyectobiblioteca.controllers;

import com.biblioteca.v1.proyectobiblioteca.services.LoanService;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoanHistoryController {

    private final LoanService loanService;

    @Autowired
    public LoanHistoryController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/librarian/loan-history")
    public String showLoanHistory(Model model) {
        // Buscar todos los préstamos con estado "RETURNED"
        List<Loan> returnedLoans = loanService.findByStatus(Loan.LoanStatus.RETURNED);
        model.addAttribute("returnedLoans", returnedLoans);
        return "librarian/loan-history";
    }
}
