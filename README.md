# Plataforma de Gestión de Citas

Sistema web desarrollado en **Java con Spring Boot** para la programación, administración y seguimiento de citas entre clientes y profesionales.

El proyecto permite gestionar usuarios, profesionales, servicios, horarios disponibles, agendamiento de citas, reprogramación, cancelación, confirmación, finalización y administración de precios. Además, cuenta con una interfaz web moderna, persistencia en SQLite, arquitectura por capas, manejo de errores, pruebas automatizadas y mejoras de accesibilidad.

Este proyecto fue desarrollado como entrega académica para la materia **Programación Orientada a Objetos II (POO2)**.

---

## Descripción general

La Plataforma de Gestión de Citas permite que un cliente seleccione primero un servicio, luego el sistema carga automáticamente los profesionales relacionados con ese servicio, posteriormente el cliente selecciona una fecha y la aplicación muestra las horas disponibles del profesional.

El sistema está dividido en tres roles principales:

* Administrador
* Profesional
* Cliente

Cada rol cuenta con un panel independiente y funcionalidades específicas.

---

## Objetivo del proyecto

Desarrollar una aplicación web funcional para la gestión de citas, aplicando principios de Programación Orientada a Objetos II, arquitectura por capas, separación de responsabilidades, persistencia de datos, validaciones, excepciones personalizadas, pruebas y buenas prácticas de desarrollo.

---

## Tecnologías utilizadas

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* SQLite
* Thymeleaf
* HTML5
* CSS3
* JavaScript
* Maven
* JUnit
* Mockito
* Git y GitHub

---

## Funcionalidades principales

* Registro e inicio de sesión de usuarios.
* Gestión de roles: administrador, profesional y cliente.
* Dashboard independiente para cada tipo de usuario.
* Creación de profesionales desde el panel administrador.
* Creación de servicios desde el panel administrador.
* Asociación de servicios con especialidades.
* Filtrado automático de profesionales según el servicio seleccionado.
* Modificación de precios de servicios desde el administrador.
* Configuración de horarios disponibles por profesional.
* Carga dinámica de horas disponibles según profesional y fecha.
* Agendamiento de citas por parte del cliente.
* Reprogramación de citas.
* Cancelación de citas.
* Confirmación de citas por parte del profesional.
* Finalización de citas por parte del profesional.
* Visualización de citas registradas.
* Recordatorios internos de próximas citas.
* Manejo centralizado de errores.
* Persistencia local con SQLite.
* Interfaz moderna con CSS modular.
* Mejoras de accesibilidad.
* Pruebas unitarias y de repositorio.

---

## Roles del sistema

### Administrador

El administrador puede:

* Ver el resumen general del sistema.
* Crear profesionales.
* Crear servicios.
* Asociar servicios a especialidades.
* Modificar precios de servicios.
* Consultar usuarios registrados.
* Consultar profesionales registrados.
* Consultar citas del sistema.

### Profesional

El profesional puede:

* Iniciar sesión.
* Consultar sus citas asignadas.
* Configurar horarios disponibles.
* Confirmar citas pendientes.
* Completar citas confirmadas.
* Cancelar citas cuando sea necesario.
* Ver recordatorios de próximas citas.

### Cliente

El cliente puede:

* Registrarse.
* Iniciar sesión.
* Seleccionar un servicio.
* Ver profesionales relacionados con ese servicio.
* Seleccionar fecha.
* Ver horas disponibles.
* Agendar citas.
* Consultar sus citas.
* Reprogramar citas.
* Cancelar citas.

---

## Flujo principal del cliente

El flujo de agendamiento funciona así:

1. El cliente selecciona un servicio.
2. El sistema carga los profesionales asociados a la especialidad del servicio.
3. El cliente selecciona un profesional.
4. El cliente selecciona una fecha.
5. El sistema carga las horas disponibles del profesional.
6. El cliente agenda la cita.
7. La cita queda registrada con estado inicial pendiente.

---

## Arquitectura del proyecto

El proyecto está organizado bajo una arquitectura por capas:

```txt
src/main/java/com/plataformacitas/
│
├── domain/
│   ├── model/
│   ├── enums/
│   └── exception/
│
├── application/
│   ├── service/
│   └── dto/
│
├── infrastructure/
│   ├── repository/
│   └── config/
│
└── presentation/
    ├── controller/
    └── exception/
```

### Capa domain

Contiene las entidades, enumeraciones y excepciones principales del sistema.

Ejemplos:

* Usuario
* Cliente
* Profesional
* Servicio
* Cita
* HorarioDisponible
* RolUsuario
* EstadoCita
* DiaSemana

### Capa application

Contiene los servicios de negocio y DTOs.

Ejemplos:

* AuthService
* CitaService
* ClienteService
* ProfesionalService
* ServicioService
* HorarioDisponibleService
* RecordatorioService

### Capa infrastructure

Contiene repositorios y configuración técnica.

Ejemplos:

* UsuarioRepository
* ClienteRepository
* ProfesionalRepository
* ServicioRepository
* CitaRepository
* HorarioDisponibleRepository
* DataInitializer

### Capa presentation

Contiene controladores web y controladores API.

