package com.biblioteca.v1.proyectobiblioteca.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.services.BookService;

@Controller
public class LibrarianController {

    // Servicio para interactuar con los libros en la base de datos
    private final BookService bookService;

    // Constructor que inyecta el servicio BookService
    @Autowired
    public LibrarianController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Método para mostrar la lista de libros disponibles para el bibliotecario.
     * Este método obtiene todos los libros de la base de datos y los pasa a la vista.
     * 
     * @param model El modelo que se pasa a la vista.
     * @return La vista "librarian/list-books" con la lista de libros.
     */
    @GetMapping("/librarian/list-books")
    public String listBooksForLibrarian(Model model) {
        // Obtiene todos los libros de la base de datos a través del servicio
        List<Book> books = bookService.findAll();
        
        // Añade los libros al modelo para que estén disponibles en la vista
        model.addAttribute("books", books);

        // Renderiza la vista "librarian/list-books"
        return "librarian/list-books";
    }

    /**
     * Método para buscar libros por ISBN o categoría.
     * Si el parámetro ISBN es proporcionado, busca por ISBN. Si el parámetro de categoría es proporcionado, busca por categoría.
     * Si no se proporcionan estos parámetros, muestra todos los libros.
     * 
     * @param isbn El número ISBN del libro a buscar (opcional).
     * @param category La categoría del libro a buscar (opcional).
     * @param model El modelo que se pasa a la vista.
     * @return La vista "librarian/list-books" con los resultados de la búsqueda.
     */
    @GetMapping("/search-books")
    public String searchBooks(@RequestParam(value = "isbn", required = false) String isbn,
                              @RequestParam(value = "category", required = false) String category,
                              Model model) {
        List<Book> books;

        // Si se ha proporcionado un ISBN, se busca por ISBN
        if (isbn != null && !isbn.isEmpty()) {
            books = bookService.findByIsbn(isbn).map(Collections::singletonList).orElse(Collections.emptyList());
        }
        // Si se ha proporcionado una categoría que no sea "all", se busca por categoría
        else if (category != null && !category.equals("all")) {
            books = bookService.findByCategory(category);
        }
        // Si no se proporcionan parámetros de búsqueda, se muestra la lista completa de libros
        else {
            books = bookService.findAll();
        }

        // Añade los libros al modelo para que estén disponibles en la vista
        model.addAttribute("books", books);

        // Reutiliza la vista "librarian/list-books" para mostrar los resultados de la búsqueda
        return "librarian/list-books";
    }
}
