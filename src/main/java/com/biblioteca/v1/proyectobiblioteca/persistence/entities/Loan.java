package com.biblioteca.v1.proyectobiblioteca.persistence.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase Loan representa un préstamo de libro en el sistema.
 * Utiliza JPA para mapear esta clase a la tabla 'loans' en la base de datos.
 */
@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para el préstamo

    /**
     * Relación muchos a uno con la entidad User (usuario que solicitó el préstamo).
     * Cada préstamo está asociado a un usuario que realizó la solicitud.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Usuario que solicitó el préstamo

    /**
     * Relación muchos a uno con la entidad Book (libro solicitado).
     * Cada préstamo está asociado a un libro que ha sido solicitado.
     */
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // Libro solicitado

    /**
     * Fecha en la que se solicitó el préstamo.
     * Esta es la fecha en que el usuario realizó la solicitud del préstamo.
     */
    private LocalDate requestDate; 

    /**
     * Fecha en la que se debe devolver el libro prestado.
     * Es la fecha límite en que el usuario debe devolver el libro.
     */
    private LocalDate loanDate;

    /**
     * Fecha en la que se devolvió el libro.
     * Esta fecha se registra si el libro ha sido devuelto por el usuario.
     */
    private LocalDate returnDate; 

    /**
     * Estado del préstamo, representado como un valor de la enumeración LoanStatus.
     * Determina si el préstamo está pendiente, aceptado, rechazado, devuelto o vencido.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status; // Estado de la solicitud de préstamo

    /**
     * Duración del préstamo, representada como un valor de la enumeración LoanDuration.
     * Define el período de tiempo durante el cual el usuario puede tener el libro en su posesión.
     */
    @Enumerated(EnumType.STRING)
    private LoanDuration duration; 

    /**
     * Enumeración que representa los diferentes estados que puede tener un préstamo.
     */
    public enum LoanStatus {
        PENDING,    // Solicitud pendiente para ser aceptada
        ACCEPTED,   // Solicitud aceptada y préstamo realizado
        REJECTED,   // Solicitud rechazada
        RETURNED,   // El préstamo ha sido devuelto
        OVERDUE     // El préstamo está vencido (no se devolvió a tiempo)
    }

    /**
     * Enumeración que representa las duraciones posibles para un préstamo.
     * Define los periodos de tiempo predefinidos para los préstamos.
     */
    public enum LoanDuration {
        TWO_WEEKS(14, "Dos semanas"),  // 14 días
        ONE_MONTH(30, "Un mes"),  // 30 días
        TWO_MONTHS(60, "Dos meses"); // 60 días

        private final int days;   // Duración en días
        private final String label;  // Texto amigable para la vista

        LoanDuration(int days, String label) {
            this.days = days;
            this.label = label;
        }

        public int getDays() {
            return days; // Devuelve el número de días de la duración
        }

        public String getLabel() {
            return label; // Devuelve la etiqueta amigable para la vista
        }
    }
    
}