Ejemplos:

* AuthController
* ClienteController
* ProfesionalController
* AdminController
* ApiController
* GlobalExceptionHandler

---

## Estructura de carpetas

```txt
PLATAFORMA_CITAS/
│
├── pom.xml
├── README.md
├── Procfile
├── .env.example
├── .gitignore
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/plataformacitas/
│   │   │       ├── PlataformaCitasApplication.java
│   │   │       ├── domain/
│   │   │       ├── application/
│   │   │       ├── infrastructure/
│   │   │       └── presentation/
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── img/
│   │       │
│   │       └── templates/
│   │
│   └── test/
│       └── java/
│           └── com/plataformacitas/
│
└── target/
```

---

## Estructura CSS

El frontend cuenta con una estructura modular de estilos:

```txt
src/main/resources/static/css/
│
├── main.css
│
├── base/
│   ├── variables.css
│   ├── reset.css
│   └── animations.css
│
├── components/
│   ├── badges.css
│   ├── buttons.css
│   ├── cards.css
│   ├── forms.css
│   ├── sidebar.css
│   └── tables.css
│
└── pages/
    ├── auth.css
    └── dashboard.css
```

---

## Interfaz de usuario

La interfaz fue mejorada con:

* Login visual.
* Registro visual.
* Dashboard moderno para administrador.
* Dashboard moderno para profesional.
* Dashboard moderno para cliente.
* Sidebar visual con rol activo.
* Tarjetas de resumen.
* Formularios organizados.
* Tablas mejoradas.
* Botones de acción.
* Mensajes de error y éxito.

---

## Accesibilidad

El sistema incluye mejoras de accesibilidad para facilitar su uso con teclado, lectores de pantalla y navegación asistida.

Se implementaron:

* Etiquetas `label` asociadas a sus campos mediante `for` e `id`.
* Atributos `aria-label`.
* Atributos `aria-live`.
* Roles `alert` y `status` para mensajes dinámicos.
* Campos obligatorios identificados con `aria-required`.
* Estados de error con `aria-invalid`.
* Encabezados de tabla con `scope="col"`.
* Clase `sr-only` para textos útiles para lectores de pantalla.
* Foco visible para navegación con teclado.
* Botón para saltar al contenido principal.
* Soporte para usuarios que prefieren reducir animaciones mediante `prefers-reduced-motion`.

Estas mejoras permiten una experiencia más clara, navegable y usable.

---

## Base de datos

El sistema utiliza **SQLite** como base de datos local y portable.

Archivo generado localmente:

```txt
plataforma_citas.db
```

Este archivo no debe subirse al repositorio, por eso debe estar incluido en `.gitignore`.

---

## Configuración del entorno

Archivo `.env.example` sugerido:

```env
SERVER_PORT=8080
APP_NAME=Plataforma Citas

DATABASE_URL=jdbc:sqlite:plataforma_citas.db
DATABASE_DRIVER=org.sqlite.JDBC

HIBERNATE_DIALECT=org.hibernate.community.dialect.SQLiteDialect
HIBERNATE_DDL_AUTO=update
SHOW_SQL=true

ADMIN_EMAIL=admin@citas.com
ADMIN_PASSWORD=123456

PROFESIONAL_EMAIL=profesional@citas.com
PROFESIONAL_PASSWORD=123456
```

---

## Configuración de `application.properties`

Ejemplo de configuración:

```properties
spring.application.name=${APP_NAME:Plataforma Citas}

server.port=${PORT:8080}

spring.datasource.url=${DATABASE_URL:jdbc:sqlite:plataforma_citas.db}
spring.datasource.driver-class-name=${DATABASE_DRIVER:org.sqlite.JDBC}

spring.jpa.database-platform=${HIBERNATE_DIALECT:org.hibernate.community.dialect.SQLiteDialect}
spring.jpa.hibernate.ddl-auto=${HIBERNATE_DDL_AUTO:update}
spring.jpa.show-sql=${SHOW_SQL:true}
spring.jpa.open-in-view=false
```

---

## Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/zabaisai/Plataforma_Citas.git
```

### 2. Entrar al proyecto

```bash
cd Plataforma_Citas
```

### 3. Compilar

```bash
mvn clean compile
```

### 4. Ejecutar pruebas

```bash
mvn clean test
```

### 5. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

### 6. Abrir en el navegador

```txt
http://localhost:8080/login
```

---

## Usuarios de prueba

### Administrador

```txt
Correo: admin@citas.com
Contraseña: 123456
```

### Profesional

```txt
Correo: profesional@citas.com
Contraseña: 123456
```

### Cliente

```txt
Correo: cliente@citas.com
Contraseña: 123456
```

También se puede crear un nuevo cliente desde la pantalla de registro.

---

## Endpoints principales

### Servicios

```http
GET /api/servicios
```

### Profesionales

```http
GET /api/profesionales
```

### Profesionales por servicio

```http
GET /api/servicios/{id}/profesionales
```

Este endpoint permite cargar únicamente los profesionales relacionados con la especialidad requerida por el servicio.

### Horarios disponibles

```http
GET /api/profesionales/{id}/horarios-disponibles?fecha=YYYY-MM-DD
```

Ejemplo:

```http
GET /api/profesionales/1/horarios-disponibles?fecha=2026-06-05
```

Respuesta esperada:

```json
["08:00", "08:30", "09:00", "09:30"]
```

### Citas

```http
GET /api/citas
```

### Crear cita

```http
POST /api/citas
```

Ejemplo de cuerpo:

```json
{
  "clienteId": 1,
  "profesionalId": 1,
  "servicioId": 1,
  "fecha": "2026-06-05",
  "hora": "08:00",
  "observaciones": "Primera consulta"
}
```

### Confirmar cita

```http
PATCH /api/citas/{id}/confirmar
```

### Cancelar cita

```http
PATCH /api/citas/{id}/cancelar
```

### Completar cita

```http
PATCH /api/citas/{id}/completar
```

---

## Validaciones implementadas

El sistema valida:

* Que los campos obligatorios estén completos.
* Que el precio de un servicio sea mayor a cero.
* Que la especialidad requerida del servicio exista.
* Que la hora final de un horario sea posterior a la hora inicial.
* Que el profesional tenga disponibilidad en el día seleccionado.
* Que no se agenden dos citas en la misma fecha y hora para el mismo profesional.
* Que el cliente solo vea horas realmente disponibles.
* Que no se puedan ejecutar acciones sobre recursos inexistentes.
* Que se manejen errores de dominio de forma controlada.

---

## Manejo de errores

El sistema cuenta con manejo centralizado de errores mediante excepciones personalizadas.

Ejemplos:

* CitaNoDisponibleException
* DatosInvalidosException
* HorarioNoDisponibleException
* RecursoNoEncontradoException
* UsuarioNoAutorizadoException

Ejemplo de respuesta:

```json
{
  "status": 400,
  "error": "DATOS_INVALIDOS",
  "mensaje": "La hora de fin debe ser posterior a la hora de inicio.",
  "timestamp": "2026-06-04T20:40:12"
}
```

---

## Pruebas

El proyecto incluye pruebas para validar la lógica principal.

Archivos de prueba:

* CitaServiceTest
* CitaRepositoryTest
* HorarioDisponibleServiceTest
* UsuarioServiceTest

Ejecutar pruebas:

```bash
mvn clean test
```

---

## Temas de POO2 aplicados

### Encapsulamiento

Las entidades protegen sus datos y exponen métodos controlados para modificar su estado.

Ejemplo:

```java
public void actualizarPrecio(Double nuevoPrecio) {
    if (nuevoPrecio == null || nuevoPrecio <= 0) {
        throw new IllegalArgumentException("El precio debe ser mayor a cero.");
    }

    this.precio = nuevoPrecio;
}
```

### Abstracción

La lógica del negocio se encuentra en servicios especializados, evitando que los controladores tengan reglas complejas.

### Separación de responsabilidades

Cada capa tiene una función clara:

* Modelos: representan entidades del dominio.
* Servicios: contienen lógica de negocio.
* Repositorios: gestionan acceso a datos.
* Controladores: reciben solicitudes web.
* DTOs: transportan información entre capas.

### Excepciones personalizadas

Se utilizan excepciones de dominio para representar errores del negocio.

### Persistencia

Se usa Spring Data JPA con SQLite para almacenar la información.

### Validación de disponibilidad

El sistema revisa horarios, citas ocupadas y disponibilidad antes de agendar.

---

## Despliegue

Para generar el `.jar`:

```bash
mvn clean package
```

Para ejecutar el `.jar`:

```bash
java -jar target/plataforma-citas-1.0.0.jar
```

Procfile sugerido:

```Procfile
web: java -Dserver.port=$PORT -jar target/plataforma-citas-1.0.0.jar
```

---

## Gitignore recomendado

```gitignore
target/
*.db
*.sqlite
*.sqlite3
.env
.DS_Store
```

Si estos archivos ya habían sido subidos antes de crear el `.gitignore`, se deben quitar del seguimiento de Git con:

```bash
git rm -r --cached target
git rm --cached plataforma_citas.db
git rm --cached .env
git add .gitignore
git commit -m "chore: stop tracking ignored files"
git push
```

---

## Estado actual del proyecto

El proyecto cuenta con:

* Autenticación.
* Registro de clientes.
* Roles diferenciados.
* Dashboard administrador.
* Dashboard profesional.
* Dashboard cliente.
* Gestión de profesionales.
* Gestión de servicios.
* Especialidades por servicio.
* Filtrado de profesionales por servicio.
* Gestión de precios desde administrador.
* Gestión de horarios por profesional.
* Carga dinámica de horarios disponibles.
* Agendamiento de citas.
* Reprogramación.
* Cancelación.
* Confirmación.
* Finalización.
* Recordatorios.
* Accesibilidad.
* Interfaz moderna.
* CSS modular.
* Pruebas.
* README actualizado.
* Configuración para ejecución local y despliegue.

---

## Autor

Proyecto desarrollado por:

**Isaí Zabala**

Materia:

**Programación Orientada a Objetos II**

Proyecto:

**Plataforma de Gestión de Citas**
