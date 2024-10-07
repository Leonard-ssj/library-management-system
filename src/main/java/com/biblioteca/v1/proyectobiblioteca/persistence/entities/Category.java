package com.biblioteca.v1.proyectobiblioteca.persistence.entities;

/**
 * La enumeración Category representa las diferentes categorías en las que un libro
 * puede ser clasificado en el sistema. Estas categorías ayudan a organizar los libros
 * de manera más eficiente y permiten a los usuarios buscar libros por temática.
 */
public enum Category {
    FICTION,      // Ficción: Libros de narrativa inventada o ficticia.
    SCIENCE,      // Ciencia: Libros sobre temas científicos o tecnológicos.
    HISTORY,      // Historia: Libros que tratan sobre hechos y eventos históricos.
    TECHNOLOGY,   // Tecnología: Libros que abordan el tema de la tecnología y sus avances.
    ART,          // Arte: Libros relacionados con el arte en sus distintas formas y disciplinas.
    SPORTS,       // Deportes: Libros sobre temas deportivos y actividades físicas.
    LITERATURE,   // Literatura: Libros que engloban obras literarias clásicas o modernas.
    BIOGRAPHY     // Biografía: Libros que narran la vida de personas reales.
}
