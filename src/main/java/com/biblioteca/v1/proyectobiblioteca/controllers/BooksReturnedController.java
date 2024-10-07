package com.biblioteca.v1.proyectobiblioteca.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;

@Controller
public class BooksReturnedController {

    // Servicio que se utiliza para interactuar con los préstamos en la base de datos
    private final LoanService loanService;

    // Constructor para inyectar la dependencia del servicio LoanService
    @Autowired
    public BooksReturnedController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Muestra la página de devoluciones de libros.
     * Este método se encarga de actualizar los préstamos que están vencidos.
     * 
     * @param model El modelo que se pasa a la vista.
     * @return La vista "librarian/books-returns" con los préstamos aceptados y vencidos.
     */
    @GetMapping("/librarian/books-returns")
    public String showReturnsPage(Model model) {
        // Obtiene los préstamos que están en estado "ACCEPTED"
        List<Loan> overdueLoans = loanService.findByStatus(Loan.LoanStatus.ACCEPTED);

        // Itera sobre los préstamos en estado "ACCEPTED" y los actualiza si están vencidos
        overdueLoans.forEach(loan -> {
            // Verifica si la fecha de devolución no es null antes de comparar las fechas
            if (loan.getReturnDate() != null && loan.getReturnDate().isBefore(LocalDate.now())) {
                // Si la fecha de devolución es anterior a la fecha actual, el préstamo se considera vencido
                loan.setStatus(Loan.LoanStatus.OVERDUE);
                loanService.saveLoan(loan); // Guarda el préstamo actualizado en la base de datos
            }
        });

        // Obtiene la lista de préstamos en estado "ACCEPTED" y "OVERDUE"
        List<Loan> acceptedLoans = loanService.findByStatus(Loan.LoanStatus.ACCEPTED);
        List<Loan> overdueLoansList = loanService.findByStatus(Loan.LoanStatus.OVERDUE);

        // Añade las listas de préstamos aceptados y vencidos al modelo para que estén disponibles en la vista
        model.addAttribute("acceptedLoans", acceptedLoans);
        model.addAttribute("overdueLoans", overdueLoansList);

        // Renderiza la vista "librarian/books-returns"
        return "librarian/books-returns";
    }

    /**
     * Marca un libro como devuelto en el sistema.
     * Este método cambia el estado del préstamo a "RETURNED" y actualiza la cantidad de libros disponibles.
     * 
     * @param loanId El identificador del préstamo que se va a marcar como devuelto.
     * @return Redirige a la página de devoluciones de libros.
     */
    @PostMapping("/return-book")
    public String returnBook(@RequestParam("loanId") Long loanId) {
        // Busca el préstamo en la base de datos utilizando el loanId proporcionado
        loanService.findById(loanId).ifPresent(loan -> {
            // Asigna la fecha de devolución actual al préstamo
            loan.setReturnDate(LocalDate.now());

            // Cambia el estado del préstamo a "RETURNED" (devuelto)
            loan.setStatus(Loan.LoanStatus.RETURNED);

            // Incrementa la cantidad de copias disponibles del libro asociado a este préstamo
            loan.getBook().setAvailableQuantity(loan.getBook().getAvailableQuantity() + 1);

            // Guarda el préstamo actualizado en la base de datos
            loanService.saveLoan(loan);
        });

        // Redirige de nuevo a la página de devoluciones de libros
        return "redirect:/librarian/books-returns";
    }
}
