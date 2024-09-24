package com.biblioteca.v1.proyectobiblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Category;
import com.biblioteca.v1.proyectobiblioteca.services.BookService;

@Controller
public class BookEditController {

    private final BookService bookService;

    @Autowired
    public BookEditController(BookService bookService) {
        this.bookService = bookService;
    }

    // Método para mostrar el formulario de edición
    @GetMapping("/librarian/edit-book/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        model.addAttribute("book", book);
        model.addAttribute("categories", Category.values()); // Opcional: Para mostrar las categorías en el formulario
        return "librarian/edit-book"; // Nombre del archivo HTML sin la extensión
    }

    // Método para actualizar el libro
    @PostMapping("/librarian/edit-book/{id}/update")
    public String updateBook(@PathVariable("id") Long id,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("category") String categoryString,
                             @RequestParam("quantity") int availableQuantity,
                             RedirectAttributes redirectAttributes) {
        Book book = bookService.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        
        // Convertir el String en un valor del enum Category
        Category category;
        try {
            category = Category.valueOf(categoryString.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Manejar el error si la categoría no es válida
            redirectAttributes.addFlashAttribute("error", "Categoría inválida.");
            return "redirect:/librarian/edit-book/" + id;
        }

        // Actualizar los campos del libro
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCategory(category);
        book.setAvailableQuantity(availableQuantity);
        
        // Guardar los cambios
        bookService.saveBook(book);
        
        return "redirect:/librarian/list-books"; // Redirige a la página de listado de libros después de actualizar
    }
}
