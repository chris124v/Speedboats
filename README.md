# Speedboats
The project consists of a distributed system using Micronaut microservices for managing community boat tours, where local guides publish tours and users can consult them, implementing small and well-isolated microservices within a monorepo.

It will execute entirely locally with SQLite and independent executable microservices.

## Authors
 * Isaac Villalobos Bonilla, 2024124285
 * Christopher Daniel Vargas Villalta, 2024108443
   
---
## Architecture
* Micronaut + microservices + REST
* Monorepo with multiple independent microservices
* Layered architecture per microservice: business logic, repositories, model
* Strict separation without sharing code between microservices
* React + Tailwind frontend consuming all microservices
* Each microservice with its own SQLite database

---

## Technology Stack

### Backend Services
* **Framework**: Micronaut (Java/Kotlin)
* **Build Tool**: Gradle
* **Language**: Java
* **HTTP Client**: Micronaut HTTP Client (for inter-service communication)
* **ORM/Database Access**: JDBC with SQLite Driver

### Frontend
* **Framework**: React
* **Styling**: Tailwind CSS
* **HTTP Client**: Fetch API or Axios
* **Node**: Node.js + npm

### Database
* **Engine**: SQLite (one per microservice)
* **File Format**: .sqlite3 format

### Development & Deployment
* **Build**: Gradle
* **Version Control**: Git
* **Scripting**: Bash (.sh) and PowerShell (.ps1)

---

## Database Architecture
* SQLite will be the database engine of choice for each microservice
* It will contain a small seeded dataset loaded automatically per service
* Structure:
  * **Tours Service**: Tours (id, nombre, ubicacion_lat, ubicacion_lng, precio, duracion, guia_id)
  * **Users Service**: Users (id, nombre, email, telefono, tipo)
  * **Bookings Service**: Bookings (id, tour_id, user_id, fecha_reserva, num_personas, estado)

--- 

## AI 

* We use GPT-5.2 for the coding implementation.

### Prompt

**Role**: Implement a functional system for speedboats tours in the repository of Speedboats using the README.

**Instructions**: Implement a functional system for the speedboats repository using the instructions of the detailed readme and the project folder structure in it.

**Context**: README and Ejercicio: Microservicios con Micronaut para tours en lanchas de personas locales de la comunidad sin permisos de turismo. Construyan un sistema distribuido donde guías locales publiquen tours en lanchas y usuarios puedan consultarlos, implementando microservicios pequeños y bien aislados dentro de un monorepo. Ejercicio en parejas.

Arquitectura: Micronaut + microservices + REST
Monorepo con múltiples microservicios independientes
Cada microservicio con sus propias capas: business logic, repositories, model
Separación estricta sin compartir código entre microservicios
Base de datos pequeña por servicio (puede ser H2 o SQLite)
Operación de escritura: POST /tours en el microservicio de tours para crear un tour con nombre, ubicación y precio
Operación de lectura: GET /tours para listar tours disponibles
Capacidad de desplegar y ejecutar cada microservicio por separado
Frontend: React + Tailwind consumiendo los microservicios
Scripts individuales por microservicio para dev y prod
Todo ejecutándose localmente con configuración mínima

**Output** : Functional system of the speedboats repository ready to run locally.


