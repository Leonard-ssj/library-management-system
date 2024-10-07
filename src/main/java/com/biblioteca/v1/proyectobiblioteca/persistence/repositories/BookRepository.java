package com.biblioteca.v1.proyectobiblioteca.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Category;

/**
 * Repositorio para la entidad {@link Book}.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas,
 * así como métodos personalizados para buscar libros por su ISBN y categoría.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Busca un libro por su código ISBN.
     * 
     * @param isbn el código ISBN del libro.
     * @return un {@link Optional<Book>} que contiene el libro encontrado, si existe.
     */
    Optional<Book> findByIsbn(String isbn); // Encuentra un libro por su código ISBN.

    /**
     * Busca una lista de libros que pertenecen a una categoría específica.
     * 
     * @param category la categoría de los libros a buscar.
     * @return una lista de libros que pertenecen a la categoría indicada.
     */
    List<Book> findByCategory(Category category);

    /**
     * Verifica si existe un libro con un código ISBN específico.
     * 
     * @param isbn el código ISBN del libro.
     * @return true si el libro con el ISBN existe, false en caso contrario.
     */
    boolean existsByIsbn(String isbn); // Método adicional para verificar si un libro con el ISBN existe.
}
