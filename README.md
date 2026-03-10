# ForoHub API 🗣️

En este proyecto se creó una API REST para la gestión de un foro de discusión, 
desarrollada como challenge del programa **ONE - Oracle Next Education** de Alura LATAM. 
Permite a los usuarios crear, consultar, actualizar y eliminar tópicos de discusión organizados por cursos, 
con autenticación segura mediante JWT.

## 👤 Autor
### Eric Herrera Romero

---

## 📋 Funcionalidades

- **Autenticación** con JWT — login seguro y acceso protegido a todos los endpoints
- **Tópicos** — CRUD completo con validaciones (no duplicados, curso activo, usuario activo)
- **Usuarios** — registro, listado, detalle y eliminación lógica
- **Cursos** — registro, listado, actualización y eliminación lógica
- **Paginación** en listados de tópicos, usuarios y cursos
- **Filtrado** de tópicos por nombre de curso y año
- **Documentación** interactiva con Swagger UI

---

## 🛠️ Tecnologías

- Java 21
- Spring Boot 4.0.3
- Spring Security 7
- Spring Data JPA + Hibernate
- MySQL
- Flyway — migraciones de base de datos
- JWT (auth0 java-jwt 4.5.1)
- Lombok
- SpringDoc OpenAPI 3.0.0 (Swagger)
- Maven

---

## 📁 Estructura del proyecto

```
src/main/java/com/aluracursos/forohub/
├── controller/
│   ├── AutenticacionController.java
│   ├── CursoController.java
│   ├── TopicoController.java
│   └── UsuarioController.java
├── domain/
│   ├── curso/
│   │   ├── validaciones/
│   │   │   ├── ValidadorCursoExistente.java
│   │   │   ├── ValidadorTopicoRepetido.java
│   │   │   ├── ValidadorUsuarioActivo.java
│   │   │   └── ValidadorInterface.java
│   │   ├── Curso.java
│   │   ├── CategoriaCurso.java
│   │   ├── CursoRepository.java
│   │   ├── DatosRegistroCurso.java
│   │   ├── DatosActualizarCurso.java
│   │   └── DatosDetalleCurso.java
│   ├── topico/
│   │   ├── Topico.java
│   │   ├── EstadoTopico.java
│   │   ├── TopicoRepository.java
│   │   ├── DatosRegistrarTopico.java
│   │   ├── DatosActualizarTopico.java
│   │   ├── DatosDetalleTopico.java
│   │   └── DatosListarTopico.java
│   ├── usuario/
│   │   ├── Usuario.java
│   │   ├── UsuarioRepository.java
│   │   ├── AutenticacionService.java
│   │   ├── DatosRegistrarUsuario.java
│   │   ├── DatosAutenticacionUsuario.java
│   │   ├── DatosDetalleUsuario.java
│   │   └── DatosListarUsuario.java
│   └── ValidacionException.java
└── infra/
    ├── exceptions/
    │   └── GestorDeErrores.java
    ├── security/
    │   ├── SecurityConfigurations.java
    │   ├── SecurityFilter.java
    │   ├── TokenService.java
    │   └── DatosTokenJWT.java
    └── springdoc/
        └── SpringDocConfiguration.java

src/main/resources/
├── db/migration/
│   ├── V1__create-table-usuarios.sql
│   ├── V2__create-table-cursos.sql
│   └── V3__create-table-topicos.sql
└── application.properties
```

---

## ⚙️ Configuración

### Requisitos previos
- Java 21
- MySQL 8+
- Maven

### Variables de entorno necesarias

| Variable | Descripción |
|---|---|
| `MYDB_PASSWORD` | Contraseña de MySQL |
| `JWT_SECRET` | Secret para firmar los tokens JWT |

### Base de datos

Crea la base de datos en MySQL:
```sql
CREATE DATABASE forohub;
```

Flyway ejecutará automáticamente las migraciones al arrancar la aplicación.

### `application.properties`

```properties
spring.application.name=challenge-forohub
spring.datasource.url=jdbc:mysql://localhost/forohub
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=${MYDB_PASSWORD}
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
api.security.token.secret=${JWT_SECRET:123456789}
spring.flyway.ignore-missing-migrations=true
```

---

## 🚀 Cómo ejecutar

```bash
# Clonar el repositorio
git clone https://github.com/tuusuario/forohub-api.git
cd forohub-api

# Configurar variables de entorno
export MYDB_PASSWORD=tu_password
export JWT_SECRET=tu_secret

# Ejecutar
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

---

## 📡 Endpoints

### Autenticación
| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| POST | `/login` | Iniciar sesión y obtener token JWT | ❌ |

### Tópicos
| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| POST | `/topicos` | Registrar nuevo tópico | ✅ |
| GET | `/topicos` | Listar tópicos activos (paginado) | ✅ |
| GET | `/topicos?curso=X&fecha=2026` | Filtrar por curso y año | ✅ |
| GET | `/topicos/{id}` | Detallar un tópico | ✅ |
| PUT | `/topicos/{id}` | Actualizar tópico | ✅ |
| DELETE | `/topicos/{id}` | Eliminar tópico (lógico) | ✅ |

### Usuarios
| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| POST | `/usuarios` | Registrar usuario | ✅ |
| GET | `/usuarios` | Listar usuarios activos | ✅ |
| GET | `/usuarios/{id}` | Detallar usuario | ✅ |
| DELETE | `/usuarios/{id}` | Eliminar usuario (lógico) | ✅ |

### Cursos
| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| POST | `/cursos` | Registrar curso | ✅ |
| GET | `/cursos` | Listar cursos | ✅ |
| PUT | `/cursos` | Actualizar curso | ✅ |
| DELETE | `/cursos/{id}` | Eliminar curso (lógico) | ✅ |

---

## 🔐 Autenticación

Todos los endpoints excepto `/login` requieren un token JWT en el header:

```
Authorization: Bearer <token>
```

Para obtener el token, realiza un POST a `/login` con un usuario registrado en la base de datos, por ejemplo:
```json
{
    "login": "usuario@forohub.com",
    "clave": "123456"
}
```

---

## 📖 Documentación Swagger

Con la aplicación corriendo, accede a la documentación interactiva en:

```
http://localhost:8080/swagger-ui/index.html
```

---


Desarrollado como parte del programa **ONE - Oracle Next Education** de [Alura LATAM](https://www.aluracursos.com/).
