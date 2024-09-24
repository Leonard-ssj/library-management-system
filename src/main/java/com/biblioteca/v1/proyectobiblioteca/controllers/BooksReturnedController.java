package com.biblioteca.v1.proyectobiblioteca.controllers;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BooksReturnedController {

    private final LoanService loanService;

    @Autowired
    public BooksReturnedController(LoanService loanService) {
        this.loanService = loanService;
    }

    // Método para listar los préstamos en estado ACCEPTED y OVERDUE
    @GetMapping("/librarian/books-returns")
    public String showReturnsPage(Model model) {
        // Actualiza los préstamos vencidos
        List<Loan> overdueLoans = loanService.findByStatus(Loan.LoanStatus.ACCEPTED);
        overdueLoans.forEach(loan -> {
            // Verifica si returnDate no es null antes de comparar fechas
            if (loan.getReturnDate() != null && loan.getReturnDate().isBefore(LocalDate.now())) {
                loan.setStatus(Loan.LoanStatus.OVERDUE);
                loanService.saveLoan(loan);
            }
        });

        // Obtener todos los préstamos en estado ACCEPTED y OVERDUE
        List<Loan> acceptedLoans = loanService.findByStatus(Loan.LoanStatus.ACCEPTED);
        List<Loan> overdueLoansList = loanService.findByStatus(Loan.LoanStatus.OVERDUE);

        model.addAttribute("acceptedLoans", acceptedLoans);
        model.addAttribute("overdueLoans", overdueLoansList);

        return "librarian/books-returns"; // Renderiza la vista books-returns.html
    }

    // Método para marcar un libro como devuelto
    @PostMapping("/return-book")
    public String returnBook(@RequestParam("loanId") Long loanId) {
        loanService.findById(loanId).ifPresent(loan -> {
            // Asigna la fecha de devolución actual
            loan.setReturnDate(LocalDate.now());

            // Cambia el estado del préstamo a RETURNED
            loan.setStatus(Loan.LoanStatus.RETURNED);

            // Incrementa la cantidad de copias disponibles del libro
            loan.getBook().setAvailableQuantity(loan.getBook().getAvailableQuantity() + 1);

            // Guarda el préstamo actualizado
            loanService.saveLoan(loan);
        });

        return "redirect:/librarian/books-returns";
    }
}
