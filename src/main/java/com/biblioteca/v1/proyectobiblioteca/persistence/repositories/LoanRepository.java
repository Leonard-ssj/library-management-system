package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;

/**
 * Repositorio para la entidad {@link Loan}.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas,
 * así como métodos personalizados para buscar préstamos por usuario, libro, estado y fechas.
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * Encuentra todos los préstamos asociados a un usuario específico.
     * 
     * @param userId el identificador del usuario.
     * @return una lista de préstamos asociados al usuario indicado.
     */
    List<Loan> findByUserId(Long userId); // Encuentra todos los préstamos asociados a un usuario específico

    /**
     * Encuentra todos los préstamos asociados a un libro específico.
     * 
     * @param bookId el identificador del libro.
     * @return una lista de préstamos asociados al libro indicado.
     */
    List<Loan> findByBookId(Long bookId); // Encuentra todos los préstamos asociados a un libro específico

    /**
     * Encuentra todos los préstamos con un estado específico.
     * 
     * @param status el estado del préstamo a buscar.
     * @return una lista de préstamos con el estado indicado.
     */
    List<Loan> findByStatus(Loan.LoanStatus status); // Encuentra préstamos con un estado específico

    /**
     * Encuentra un préstamo de un usuario para un libro específico con un estado dado.
     * 
     * @param user el usuario que solicitó el préstamo.
     * @param book el libro prestado.
     * @param status el estado del préstamo.
     * @return un {@link Optional<Loan>} que contiene el préstamo si existe, o vacío si no se encuentra.
     */
    Optional<Loan> findByUserAndBookAndStatus(User user, Book book, Loan.LoanStatus status); 
    // Encuentra un préstamo de un usuario para un libro específico con un estado dado

    /**
     * (Opcional) Encuentra préstamos atrasados basados en la fecha de devolución.
     * 
     * @param user el usuario que solicitó el préstamo.
     * @param status el estado del préstamo.
     * @param returnDate la fecha de devolución (se usa para encontrar préstamos que están atrasados).
     * @return una lista de préstamos que cumplen con los criterios de usuario, estado y fecha de devolución antes de la fecha indicada.
     */
    List<Loan> findByUserAndStatusAndReturnDateBefore(User user, Loan.LoanStatus status, LocalDate returnDate); 
    // (Opcional) Encuentra préstamos atrasados basados en returnDate
}
