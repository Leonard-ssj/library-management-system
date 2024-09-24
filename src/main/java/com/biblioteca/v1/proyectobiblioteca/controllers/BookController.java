package com.biblioteca.v1.proyectobiblioteca.controllers;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Category;
import com.biblioteca.v1.proyectobiblioteca.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/librarian/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", Category.values()); // Agrega la lista de categorías al modelo
        return "librarian/add-book"; // Ruta a la vista
    }

    @PostMapping("/librarian/add-book")
    public String addBook(Book book, @RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty() || !isValidImageFile(file)) {
            model.addAttribute("error", "Por favor, sube un archivo de imagen válido (jpg, jpeg, png).");
            model.addAttribute("categories", Category.values());
            return "librarian/add-book"; // Ruta a la vista
        }

        try {
            // Guarda la imagen en el directorio deseado
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get("src/main/resources/static/book-covers/" + fileName);

            // Verifica si el archivo ya existe y crea un nombre único si es necesario
            if (Files.exists(filePath)) {
                fileName = System.currentTimeMillis() + "-" + fileName; // Asegura que el nombre sea único
                filePath = Paths.get("src/main/resources/static/book-covers/" + fileName);
            }

            Files.copy(file.getInputStream(), filePath);

            // Guarda la ruta de la imagen en la base de datos
            book.setImagePath("/book-covers/" + fileName);
            bookService.saveBook(book);

            
            return "redirect:/librarian/add-book";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un error al guardar el libro.");
            model.addAttribute("categories", Category.values());
            return "librarian/add-book"; // Ruta a la vista
        }
    }

    private boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
    }
}
