package com.biblioteca.v1.proyectobiblioteca.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Category;
import com.biblioteca.v1.proyectobiblioteca.persistence.repositories.BookRepository;
import com.biblioteca.v1.proyectobiblioteca.services.BookService;

/**
 * Implementación del servicio {@link BookService} para manejar operaciones relacionadas con la entidad {@link Book}.
 * Proporciona métodos para la gestión de libros, como guardarlos, buscarlos, eliminarlos y verificar su existencia.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Constructor de la clase BookServiceImpl.
     * 
     * @param bookRepository el repositorio de {@link Book} que será utilizado para acceder a la base de datos.
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Guarda un libro en la base de datos.
     * 
     * @param book el libro a guardar.
     * @return el libro guardado.
     */
    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Busca un libro por su identificador.
     * 
     * @param id el identificador del libro.
     * @return un {@link Optional<Book>} que contiene el libro si se encuentra, o vacío si no existe.
     */
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Busca un libro por su código ISBN.
     * 
     * @param isbn el ISBN del libro.
     * @return un {@link Optional<Book>} que contiene el libro si se encuentra, o vacío si no existe.
     */
    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    /**
     * Devuelve una lista de todos los libros almacenados en la base de datos.
     * 
     * @return una lista de {@link Book} que contiene todos los libros.
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Elimina un libro de la base de datos por su identificador.
     * 
     * @param id el identificador del libro a eliminar.
     */
    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Busca libros por categoría. La categoría se convierte a un valor de la enumeración {@link Category}.
     * Si la categoría no es válida, se retorna una lista vacía.
     * 
     * @param category el nombre de la categoría.
     * @return una lista de {@link Book} que contiene los libros de la categoría especificada, o una lista vacía si la categoría no es válida.
     */
    @Override
    public List<Book> findByCategory(String category) {
        Category cat;
        try {
            cat = Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return List.of(); // Retorna una lista vacía si la categoría no es válida
        }
        return bookRepository.findByCategory(cat);
    }

    /**
     * Verifica si un libro con el ISBN especificado ya existe en la base de datos.
     * 
     * @param isbn el ISBN del libro.
     * @return {@code true} si el libro con el ISBN existe, {@code false} en caso contrario.
     */
    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }
}
