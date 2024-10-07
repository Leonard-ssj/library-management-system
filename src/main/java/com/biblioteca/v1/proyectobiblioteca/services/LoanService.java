package com.biblioteca.v1.proyectobiblioteca.services;

import java.util.List;
import java.util.Optional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;

/**
 * Servicio para manejar las operaciones relacionadas con la entidad {@link Loan}.
 * Proporciona métodos para la gestión de préstamos de libros, incluyendo la creación, búsqueda, eliminación y actualización de préstamos.
 */
public interface LoanService {

    /**
     * Guarda un préstamo en la base de datos.
     * 
     * @param loan el préstamo a guardar.
     * @return el préstamo guardado.
     */
    Loan saveLoan(Loan loan);

    /**
     * Busca un préstamo por su identificador.
     * 
     * @param id el identificador del préstamo.
     * @return un {@link Optional<Loan>} que contiene el préstamo si se encuentra, o vacío si no existe.
     */
    Optional<Loan> findById(Long id);

    /**
     * Devuelve una lista de todos los préstamos.
     * 
     * @return una lista de {@link Loan} que contiene todos los préstamos.
     */
    List<Loan> findAll();

    /**
     * Elimina un préstamo de la base de datos por su identificador.
     * 
     * @param id el identificador del préstamo a eliminar.
     */
    void deleteLoan(Long id);

    /**
     * Encuentra todos los préstamos asociados a un usuario específico.
     * 
     * @param userId el identificador del usuario.
     * @return una lista de {@link Loan} que contiene los préstamos del usuario.
     */
    List<Loan> findByUserId(Long userId);

    /**
     * Encuentra todos los préstamos asociados a un libro específico.
     * 
     * @param bookId el identificador del libro.
     * @return una lista de {@link Loan} que contiene los préstamos de un libro.
     */
    List<Loan> findByBookId(Long bookId);

    /**
     * Encuentra préstamos con un estado específico.
     * 
     * @param status el estado del préstamo.
     * @return una lista de {@link Loan} que contiene los préstamos con el estado especificado.
     */
    List<Loan> findByStatus(Loan.LoanStatus status);

    /**
     * Acepta un préstamo.
     * 
     * @param loanId el identificador del préstamo a aceptar.
     */
    void acceptLoan(Long loanId);

    /**
     * Rechaza un préstamo.
     * 
     * @param loanId el identificador del préstamo a rechazar.
     */
    void rejectLoan(Long loanId);

    /**
     * Encuentra un préstamo pendiente de un usuario para un libro específico.
     * 
     * @param user el usuario que realizó el préstamo.
     * @param book el libro para el que se solicitó el préstamo.
     * @return un {@link Optional<Loan>} que contiene el préstamo pendiente, o vacío si no existe.
     */
    Optional<Loan> findPendingLoanForUserAndBook(User user, Book book);

    /**
     * Verifica si un libro está disponible para préstamo.
     * 
     * @param book el libro a verificar.
     * @return true si el libro está disponible, false en caso contrario.
     */
    boolean isBookAvailable(Book book);

    /**
     * Encuentra un préstamo activo de un usuario para un libro específico.
     * 
     * @param user el usuario que tiene el préstamo.
     * @param book el libro prestado.
     * @return un {@link Optional<Loan>} que contiene el préstamo activo, o vacío si no existe.
     */
    Optional<Loan> findActiveLoanForUserAndBook(User user, Book book);

    /**
     * Encuentra los préstamos atrasados de un usuario.
     * 
     * @param user el usuario que tiene los préstamos.
     * @return una lista de {@link Loan} que contiene los préstamos atrasados del usuario.
     */
    List<Loan> findOverdueLoanForUser(User user);

    /**
     * Encuentra todos los préstamos de un usuario.
     * 
     * @param user el usuario que tiene los préstamos.
     * @return una lista de {@link Loan} que contiene todos los préstamos del usuario.
     */
    List<Loan> findLoansByUser(User user);

    /**
     * Actualiza los préstamos atrasados de un usuario.
     * 
     * @param user el usuario cuyas deudas de préstamos deben actualizarse.
     */
    void updateOverdueLoans(User user);




    
}
