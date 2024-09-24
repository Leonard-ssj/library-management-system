package com.biblioteca.v1.proyectobiblioteca.controllers;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan.LoanDuration;
import com.biblioteca.v1.proyectobiblioteca.services.BookService;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;
import com.biblioteca.v1.proyectobiblioteca.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

@Controller
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    // Mostrar la vista de solicitud de préstamo
    // Mostrar la vista de solicitud de préstamo
    @GetMapping("/user/apply-for-a-loan/{bookId}")
    public String mostrarFormularioPrestamo(@PathVariable Long bookId, Model model, Authentication authentication,
            RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/error";
        }

        User user = optionalUser.get();
        Book book = bookService.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        // Validar si el usuario tiene un préstamo aceptado o retrasado
        Optional<Loan> activeLoan = loanService.findActiveLoanForUserAndBook(user, book);
        if (activeLoan.isPresent() && activeLoan.get().getStatus() == Loan.LoanStatus.ACCEPTED) {
            redirectAttributes.addFlashAttribute("error",
                    "Tienes este libro en préstamo. Por favor devuélvelo para solicitar nuevamente.");
            return "redirect:/user/my-loans";
        }

        // Validar si hay un préstamo pendiente
        Optional<Loan> existingLoan = loanService.findPendingLoanForUserAndBook(user, book);
        if (existingLoan.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya tienes un préstamo pendiente para este libro, espera a que el bibliotecario acepte tu solicitud.");
            return "redirect:/user/my-loans";
        }

        // Validar si hay un libro en estado overdue
        List<Loan> overdueLoans = loanService.findOverdueLoanForUser(user);
        if (!overdueLoans.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tienes un libro atrasado: "
                    + overdueLoans.get(0).getBook().getTitle() + ". Devuélvelo para poder solicitar más préstamos.");
            return "redirect:/user/my-loans";
        }

        model.addAttribute("loan", new Loan());
        model.addAttribute("book", book);
        model.addAttribute("user", user);
        model.addAttribute("durations", LoanDuration.values());

        return "user/apply-for-a-loan";
    }

    // Procesar la solicitud de préstamo
    @PostMapping("/loans")
    public String solicitarPrestamo(@ModelAttribute("loan") Loan loan, Authentication authentication,
            RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/error";
        }

        User user = optionalUser.get();
        Book book = loan.getBook();

        // Validar si ya hay un préstamo pendiente para este libro
        Optional<Loan> existingLoan = loanService.findPendingLoanForUserAndBook(user, book);
        if (existingLoan.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya tienes un préstamo pendiente para este libro.");
            return "redirect:/user/my-loans";
        }

        // Validar si el usuario tiene un préstamo aceptado o overdue
        Optional<Loan> activeLoan = loanService.findActiveLoanForUserAndBook(user, book);
        if (activeLoan.isPresent() && activeLoan.get().getStatus() == Loan.LoanStatus.ACCEPTED) {
            redirectAttributes.addFlashAttribute("error",
                    "Tienes este libro en préstamo. Por favor devuélvelo para solicitar nuevamente.");
            return "redirect:/user/my-loans";
        }

        List<Loan> overdueLoans = loanService.findOverdueLoanForUser(user);
        if (!overdueLoans.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tienes un libro atrasado: "
                    + overdueLoans.get(0).getBook().getTitle() + ". Devuélvelo para poder solicitar más préstamos.");
            return "redirect:/user/my-loans";
        }

        // Establecer fecha de solicitud y usuario
        loan.setRequestDate(LocalDate.now());
        loan.setUser(user);
        loan.setStatus(Loan.LoanStatus.PENDING);

        // Calcular y establecer la fecha de préstamo
        LocalDate loanDate = loan.getRequestDate().plusDays(loan.getDuration().getDays());
        loan.setLoanDate(loanDate);

        // Guardar el préstamo
        loanService.saveLoan(loan);

        redirectAttributes.addFlashAttribute("success", "Solicitud de préstamo enviada con éxito.");
        return "redirect:/global/index";
    }

}
