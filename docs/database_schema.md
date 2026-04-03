# Database Schema

Each microservice owns its SQLite database and creates tables on startup.

## tours-service
Table: `tours`
- `id` INTEGER PK AUTOINCREMENT
- `nombre` TEXT NOT NULL
- `ubicacion_lat` REAL NOT NULL
- `ubicacion_lng` REAL NOT NULL
- `precio` REAL NOT NULL
- `duracion` INTEGER NOT NULL
- `guia_id` INTEGER NOT NULL

## users-service
Table: `users`
- `id` INTEGER PK AUTOINCREMENT
- `nombre` TEXT NOT NULL
- `email` TEXT NOT NULL
- `telefono` TEXT NOT NULL
- `tipo` TEXT NOT NULL (GUIDE|USER)

## bookings-service
Table: `bookings`
- `id` INTEGER PK AUTOINCREMENT
- `tour_id` INTEGER NOT NULL
- `user_id` INTEGER NOT NULL
- `fecha_reserva` TEXT NOT NULL (ISO date)
- `num_personas` INTEGER NOT NULL
- `estado` TEXT NOT NULL (PENDING|CONFIRMED|CANCELLED)
