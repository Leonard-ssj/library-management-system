package com.biblioteca.v1.proyectobiblioteca.services;

import java.util.List;
import java.util.Optional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Loan;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.User;

public interface LoanService {

    Loan saveLoan(Loan loan);

    Optional<Loan> findById(Long id);

    List<Loan> findAll();

    void deleteLoan(Long id);

    List<Loan> findByUserId(Long userId);

    List<Loan> findByBookId(Long bookId);

    List<Loan> findByStatus(Loan.LoanStatus status);

    void acceptLoan(Long loanId);

    void rejectLoan(Long loanId);

    Optional<Loan> findPendingLoanForUserAndBook(User user, Book book);

    boolean isBookAvailable(Book book);

    Optional<Loan> findActiveLoanForUserAndBook(User user, Book book);

    List<Loan> findOverdueLoanForUser(User user);

    List<Loan> findLoansByUser(User user);

    void updateOverdueLoans(User user);

    



}