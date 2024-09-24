package com.biblioteca.v1.proyectobiblioteca.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 6, max = 30, message = "El nombre de usuario debe tener entre 6 y 30 caracteres.")
    @Pattern(regexp = "^(?=.*[a-zA-Z]{5,})(?=.*\\d)[a-zA-Z0-9]{6,}$",
            message = "El nombre de usuario debe tener al menos 6 caracteres, contener al menos 5 letras y 1 número, y solo letras y números.")
    private String username; // Nombre del usuario en el sistema

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres con letras y números.")
    private String password; // Contraseña del usuario

    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    @Email(message = "El correo electrónico debe ser válido.")
    @Size(max = 80, message = "El correo electrónico no puede tener más de 80 caracteres.")
    private String email; // Correo electrónico único

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres.")
    private String name; // Primer nombre del usuario (individual)

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres.")
    private String lastName; // Apellido del usuario (individual)

}
