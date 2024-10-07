package com.biblioteca.v1.proyectobiblioteca.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan.LoanDuration;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import com.biblioteca.v1.proyectobiblioteca.services.BookService;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;
import com.biblioteca.v1.proyectobiblioteca.services.UserService;

/**
 * Controlador encargado de manejar las solicitudes de préstamos de libros.
 * Permite que los usuarios soliciten préstamos, verifiquen el estado de los mismos
 * y gestionen los préstamos pendientes o atrasados.
 */
@Controller
public class LoanController {

    // Inyección de dependencias para los servicios de préstamos, libros y usuarios
    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    /**
     * Muestra el formulario de solicitud de préstamo para un libro específico.
     * 
     * @param bookId Id del libro que se desea prestar.
     * @param model Objeto para pasar datos a la vista.
     * @param authentication Información de autenticación del usuario.
     * @param redirectAttributes Para redirigir con mensajes de éxito o error.
     * @return La vista correspondiente al formulario de solicitud de préstamo.
     */
    @GetMapping("/user/apply-for-a-loan/{bookId}")
    public String mostrarFormularioPrestamo(@PathVariable Long bookId, Model model, Authentication authentication,
            RedirectAttributes redirectAttributes) {
        // Obtiene el nombre de usuario desde la autenticación
        String username = authentication.getName();

        // Obtiene el usuario autenticado por su nombre de usuario
        Optional<User> optionalUser = userService.findByUsername(username);

        // Verifica si el usuario existe
        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/error";
        }

        User user = optionalUser.get();
        // Obtiene el libro por su id
        Book book = bookService.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        // Verifica si el usuario ya tiene un préstamo aceptado para este libro
        Optional<Loan> activeLoan = loanService.findActiveLoanForUserAndBook(user, book);
        if (activeLoan.isPresent() && activeLoan.get().getStatus() == Loan.LoanStatus.ACCEPTED) {
            redirectAttributes.addFlashAttribute("error",
                    "Tienes este libro en préstamo. Por favor devuélvelo para solicitar nuevamente.");
            return "redirect:/user/my-loans";
        }

        // Verifica si ya existe una solicitud de préstamo pendiente
        Optional<Loan> existingLoan = loanService.findPendingLoanForUserAndBook(user, book);
        if (existingLoan.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya tienes un préstamo pendiente para este libro, espera a que el bibliotecario acepte tu solicitud.");
            return "redirect:/user/my-loans";
        }

        // Verifica si el usuario tiene libros atrasados
        List<Loan> overdueLoans = loanService.findOverdueLoanForUser(user);
        if (!overdueLoans.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tienes un libro atrasado: "
                    + overdueLoans.get(0).getBook().getTitle() + ". Devuélvelo para poder solicitar más préstamos.");
            return "redirect:/user/my-loans";
        }

        // Se pasan al modelo los datos necesarios para la vista
        model.addAttribute("loan", new Loan());
        model.addAttribute("book", book);
        model.addAttribute("user", user);
        model.addAttribute("durations", LoanDuration.values());

        // Retorna la vista del formulario de solicitud de préstamo
        return "user/apply-for-a-loan";
    }

    /**
     * Procesa la solicitud de préstamo de un libro.
     * 
     * @param loan Objeto Loan con la información del préstamo.
     * @param authentication Información de autenticación del usuario.
     * @param redirectAttributes Para redirigir con mensajes de éxito o error.
     * @return Redirige a la vista de la página de inicio si la solicitud es exitosa,
     *         o muestra un error en caso de fallo.
     */
    @PostMapping("/loans")
    public String solicitarPrestamo(@ModelAttribute("loan") Loan loan, Authentication authentication,
            RedirectAttributes redirectAttributes) {
        // Obtiene el nombre de usuario desde la autenticación
        String username = authentication.getName();

        // Obtiene el usuario autenticado por su nombre de usuario
        Optional<User> optionalUser = userService.findByUsername(username);

        // Verifica si el usuario existe
        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/error";
        }

        User user = optionalUser.get();
        Book book = loan.getBook();

        // Verifica si el usuario ya tiene un préstamo pendiente para este libro
        Optional<Loan> existingLoan = loanService.findPendingLoanForUserAndBook(user, book);
        if (existingLoan.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya tienes un préstamo pendiente para este libro.");
            return "redirect:/user/my-loans";
        }

        // Verifica si el usuario tiene un préstamo aceptado o atrasado
        Optional<Loan> activeLoan = loanService.findActiveLoanForUserAndBook(user, book);
        if (activeLoan.isPresent() && activeLoan.get().getStatus() == Loan.LoanStatus.ACCEPTED) {
            redirectAttributes.addFlashAttribute("error",
                    "Tienes este libro en préstamo. Por favor devuélvelo para solicitar nuevamente.");
            return "redirect:/user/my-loans";
        }

        // Verifica si el usuario tiene libros atrasados
        List<Loan> overdueLoans = loanService.findOverdueLoanForUser(user);
        if (!overdueLoans.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tienes un libro atrasado: "
                    + overdueLoans.get(0).getBook().getTitle() + ". Devuélvelo para poder solicitar más préstamos.");
            return "redirect:/user/my-loans";
        }

        // Establece la fecha de solicitud y el usuario asociado
        loan.setRequestDate(LocalDate.now());
        loan.setUser(user);
        loan.setStatus(Loan.LoanStatus.PENDING);

        // Calcula la fecha de préstamo basada en la duración seleccionada
        LocalDate loanDate = loan.getRequestDate().plusDays(loan.getDuration().getDays());
        loan.setLoanDate(loanDate);

        // Guarda el préstamo en la base de datos
        loanService.saveLoan(loan);

        // Mensaje de éxito al redirigir al usuario
        redirectAttributes.addFlashAttribute("success", "Solicitud de préstamo enviada con éxito.");
        return "redirect:/global/index";
    }
    
}
