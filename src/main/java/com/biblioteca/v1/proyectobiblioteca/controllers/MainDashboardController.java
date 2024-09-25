package com.biblioteca.v1.proyectobiblioteca.controllers;

import com.biblioteca.v1.proyectobiblioteca.services.BookService;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;
import com.biblioteca.v1.proyectobiblioteca.services.UserService;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainDashboardController {

    private final BookService bookService;
    private final LoanService loanService;
    private final UserService userService;

    @Autowired
    public MainDashboardController(BookService bookService, LoanService loanService, UserService userService) {
        this.bookService = bookService;
        this.loanService = loanService;
        this.userService = userService;
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        // 1. Obtener el número total de libros
        long totalBooks = bookService.findAll().size();

        // 2. Obtener el número total de usuarios
        long totalUsers = userService.findAll().size();

        // 3. Obtener el número total de préstamos activos
        long activeLoans = loanService.findByStatus(Loan.LoanStatus.ACCEPTED).size();

        // 4. Obtener el número total de solicitudes de préstamos pendientes
        long pendingLoans = loanService.findByStatus(Loan.LoanStatus.PENDING).size();

        // Añadir las estadísticas al modelo para mostrarlas en la vista
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("activeLoans", activeLoans);
        model.addAttribute("pendingLoans", pendingLoans);

        // Devolver la vista del dashboard del administrador
        return "admin/dashboard";
    }
}
