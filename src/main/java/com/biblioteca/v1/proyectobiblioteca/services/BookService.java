package com.biblioteca.v1.proyectobiblioteca.services;

import java.util.List;
import java.util.Optional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;

/**
 * Servicio para manejar las operaciones relacionadas con la entidad {@link Book}.
 * Proporciona métodos para la gestión de libros, incluyendo la creación, búsqueda, eliminación y verificación de libros.
 */
public interface BookService {

    /**
     * Guarda un libro en la base de datos.
     * 
     * @param book el libro a guardar.
     * @return el libro guardado.
     */
    Book saveBook(Book book); // Guarda un libro en la base de datos.

    /**
     * Busca un libro por su identificador.
     * 
     * @param id el identificador del libro.
     * @return un {@link Optional<Book>} que contiene el libro si se encuentra, o vacío si no existe.
     */
    Optional<Book> findById(Long id); // Busca un libro por su identificador.

    /**
     * Busca un libro por su ISBN.
     * 
     * @param isbn el ISBN del libro.
     * @return un {@link Optional<Book>} que contiene el libro si se encuentra, o vacío si no existe.
     */
    Optional<Book> findByIsbn(String isbn); // Busca un libro por su ISBN.

    /**
     * Devuelve una lista de todos los libros disponibles en la base de datos.
     * 
     * @return una lista de {@link Book} que contiene todos los libros.
     */
    List<Book> findAll(); // Devuelve una lista de todos los libros.

    /**
     * Elimina un libro de la base de datos por su identificador.
     * 
     * @param id el identificador del libro a eliminar.
     */
    void deleteBook(Long id); // Elimina un libro de la base de datos por su identificador.

    /**
     * Busca libros por categoría.
     * 
     * @param category la categoría para la búsqueda de libros.
     * @return una lista de {@link Book} que contiene los libros de la categoría especificada.
     */
    List<Book> findByCategory(String category); // Busca libros por categoría.

    /**
     * Verifica si un libro con el ISBN proporcionado ya existe en la base de datos.
     * 
     * @param isbn el ISBN del libro a verificar.
     * @return true si el libro existe, false en caso contrario.
     */
    boolean existsByIsbn(String isbn); // Verifica si un libro con el ISBN existe.
}
