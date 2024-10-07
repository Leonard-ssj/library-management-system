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

    // Servicio que se utiliza para interactuar con los libros en la base de datos
    private final BookService bookService;

    // Constructor para inyectar la dependencia del servicio BookService
    @Autowired
    public BookEditController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Muestra el formulario para editar un libro existente.
     * 
     * @param id Identificador del libro a editar.
     * @param model El modelo que se pasa a la vista.
     * @return La vista "librarian/edit-book" con el libro a editar y las categorías disponibles.
     */
    @GetMapping("/librarian/edit-book/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        // Obtiene el libro desde el servicio utilizando el ID proporcionado
        Book book = bookService.findById(id)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado")); // Lanza una excepción si no se encuentra el libro
        model.addAttribute("book", book); // Añade el libro al modelo para que esté disponible en la vista
        model.addAttribute("categories", Category.values()); // Añade la lista de categorías al modelo para mostrarlas en el formulario
        return "librarian/edit-book"; // Devuelve la vista que muestra el formulario de edición de libro
    }

    /**
     * Actualiza un libro en la base de datos con la nueva información.
     * 
     * @param id Identificador del libro a actualizar.
     * @param title Nuevo título del libro.
     * @param author Nuevo autor del libro.
     * @param isbn Nuevo ISBN del libro.
     * @param categoryString Nueva categoría del libro (en forma de String).
     * @param availableQuantity Nueva cantidad disponible del libro.
     * @param redirectAttributes Atributos de redirección para mostrar mensajes de error o éxito.
     * @return Redirige a la lista de libros después de la actualización, o muestra un mensaje de error en caso de fallo.
     */
    @PostMapping("/librarian/edit-book/{id}/update")
    public String updateBook(@PathVariable("id") Long id,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("category") String categoryString,
                             @RequestParam("quantity") int availableQuantity,
                             RedirectAttributes redirectAttributes) {
        // Obtiene el libro a actualizar desde la base de datos
        Book book = bookService.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Intenta convertir el String de categoría a un valor del enum Category
        Category category;
        try {
            category = Category.valueOf(categoryString.toUpperCase()); // Convierte la cadena en un valor del enum
        } catch (IllegalArgumentException e) {
            // Si la conversión falla, muestra un error y redirige al formulario de edición
            redirectAttributes.addFlashAttribute("error", "Categoría inválida.");
            return "redirect:/librarian/edit-book/" + id;
        }

        // Actualiza los campos del libro con los nuevos valores proporcionados
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCategory(category);
        book.setAvailableQuantity(availableQuantity);

        // Guarda el libro actualizado en la base de datos
        bookService.saveBook(book);

        // Redirige al listado de libros después de la actualización exitosa
        return "redirect:/librarian/list-books";
    }
}
