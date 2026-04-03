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


---
## Workflow 1: Create and Publish a Tour
* Step 1: Guide accesses form on frontend `GET /tours/create`
* Step 2: Guide fills in tour details (name, location, price, duration)
* Step 3: Frontend submits via `POST http://localhost:8001/api/tours` (JSON)
* Step 4: Tours Microservice validates through business logic layer
* Step 5: Repository layer inserts into SQLite
* Step 6: Tours Service responds with created tour data

---
## Workflow 2: Browse and Book Tours
* Step 1: User accesses frontend `GET /tours`
* Step 2: Frontend calls `GET http://localhost:8001/api/tours`
* Step 3: Tours Service queries SQLite and returns JSON list
* Step 4: User can filter by price, location, capacity
* Step 5: User clicks "Book Tour" and submits booking form
* Step 6: Frontend calls `POST http://localhost:8003/api/bookings` (JSON)
* Step 7: Bookings Microservice validates and inserts into SQLite
* Step 8: Frontend displays booking confirmation

---
## Microservices
* **Tours Service** (Port 8001): Manage tour publications, GET/POST /api/tours
* **Users Service** (Port 8002): Manage user profiles, GET/POST /api/users
* **Bookings Service** (Port 8003): Manage reservations, communicates with Tours and Users via HTTP
* **Frontend** (Port 3000): React + Tailwind, consumes all microservices

---
## Layered Design
* The microservices follow a layered architecture per service
* **Business Logic layer**: Data type validations, price calculations, booking rules
* **Repositories layer**: SQLite communication and query operations
* **Model layer**: Defines the structure of entities (Tour, User, Booking, Review)
* **Controller layer**: HTTP endpoint handling and JSON response formatting
* Each microservice is completely independent with its own implementation

---
## Paradigm
* **REST-Based Architecture**: All communication via stateless HTTP REST
* **Microservices Pattern**: Each service is autonomous with its own data and logic
* **Service Independence**: No shared code between microservices
* **Database per Service**: Each microservice owns its SQLite database
* **Distributed System**: Services communicate via HTTP, can fail independently
---

## Run Locally

### Prerequisites
* Java 21 (already required by the backend)
* Node.js 18+ (for the frontend)

### Start everything (Windows PowerShell)
From the `Speedboats/` folder:

```powershell
./scripts/start-all.ps1
```

### Start services individually

```powershell
./scripts/start-tours.ps1
./scripts/start-users.ps1
./scripts/start-bookings.ps1
./scripts/start-frontend.ps1
```

### URLs
* Frontend: http://localhost:3000 
* Tours API: http://localhost:8001/api/tours
* Users API: http://localhost:8002/api/users
* Bookings API: http://localhost:8003/api/bookings

### Notes
* Backend scripts download a local Gradle distribution the first time they run (no global Gradle install needed).
* On Windows, Gradle builds use a short output path by default (`C:/speedboats-build/...`) to avoid path-length issues; override with `SPEEDBOATS_BUILD_ROOT` if needed.
* Each service creates its own SQLite DB automatically under its own `./data/` folder when it starts.
* `tours-service` and `users-service` seed demo data the first time they run.
* `bookings-service` validates `tourId` and `userId` by calling the other services.
* If you run `./scripts/start-all.ps1` multiple times, it will skip services whose ports are already in use and will print the PID using that port.
* To restart a service, close the PowerShell window that started it, or stop the PID shown by `start-all.ps1` (e.g., `Stop-Process -Id <pid>`).
* If `npm` is not installed / not on PATH, the frontend will be skipped. Install Node.js LTS (includes `npm`) and rerun `./scripts/start-frontend.ps1`