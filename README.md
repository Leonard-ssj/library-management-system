# Sistema de Gestión de Biblioteca

Este proyecto es un **Sistema de Gestión de Biblioteca**, diseñado para facilitar la administración de libros, usuarios y préstamos en una biblioteca. Se desarrolló con el propósito de probar mis habilidades en programación y arquitectura de software, así como para aplicar buenas prácticas en el desarrollo. 

El sistema permite a los bibliotecarios gestionar los libros y préstamos, a los usuarios consultar la disponibilidad de libros y realizar reservas. Implementa un control de acceso basado en roles, asegurando que las funcionalidades se distribuyan adecuadamente entre los diferentes tipos de usuarios: **Administrador**, **Bibliotecario** y **Usuario**.

## Tecnologías Utilizadas

- **Backend**: Java con Spring Boot
- **Base de Datos**: MySQL
- **Seguridad**: Spring Security
- **Frontend**: Thymeleaf y TailwindUI (para estilos)
- **Control de Versiones**: Git y GitHub

## Instalación

Para instalar y configurar el proyecto en un entorno local, sigue estos pasos:

1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/Leonard-ssj/tu_repositorio.git
Navega al directorio del proyecto:

bash
Copiar código
cd tu_repositorio
Instala las dependencias: Si estás utilizando Maven, ejecuta:

bash
Copiar código
mvn install
Configura la base de datos:

Asegúrate de tener una base de datos MySQL configurada.
Los usuarios con roles de librarian y admin deben ser insertados o creados manualmente en la base de datos.
Si lo deseas, puedo proporcionarte un archivo SQL exportado desde mi MySQL para que lo ejecutes y tengas la misma configuración.
Ejecuta la aplicación: Para iniciar la aplicación, usa el siguiente comando:

bash
Copiar código
mvn spring-boot:run
Uso
Después de iniciar la aplicación, puedes acceder a la interfaz a través de tu navegador web. Por defecto, se ejecuta en http://localhost:8080.

Funcionalidades Principales
Administración de libros: Agregar, editar y eliminar libros.
Gestión de préstamos: Registrar préstamos y devoluciones de libros.
Gestión de usuarios: Registrar nuevos usuarios y asignarles roles.
Cómo Funciona
El sistema permite a los usuarios acceder a diferentes funcionalidades según su rol. Los administradores pueden gestionar tanto los libros como los usuarios, mientras que los bibliotecarios tienen acceso a la gestión de libros y préstamos. Los usuarios pueden ver la disponibilidad de libros y realizar reservas.

El sistema utiliza Spring Security para implementar un control de acceso seguro, garantizando que solo los usuarios autorizados puedan acceder a las funciones específicas.

Mejoras Futuras
Implementar una arquitectura basada en microservicios para escalar el sistema de manera más efectiva.
Añadir autenticación de dos pasos para mejorar la seguridad.
Integrar OAuth2 para permitir el inicio de sesión mediante cuentas de terceros (Google, Facebook, etc.).
Contribución
¡Las contribuciones son bienvenidas! Si deseas contribuir a este proyecto, por favor sigue estos pasos:

Haz un fork del repositorio.
Crea una nueva rama:
bash
Copiar código
git checkout -b nombre-de-tu-rama
Realiza tus cambios y haz commit:
bash
Copiar código
git commit -m "Descripción de los cambios"
Envía tu rama:
bash
Copiar código
git push origin nombre-de-tu-rama
Crea un Pull Request.


Contacto:
Leonardo Alonso Aldana,
Correo: pardo0435@gmail.com,
GitHub: Leonard-ssj
