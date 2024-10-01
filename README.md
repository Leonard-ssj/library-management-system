# Sistema de Gestión de Biblioteca

Este proyecto es un **Sistema de Gestión de Biblioteca**, diseñado para facilitar la administración de libros, usuarios y préstamos en una biblioteca. Se desarrolló con el propósito de probar mis habilidades en programación y arquitectura de software, así como para aplicar buenas prácticas en el desarrollo. 

El sistema permite a los bibliotecarios gestionar los libros y préstamos, a los usuarios consultar la disponibilidad de libros y realizar reservas. Implementa un control de acceso basado en roles, asegurando que las funcionalidades se distribuyan adecuadamente entre los diferentes tipos de usuarios: **Administrador**, **Bibliotecario** y **Usuario**.

## Tecnologías Utilizadas

- **Backend**: Java con Spring Boot
- **Base de Datos**: MySQL
- **Seguridad**: Spring Security
- **Frontend**: Thymeleaf y TailwindUI (para estilos)
- **Control de Versiones**: Git y GitHub


## Configura la base de datos:

Asegúrate de tener una base de datos MySQL configurada.
Si lo deseas, puedo proporcionarte un archivo SQL exportado desde mi MySQL para que lo ejecutes y tengas la misma configuración.
Este archivo lo puedes encontrar en la carpeta de database_import el archivo se llama "proyectobiblioteca.sql", tienes que importar este archivo
en Mysql pero antes tienes que crear una db con el nombre que tenemos en el application.properties nombre de la db:"biblioteca_proyect" asi se llama la db para que se compatible con el sql
para que puedas tener la misma db ya que no se puede generar usuario con el ROL de ADMIN y LIBRARIAN ya que necesitarias crearlos desde Mysql pero con la importacion de la db 
ya tendrias estos usuarios con los roles.
Para acceder a el Usuario con el ROL de ADMIN inicia sesión al iniciar el proyecto en Nombre de usuario:admin123 y Contraseña:Administrador123, y 
para el usuario con el ROL de LIBRAIAN seria en el Nombre de usuario:librarianUser y Contraseña:bibliotecario123, con esto ya podras acceder a los recursos 
autorizados para estos roles.

Si deseas crearlos desde la Mysql en la db que quieras primero tienes que crear la conexion en el application.properties por default ya la tenemos con el nombre de "biblioteca_proyect" pero le puedes cambies el nombre, luego inicia el proyecto para que jpa y hibernate hagan su trabajo de mapear las entidades y tablas, despues ve a tu Mysql y la tabla que creaste anteriormente e inserta los usuarios en la tabla de "users" pero en el campo de password utilizamos un metodo de encryptacion: 
// Codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 , puedes crear una contraseña con este metodo para copiarla y pegarla en el campo de password cuando intentes crear el usuario con el rol de librarian o admin, despues de esto tienes que ir a la tabla de "user_roles" e insertar el id del usuario con el id del rol de admin o librarian, este metodo solo sirve si no importaste el archivo .sql que antes te describi o quieres crear mas admins o librarians, para los usuarios con el rol de USER solo se pueden crear en la pagina de register por defecto ya tienen ell rol de USER o tambien puedes hacerlo desde la db. 


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
