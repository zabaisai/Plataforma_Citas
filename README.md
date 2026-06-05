# Plataforma de Gestión de Citas

Sistema web desarrollado en **Java con Spring Boot** para la gestión de citas entre clientes, profesionales y administradores. El proyecto permite registrar usuarios, administrar servicios, configurar horarios disponibles, agendar citas, reprogramarlas, cancelarlas y gestionar sus estados.

Este proyecto fue desarrollado como entrega académica para la materia **Programación Orientada a Objetos II (POO2)**, aplicando conceptos como arquitectura por capas, separación de responsabilidades, entidades de dominio, servicios, repositorios, DTOs, manejo de excepciones, validaciones y persistencia con base de datos.

---

## Tabla de contenido

* [Descripción general](#descripción-general)
* [Objetivo del proyecto](#objetivo-del-proyecto)
* [Tecnologías utilizadas](#tecnologías-utilizadas)
* [Características principales](#características-principales)
* [Roles del sistema](#roles-del-sistema)
* [Arquitectura del proyecto](#arquitectura-del-proyecto)
* [Temas de POO2 aplicados](#temas-de-poo2-aplicados)
* [Estructura de carpetas](#estructura-de-carpetas)
* [Base de datos](#base-de-datos)
* [Configuración del entorno](#configuración-del-entorno)
* [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
* [Usuarios de prueba](#usuarios-de-prueba)
* [Flujo de uso](#flujo-de-uso)
* [Endpoints API](#endpoints-api)
* [Pruebas](#pruebas)
* [Despliegue](#despliegue)
* [Autor](#autor)

---

## Descripción general

La Plataforma de Gestión de Citas es una aplicación web que permite administrar el proceso completo de agendamiento entre clientes y profesionales.

El sistema permite que los profesionales definan sus horarios disponibles, que los clientes seleccionen un servicio, un profesional y una fecha, y que el sistema cargue automáticamente las horas disponibles para realizar la reserva.

Además, el administrador puede gestionar usuarios, profesionales, servicios, precios y visualizar las citas registradas dentro del sistema.

---

## Objetivo del proyecto

Desarrollar una plataforma web funcional para la gestión de citas, aplicando buenas prácticas de programación orientada a objetos y arquitectura de software.

El sistema busca resolver la necesidad de organizar citas de manera estructurada, evitando cruces de horarios, permitiendo la administración de estados y centralizando la información de clientes, profesionales, servicios y reservas.

---

## Tecnologías utilizadas

* **Java 21**
* **Spring Boot**
* **Spring MVC**
* **Spring Data JPA**
* **Hibernate**
* **SQLite**
* **Thymeleaf**
* **HTML5**
* **CSS3**
* **JavaScript**
* **Maven**
* **JUnit**
* **Mockito**
* **Git y GitHub**

---

## Características principales

* Registro e inicio de sesión de usuarios.
* Roles diferenciados: cliente, profesional y administrador.
* Panel visual para cada tipo de usuario.
* Registro de clientes.
* Registro de profesionales desde el panel administrador.
* Creación de servicios desde el panel administrador.
* Modificación de precios de servicios desde el panel administrador.
* Configuración de horarios disponibles por parte del profesional.
* Carga dinámica de horas disponibles para el cliente.
* Agendamiento de citas.
* Validación de disponibilidad antes de guardar una cita.
* Cancelación de citas.
* Reprogramación de citas.
* Confirmación y finalización de citas por parte del profesional.
* Recordatorios internos de citas próximas.
* Manejo centralizado de excepciones.
* Persistencia con SQLite.
* Interfaz web modular con CSS y JavaScript.

---

## Roles del sistema

### Cliente

El cliente puede:

* Registrarse en el sistema.
* Iniciar sesión.
* Consultar servicios disponibles.
* Seleccionar un profesional.
* Seleccionar una fecha.
* Ver las horas disponibles del profesional.
* Agendar una cita.
* Reprogramar una cita.
* Cancelar una cita.
* Ver sus citas agendadas.

### Profesional

El profesional puede:

* Iniciar sesión.
* Configurar sus horarios disponibles.
* Ver sus citas asignadas.
* Confirmar citas pendientes.
* Completar citas confirmadas.
* Cancelar citas cuando sea necesario.
* Consultar recordatorios de citas próximas.

### Administrador

El administrador puede:

* Iniciar sesión.
* Ver usuarios registrados.
* Crear profesionales.
* Crear servicios.
* Modificar precios de servicios.
* Consultar profesionales registrados.
* Consultar servicios disponibles.
* Ver todas las citas del sistema.

---

## Arquitectura del proyecto

El proyecto está organizado bajo una arquitectura por capas, separando responsabilidades para mejorar el mantenimiento, la escalabilidad y la claridad del código.

### Capas principales

#### Domain

Contiene las entidades principales del negocio, enumeraciones y excepciones del dominio.

Ejemplos:

* `Usuario`
* `Cliente`
* `Profesional`
* `Servicio`
* `Cita`
* `HorarioDisponible`
* `EstadoCita`
* `RolUsuario`
* `DiaSemana`

#### Application

Contiene la lógica de negocio mediante servicios y DTOs.

Ejemplos:

* `AuthService`
* `CitaService`
* `ClienteService`
* `ProfesionalService`
* `ServicioService`
* `HorarioDisponibleService`
* `RecordatorioService`

#### Infrastructure

Contiene la persistencia y configuración técnica del sistema.

Ejemplos:

* `UsuarioRepository`
* `ClienteRepository`
* `ProfesionalRepository`
* `ServicioRepository`
* `CitaRepository`
* `HorarioDisponibleRepository`
* `SecurityConfig`
* `DataInitializer`

#### Presentation

Contiene los controladores web y el manejo de las rutas.

Ejemplos:

* `AuthController`
* `ClienteController`
* `ProfesionalController`
* `AdminController`
* `ApiController`
* `GlobalExceptionHandler`

---

## Temas de POO2 aplicados

Este proyecto aplica diferentes conceptos vistos en Programación Orientada a Objetos II:

### 1. Encapsulamiento

Las entidades del dominio manejan sus atributos de forma privada y exponen métodos para acceder o modificar información de manera controlada.

Ejemplo:

```java
public void actualizarPrecio(Double nuevoPrecio) {
    if (nuevoPrecio == null || nuevoPrecio <= 0) {
        throw new IllegalArgumentException("El precio debe ser mayor a cero.");
    }

    this.precio = nuevoPrecio;
}
```

### 2. Abstracción

El sistema separa la lógica de negocio en servicios especializados, evitando que los controladores contengan reglas internas del dominio.

Ejemplo:

* `CitaService` gestiona reglas de citas.
* `HorarioDisponibleService` gestiona disponibilidad.
* `ServicioService` gestiona servicios y precios.

### 3. Separación de responsabilidades

Cada clase tiene una responsabilidad clara:

* Los modelos representan entidades.
* Los servicios ejecutan lógica de negocio.
* Los repositorios acceden a la base de datos.
* Los controladores reciben solicitudes web.
* Los DTOs transportan información entre capas.

### 4. Manejo de excepciones

Se implementó una jerarquía de excepciones del dominio mediante la clase base `DominioException`.

Ejemplos:

* `CitaNoDisponibleException`
* `DatosInvalidosException`
* `HorarioNoDisponibleException`
* `RecursoNoEncontradoException`
* `UsuarioNoAutorizadoException`

### 5. Persistencia de datos

Se utiliza Spring Data JPA con SQLite para almacenar usuarios, clientes, profesionales, servicios, horarios y citas.

### 6. Validaciones de negocio

El sistema valida condiciones como:

* No crear citas en fechas pasadas.
* No crear horarios donde la hora final sea menor que la hora inicial.
* No agendar citas fuera del horario disponible del profesional.
* No agendar dos citas en la misma fecha y hora para el mismo profesional.
* No permitir precios menores o iguales a cero.

### 7. DTOs

Se implementan DTOs para transportar datos sin exponer directamente las entidades del dominio.

Ejemplos:

* `UsuarioDTO`
* `ServicioDTO`
* `CitaDTO`
* `HorarioDisponibleDTO`
* `ReprogramarCitaDTO`

---

## Estructura de carpetas

```txt
Plataforma_Citas/
│
├── pom.xml
├── README.md
├── Procfile
├── .env.example
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/plataformacitas/
│   │   │       ├── PlataformaCitasApplication.java
│   │   │       │
│   │   │       ├── domain/
│   │   │       │   ├── model/
│   │   │       │   ├── enums/
│   │   │       │   └── exception/
│   │   │       │
│   │   │       ├── application/
│   │   │       │   ├── service/
│   │   │       │   └── dto/
│   │   │       │
│   │   │       ├── infrastructure/
│   │   │       │   ├── repository/
│   │   │       │   └── config/
│   │   │       │
│   │   │       └── presentation/
│   │   │           ├── controller/
│   │   │           └── exception/
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
```

---

## Base de datos

El proyecto utiliza **SQLite**, una base de datos liviana y portable. Esto permite ejecutar el sistema en cualquier computador sin necesidad de instalar un motor de base de datos externo como MySQL o PostgreSQL.

Archivo de base de datos generado:

```txt
plataforma_citas.db
```

Este archivo no debe subirse al repositorio, por eso se recomienda incluirlo en `.gitignore`.

---

## Configuración del entorno

El archivo `.env.example` contiene un ejemplo de variables de entorno:

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

El archivo `application.properties` puede usar estas variables con valores por defecto:

```properties
spring.application.name=${APP_NAME:Plataforma Citas}

server.port=${SERVER_PORT:8080}

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

### 2. Entrar a la carpeta del proyecto

```bash
cd Plataforma_Citas
```

### 3. Compilar el proyecto

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

El cliente puede registrarse desde:

```txt
http://localhost:8080/registro
```

---

## Flujo de uso

### Flujo del profesional

1. Iniciar sesión como profesional.
2. Entrar al panel profesional.
3. Ir a la sección “Mis horarios”.
4. Registrar horarios disponibles por día.
5. Consultar citas asignadas.
6. Confirmar, cancelar o completar citas.

### Flujo del cliente

1. Registrarse como cliente.
2. Iniciar sesión.
3. Seleccionar un servicio.
4. Seleccionar un profesional.
5. Seleccionar una fecha.
6. El sistema carga automáticamente las horas disponibles.
7. Seleccionar una hora.
8. Agendar la cita.
9. Reprogramar o cancelar si es necesario.

### Flujo del administrador

1. Iniciar sesión como administrador.
2. Crear profesionales.
3. Crear servicios.
4. Modificar precios.
5. Consultar usuarios.
6. Consultar citas del sistema.

---

## Endpoints API

El sistema incluye endpoints internos para consultar información del sistema.

### Listar servicios

```http
GET /api/servicios
```

### Listar profesionales

```http
GET /api/profesionales
```

### Listar citas

```http
GET /api/citas
```

### Consultar horarios disponibles de un profesional

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

### Crear cita desde API

```http
POST /api/citas
```

Cuerpo de ejemplo:

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

## Pruebas

El proyecto incluye pruebas unitarias para validar parte de la lógica principal.

Ejemplos de pruebas:

* `CitaServiceTest`
* `UsuarioServiceTest`
* `CitaRepositoryTest`
* `HorarioDisponibleServiceTest`

Para ejecutar las pruebas:

```bash
mvn clean test
```

---

## Manejo de errores

El sistema cuenta con manejo de errores centralizado mediante `GlobalExceptionHandler`.

Las excepciones del dominio permiten controlar errores como:

* Cita no disponible.
* Datos inválidos.
* Horario no disponible.
* Recurso no encontrado.
* Usuario no autorizado.

Ejemplo de respuesta de error:

```json
{
  "timestamp": "2026-06-04T20:40:12",
  "status": 400,
  "error": "DATOS_INVALIDOS",
  "mensaje": "La hora de fin debe ser posterior a la hora de inicio."
}
```

---

## Validaciones importantes

El sistema valida que:

* La hora final de un horario sea posterior a la hora inicial.
* Una cita no sea creada en una fecha pasada.
* El profesional tenga horario disponible para la fecha seleccionada.
* No exista otra cita en la misma fecha y hora para el mismo profesional.
* Los precios de los servicios sean mayores a cero.
* Los usuarios tengan roles definidos.
* El cliente solo pueda seleccionar horas disponibles.

---

## Frontend

El frontend está construido con HTML, CSS y JavaScript modular.

### CSS

La estructura CSS está separada por responsabilidad:

```txt
static/css/
├── main.css
├── base/
│   ├── variables.css
│   ├── reset.css
│   └── animations.css
├── components/
│   ├── buttons.css
│   ├── forms.css
│   ├── cards.css
│   ├── tables.css
│   ├── badges.css
│   └── sidebar.css
└── pages/
    ├── auth.css
    └── dashboard.css
```

### JavaScript

La estructura JavaScript también se encuentra modularizada:

```txt
static/js/
├── main.js
├── modules/
│   ├── alerts.js
│   ├── forms.js
│   ├── sidebar.js
│   └── citas.js
└── utils/
    └── helpers.js
```

El módulo `citas.js` permite cargar dinámicamente los horarios disponibles de un profesional según la fecha seleccionada.

---

## Despliegue

El proyecto está preparado para despliegue usando Maven y Spring Boot.

Archivo `Procfile` sugerido:

```txt
web: java -jar target/plataforma-citas-0.0.1-SNAPSHOT.jar
```

Para generar el `.jar`:

```bash
mvn clean package
```

Para ejecutar el `.jar`:

```bash
java -jar target/plataforma-citas-0.0.1-SNAPSHOT.jar
```

---

## Recomendaciones para Git

Se recomienda tener este contenido en `.gitignore`:

```gitignore
target/
*.db
*.sqlite
*.sqlite3
.env
.DS_Store
```

Esto evita subir archivos generados, bases de datos locales y configuraciones privadas.

---

## Estado del proyecto

El sistema cuenta con las funcionalidades principales implementadas:

* Login y registro.
* Roles.
* Dashboard por usuario.
* Gestión de servicios.
* Gestión de profesionales.
* Configuración de horarios.
* Carga de horarios disponibles.
* Agendamiento.
* Reprogramación.
* Cancelación.
* Confirmación.
* Finalización.
* Modificación de precios desde administrador.
* Manejo de errores.
* Pruebas unitarias.
* Interfaz web modular.

---

## Autor

Proyecto desarrollado por:

**Isaí Zabala**

Materia:

**Programación Orientada a Objetos II**

Proyecto:

**Plataforma de Gestión de Citas**
