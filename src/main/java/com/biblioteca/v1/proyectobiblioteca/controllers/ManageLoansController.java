package com.biblioteca.v1.proyectobiblioteca.controllers;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ManageLoansController {

    @Autowired
    private LoanService loanService;

    // Mostrar todas las solicitudes pendientes
    @GetMapping("/librarian/manage-loans")
    public String mostrarSolicitudesPrestamos(Model model) {
        List<Loan> pendingLoans = loanService.findByStatus(Loan.LoanStatus.PENDING);
        model.addAttribute("pendingLoans", pendingLoans);
        return "librarian/manage-loans"; // Página de gestión de préstamos
    }

    // Aceptar solicitud de préstamo
    @PostMapping("/librarian/accept-loan/{loanId}")
    public String aceptarPrestamo(@PathVariable Long loanId, Model model) {
        try {
            loanService.acceptLoan(loanId); // Cambia el estado del préstamo a ACCEPTED
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage()); // Agrega el mensaje de error al modelo
            return "librarian/manage-loans";
        }
        return "redirect:/librarian/manage-loans";
    }

    // Rechazar solicitud de préstamo
    @PostMapping("/librarian/reject-loan/{loanId}")
    public String rechazarPrestamo(@PathVariable Long loanId) {
        loanService.rejectLoan(loanId); // Cambia el estado del préstamo a REJECTED
        return "redirect:/librarian/manage-loans";
    }
}
