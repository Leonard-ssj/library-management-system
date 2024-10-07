package com.biblioteca.v1.proyectobiblioteca.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Book;
import com.biblioteca.v1.proyectobiblioteca.persistence.entities.Category;
import com.biblioteca.v1.proyectobiblioteca.services.BookService;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con los libros.
 * Permite al bibliotecario agregar nuevos libros y asociarles imágenes de portada.
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService; // Inyección del servicio que maneja las operaciones de los libros

    /**
     * Muestra el formulario para agregar un libro.
     * 
     * @param model Modelo que se pasa a la vista
     * @return La vista "librarian/add-book" que contiene el formulario para agregar un libro
     */
    @GetMapping("/librarian/add-book")
    public String showAddBookForm(Model model) {
        // Se agrega un nuevo objeto Book al modelo para ser utilizado en la vista
        model.addAttribute("book", new Book());
        // Se agrega la lista de categorías al modelo para que el bibliotecario pueda seleccionar una
        model.addAttribute("categories", Category.values());
        return "librarian/add-book"; // Ruta de la vista
    }

    /**
     * Maneja la solicitud POST para agregar un nuevo libro.
     * 
     * @param book El objeto Book con los datos ingresados por el bibliotecario
     * @param file El archivo de imagen de la portada del libro
     * @param model Modelo que se pasa a la vista
     * @return La vista "librarian/add-book" si hay errores, o redirige a la misma vista si el libro se agrega correctamente
     */
    @PostMapping("/librarian/add-book")
    public String addBook(Book book, @RequestParam("file") MultipartFile file, Model model) {
        // Verifica que el archivo no esté vacío y sea una imagen válida (jpg, jpeg, png)
        if (file.isEmpty() || !isValidImageFile(file)) {
            // Si el archivo no es válido, agrega un mensaje de error y vuelve a mostrar el formulario
            model.addAttribute("error", "Por favor, sube un archivo de imagen válido (jpg, jpeg, png).");
            model.addAttribute("categories", Category.values());
            return "librarian/add-book"; // Ruta a la vista
        }

        try {
            // Obtiene el nombre original del archivo
            String fileName = file.getOriginalFilename();
            // Define la ruta del archivo en el sistema de archivos (en el directorio de recursos estáticos)
            Path filePath = Paths.get("src/main/resources/static/book-covers/" + fileName);

            // Verifica si el archivo ya existe y genera un nombre único si es necesario
            if (Files.exists(filePath)) {
                // Si el archivo ya existe, se renombra agregando el timestamp para asegurar unicidad
                fileName = System.currentTimeMillis() + "-" + fileName;
                filePath = Paths.get("src/main/resources/static/book-covers/" + fileName);
            }

            // Copia el archivo de la imagen al directorio de destino
            Files.copy(file.getInputStream(), filePath);

            // Guarda la ruta de la imagen en la base de datos
            book.setImagePath("/book-covers/" + fileName);
            // Llama al servicio para guardar el libro en la base de datos
            bookService.saveBook(book);

            // Redirige al formulario para agregar un nuevo libro
            return "redirect:/librarian/add-book";
        } catch (IOException e) {
            e.printStackTrace();
            // Si ocurre un error al guardar el archivo o al agregar el libro, muestra un mensaje de error
            model.addAttribute("error", "Hubo un error al guardar el libro.");
            model.addAttribute("categories", Category.values());
            return "librarian/add-book"; // Ruta a la vista
        }
    }

    /**
     * Verifica si el archivo subido es una imagen válida (jpg o png).
     * 
     * @param file El archivo de imagen
     * @return true si es una imagen válida, false en caso contrario
     */
    private boolean isValidImageFile(MultipartFile file) {
        // Verifica que el tipo de contenido sea JPEG o PNG
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
    }
}
