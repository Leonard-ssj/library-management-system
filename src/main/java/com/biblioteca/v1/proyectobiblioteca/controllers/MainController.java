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
public class MainController {

    // Inyección de dependencia del servicio de libros
    private final BookService bookService;

    @Autowired
    public MainController(BookService bookService) {
        this.bookService = bookService;
    }

    // Ruta para la página de inicio, muestra todos los libros
    @GetMapping("/global/index")
    public String homePage(Model model) {
        // Obtiene todos los libros desde el servicio y los añade al modelo
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "global/index"; // Retorna el nombre de la vista que representa la página de inicio
    }

    // Ruta para realizar búsquedas de libros por ISBN o categoría
    @GetMapping("/buscar")
    public String searchBooks(@RequestParam(value = "isbn", required = false) String isbn,
                              @RequestParam(value = "category", required = false) String category,
                              Model model) {
        List<Book> books;

        // Si se proporciona un ISBN, busca el libro por ISBN
        if (isbn != null && !isbn.isEmpty()) {
            books = bookService.findByIsbn(isbn).map(Collections::singletonList).orElse(Collections.emptyList());
        }
        // Si se proporciona una categoría, busca libros por categoría
        else if (category != null && !category.equals("all")) {
            books = bookService.findByCategory(category);
        }
        // Si no se proporciona ninguno, muestra todos los libros
        else {
            books = bookService.findAll();
        }

        // Añade los libros encontrados al modelo
        model.addAttribute("books", books);
        return "global/index"; // Retorna el nombre de la vista para mostrar los libros
    }
}
