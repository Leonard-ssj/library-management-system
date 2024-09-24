package com.biblioteca.v1.proyectobiblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.v1.proyectobiblioteca.services.BookService;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;

import java.util.Collections;
import java.util.List;

@Controller
public class LibrarianController {

    private final BookService bookService;

    @Autowired
    public LibrarianController(BookService bookService) {
        this.bookService = bookService;
    }

    // Ruta para mostrar la lista de libros al bibliotecario
    @GetMapping("/librarian/list-books")
    public String listBooksForLibrarian(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "librarian/list-books"; // Nombre del archivo HTML que se renderizará
    }

    // Ruta para que el bibliotecario pueda buscar libros
    @GetMapping("/search-books")
    public String searchBooks(@RequestParam(value = "isbn", required = false) String isbn,
                              @RequestParam(value = "category", required = false) String category,
                              Model model) {
        List<Book> books;
        if (isbn != null && !isbn.isEmpty()) {
            books = bookService.findByIsbn(isbn).map(Collections::singletonList).orElse(Collections.emptyList());
        } else if (category != null && !category.equals("all")) {
            books = bookService.findByCategory(category);
        } else {
            books = bookService.findAll();
        }
        model.addAttribute("books", books);
        return "librarian/list-books"; // Reutiliza la vista de lista de libros
    }
}
