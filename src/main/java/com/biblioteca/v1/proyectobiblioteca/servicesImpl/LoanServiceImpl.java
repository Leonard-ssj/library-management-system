package com.biblioteca.v1.proyectobiblioteca.servicesImpl;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.BookRepository;
import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.LoanRepository;
import com.biblioteca.v1.proyectobiblioteca.services.LoanService;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Loan saveLoan(Loan loan) {// Guarda el objeto Loan en la base de datos usando el repositorio
        return loanRepository.save(loan);
    }

    @Override
    public Optional<Loan> findById(Long id) {// Busca un préstamo por su identificador
        return loanRepository.findById(id);
    }

    @Override
    public List<Loan> findAll() {// Devuelve una lista de todos los préstamos en la base de datos.
        return loanRepository.findAll();
    }

    @Override
    public void deleteLoan(Long id) {// Elimina un préstamo de la base de datos por su identificador.
        loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> findByUserId(Long userId) {// Busca todos los préstamos asociados a un usuario específico.
        return loanRepository.findByUserId(userId);
    }

    @Override
    public List<Loan> findByBookId(Long bookId) {// Busca todos los préstamos asociados a un libro específico.
        return loanRepository.findByBookId(bookId);
    }

    @Override
    public List<Loan> findByStatus(Loan.LoanStatus status) {// Busca préstamos que tengan un estado específico.
        return loanRepository.findByStatus(status);
    }

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

    @Override
    public void rejectLoan(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            Loan existingLoan = loan.get();
            existingLoan.setStatus(Loan.LoanStatus.REJECTED);
            loanRepository.save(existingLoan);
        }
    }

    @Override
    public Optional<Loan> findPendingLoanForUserAndBook(User user, Book book) {
        return loanRepository.findByUserAndBookAndStatus(user, book, Loan.LoanStatus.PENDING);
    }

    @Override
    public boolean isBookAvailable(Book book) {
        // Verifica si hay copias disponibles del libro
        return book.getAvailableQuantity() > 0;
    }

    @Override
    public Optional<Loan> findActiveLoanForUserAndBook(User user, Book book) {
        return loanRepository.findByUserAndBookAndStatus(user, book, Loan.LoanStatus.ACCEPTED);
    }

    @Override
    public List<Loan> findOverdueLoanForUser(User user) {
        return loanRepository.findByUserAndStatusAndReturnDateBefore(user, Loan.LoanStatus.ACCEPTED, LocalDate.now());
    }

    @Override
    public List<Loan> findLoansByUser(User user) {
        return loanRepository.findByUserId(user.getId());
    }

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