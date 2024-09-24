package com.biblioteca.v1.proyectobiblioteca.persistence.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase Book representa un libro en el sistema.
 * Utiliza JPA para mapear esta clase a una tabla llamada 'books'.
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del libro

    @NotBlank(message = "El título del libro no puede estar vacío")
    @Column(nullable = false)
    private String title;  // Título del libro

    @NotBlank(message = "El autor no puede estar vacío")
    @Column(nullable = false)
    private String author;  // Autor del libro

    @NotBlank(message = "El ISBN no puede estar vacío")
    @Pattern(regexp = "^[0-9]{13}$", message = "El ISBN debe tener 13 dígitos")
    @Column(unique = true)
    private String isbn;  // ISBN del libro, único para cada libro

    @Min(value = 1, message = "Debe haber al menos una copia del libro")
    @Column(nullable = false)
    private int quantity;  // Cantidad total de ejemplares disponibles

    private int availableQuantity;  // Cantidad disponible para prestar

    private LocalDate publicationDate;  // Fecha de publicación del libro

    private String imagePath;  // Ruta o URL de la imagen del libro

    // Campo para manejar la carga de archivos (no se persiste en la base de datos)
    @Transient
    private MultipartFile imageFile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;  // Categoría del libro, representada como un enum

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;  // Lista de préstamos asociados a este libro

    
    @PrePersist
    public void prePersist() {
        this.availableQuantity = this.quantity;
    }
}
