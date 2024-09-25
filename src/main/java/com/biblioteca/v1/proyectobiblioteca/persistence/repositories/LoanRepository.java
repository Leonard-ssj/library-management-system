package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Encuentra todos los préstamos asociados a un usuario específico
    List<Loan> findByUserId(Long userId);

    // Encuentra todos los préstamos asociados a un libro específico
    List<Loan> findByBookId(Long bookId);

    // Encuentra préstamos con un estado específico
    List<Loan> findByStatus(Loan.LoanStatus status);

    // Encuentra un préstamo de un usuario para un libro específico con un estado dado
    Optional<Loan> findByUserAndBookAndStatus(User user, Book book, Loan.LoanStatus status);

    // (Opcional) Encuentra préstamos atrasados basados en returnDate
    List<Loan> findByUserAndStatusAndReturnDateBefore(User user, Loan.LoanStatus status, LocalDate returnDate);
    
    
}
