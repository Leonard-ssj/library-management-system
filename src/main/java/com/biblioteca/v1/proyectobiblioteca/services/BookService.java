package com.biblioteca.v1.proyectobiblioteca.services;

import java.util.List;
import java.util.Optional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;

public interface BookService {
    
    Book saveBook(Book book); // Guarda un libro en la base de datos.
    
    Optional<Book> findById(Long id); // Busca un libro por su identificador.
    
    Optional<Book> findByIsbn(String isbn); // Busca un libro por su ISBN.
    
    List<Book> findAll(); // Devuelve una lista de todos los libros.
    
    void deleteBook(Long id); // Elimina un libro de la base de datos por su identificador.
    
    List<Book> findByCategory(String category); // Busca libros por categoría.

    // Método adicional para verificar si un libro con el ISBN existe
    boolean existsByIsbn(String isbn);
}
