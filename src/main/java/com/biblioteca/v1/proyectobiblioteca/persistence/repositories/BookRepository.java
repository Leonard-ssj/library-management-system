package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Category;

public interface BookRepository extends JpaRepository<Book, Long>{

    Optional<Book> findByIsbn(String isbn);//Encuentra un libro por su código ISBN.
    List<Book> findByCategory(Category category);
    // Método adicional para verificar si un libro con el ISBN existe
    boolean existsByIsbn(String isbn);
}
