package com.biblioteca.v1.proyectobiblioteca.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.BookRepository;
import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.LoanRepository;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;

/**
 * Implementación del servicio {@link LoanService} para manejar operaciones relacionadas con los préstamos de libros.
 * Proporciona métodos para la gestión de préstamos, como guardarlos, buscarlos, aceptarlos, rechazarlos y actualizarlos según el estado.
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    /**
     * Constructor de la clase LoanServiceImpl.
     * 
     * @param loanRepository el repositorio de {@link Loan} que será utilizado para acceder a la base de datos.
     * @param bookRepository el repositorio de {@link Book} que será utilizado para manejar los libros relacionados con los préstamos.
     */
    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Guarda un préstamo en la base de datos.
     * 
     * @param loan el préstamo a guardar.
     * @return el préstamo guardado.
     */
    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    /**
     * Busca un préstamo por su identificador.
     * 
     * @param id el identificador del préstamo.
     * @return un {@link Optional<Loan>} que contiene el préstamo si se encuentra, o vacío si no existe.
     */
    @Override
    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    /**
     * Devuelve una lista de todos los préstamos almacenados en la base de datos.
     * 
     * @return una lista de {@link Loan} que contiene todos los préstamos.
     */
    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    /**
     * Elimina un préstamo de la base de datos por su identificador.
     * 
     * @param id el identificador del préstamo a eliminar.
     */
    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    /**
     * Busca todos los préstamos asociados a un usuario específico.
     * 
     * @param userId el identificador del usuario.
     * @return una lista de {@link Loan} que contiene todos los préstamos del usuario.
     */
    @Override
    public List<Loan> findByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    /**
     * Busca todos los préstamos asociados a un libro específico.
     * 
     * @param bookId el identificador del libro.
     * @return una lista de {@link Loan} que contiene todos los préstamos del libro.
     */
    @Override
    public List<Loan> findByBookId(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    /**
     * Busca préstamos que tengan un estado específico.
     * 
     * @param status el estado del préstamo.
     * @return una lista de {@link Loan} que contiene los préstamos con el estado especificado.
     */
    @Override
    public List<Loan> findByStatus(Loan.LoanStatus status) {
        return loanRepository.findByStatus(status);
    }

    /**
     * Acepta un préstamo, verificando que haya copias disponibles del libro.
     * Si el libro está disponible, cambia el estado del préstamo a "ACEPTADO" y reduce la cantidad disponible del libro en la base de datos.
     * 
     * @param loanId el identificador del préstamo.
     * @throws IllegalStateException si no hay copias disponibles del libro.
     */
    @Override
    public void acceptLoan(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            Loan existingLoan = loan.get();
            Book book = existingLoan.getBook();

            // Verifica si hay copias disponibles del libro antes de aceptar el préstamo
            if (isBookAvailable(book)) {
                existingLoan.setStatus(Loan.LoanStatus.ACCEPTED);

                // Reduce la cantidad disponible del libro
                book.setAvailableQuantity(book.getAvailableQuantity() - 1);
                bookRepository.save(book);

                loanRepository.save(existingLoan);
            } else {
                throw new IllegalStateException("No hay copias disponibles del libro solicitado.");
            }
        }
    }

    /**
     * Rechaza un préstamo, cambiando su estado a "RECHAZADO".
     * 
     * @param loanId el identificador del préstamo.
     */
    @Override
    public void rejectLoan(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            Loan existingLoan = loan.get();
            existingLoan.setStatus(Loan.LoanStatus.REJECTED);
            loanRepository.save(existingLoan);
        }
    }

    /**
     * Busca un préstamo pendiente específico para un usuario y un libro.
     * 
     * @param user el usuario que ha solicitado el préstamo.
     * @param book el libro solicitado.
     * @return un {@link Optional<Loan>} que contiene el préstamo si se encuentra, o vacío si no existe.
     */
    @Override
    public Optional<Loan> findPendingLoanForUserAndBook(User user, Book book) {
        return loanRepository.findByUserAndBookAndStatus(user, book, Loan.LoanStatus.PENDING);
    }

    /**
     * Verifica si un libro tiene copias disponibles para préstamo.
     * 
     * @param book el libro a verificar.
     * @return {@code true} si hay copias disponibles, {@code false} si no.
     */
    @Override
    public boolean isBookAvailable(Book book) {
        return book.getAvailableQuantity() > 0;
    }

    /**
     * Busca un préstamo activo para un usuario y un libro específico.
     * 
     * @param user el usuario que ha solicitado el préstamo.
     * @param book el libro solicitado.
     * @return un {@link Optional<Loan>} que contiene el préstamo si se encuentra, o vacío si no existe.
     */
    @Override
    public Optional<Loan> findActiveLoanForUserAndBook(User user, Book book) {
        return loanRepository.findByUserAndBookAndStatus(user, book, Loan.LoanStatus.ACCEPTED);
    }

    /**
     * Busca todos los préstamos vencidos de un usuario, cuyo fecha de devolución sea anterior a la fecha actual.
     * 
     * @param user el usuario que ha solicitado el préstamo.
     * @return una lista de {@link Loan} que contiene los préstamos vencidos del usuario.
     */
    @Override
    public List<Loan> findOverdueLoanForUser(User user) {
        return loanRepository.findByUserAndStatusAndReturnDateBefore(user, Loan.LoanStatus.ACCEPTED, LocalDate.now());
    }

    /**
     * Busca todos los préstamos de un usuario.
     * 
     * @param user el usuario que ha solicitado los préstamos.
     * @return una lista de {@link Loan} que contiene todos los préstamos del usuario.
     */
    @Override
    public List<Loan> findLoansByUser(User user) {
        return loanRepository.findByUserId(user.getId());
    }

    /**
     * Actualiza el estado de los préstamos vencidos de un usuario a "VENCIDO".
     * 
     * @param user el usuario que ha solicitado los préstamos.
     */
    @Override
    public void updateOverdueLoans(User user) {
        List<Loan> activeLoans = loanRepository.findByUserAndStatusAndReturnDateBefore(
                user, Loan.LoanStatus.ACCEPTED, LocalDate.now());

        for (Loan loan : activeLoans) {
            loan.setStatus(Loan.LoanStatus.OVERDUE);
            loanRepository.save(loan);
        }
    }


}
