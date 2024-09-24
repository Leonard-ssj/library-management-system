package com.biblioteca.v1.proyectobiblioteca.persistence.entities;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // Relación muchos a uno con la entidad User (usuario que solicitó el préstamo)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Usuario que solicitó el préstamo

    // Relación muchos a uno con la entidad Book (libro solicitado)
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // Libro solicitado

    
    private LocalDate requestDate; // Fecha en la que se solicitó el préstamo

    
    private LocalDate loanDate; // Fecha en la que se tendria que devolver el prestramo

    private LocalDate returnDate; // Fecha en la que se devolvió el libro (opcional)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status; // Estado de la solicitud de préstamo

    @Enumerated(EnumType.STRING)
    private LoanDuration duration; // Duración seleccionada para el préstamo

    // Enumeración que representa los diferentes estados del préstamo
    public enum LoanStatus {
        PENDING,    // Solicitud pendiente para ser aceptada
        ACCEPTED,   // Solicitud aceptada y préstamo realizado
        REJECTED,   // Solicitud rechazada
        RETURNED,   // El préstamo ha sido devuelto
        OVERDUE     // El préstamo está vencido (no se devolvió a tiempo)
    }

    // Enumeración que representa las duraciones posibles del préstamo
    public enum LoanDuration {
        TWO_WEEKS(14, "Dos semanas"),  // 14 días
        ONE_MONTH(30, "Un mes"),  // 30 días
        TWO_MONTHS(60, "Dos meses"); // 60 días

        private final int days;
        private final String label; // Texto amigable para la vista

        LoanDuration(int days, String label) {
            this.days = days;
            this.label = label;
        }

        public int getDays() {
            return days;
        }

        public String getLabel() {
            return label;
        }
    }
    
}
