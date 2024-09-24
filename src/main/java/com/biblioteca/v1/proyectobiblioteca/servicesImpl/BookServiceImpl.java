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

@Service
@Transactional
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(Book book) {//Guarda un libro en la base de datos usando el repositorio.
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {//Busca un libro por su identificador. El método findById del repositorio devuelve un Optional<Book>
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {//Busca un libro por su ISBN. Similar al método findById
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public List<Book> findAll() {//Devuelve una lista de todos los libros almacenados en la base de datos.
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(Long id) {//Elimina un libro de la base de datos por su identificador.
        bookRepository.deleteById(id);
    }

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

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }
}
