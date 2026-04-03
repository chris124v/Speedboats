# API Specification

## Tours Service (8001)
Base: `http://localhost:8001`

- `GET /api/tours` → list tours
- `POST /api/tours` → create tour
- `GET /api/tours/{id}` → get tour by id (used by bookings-service)

Create payload:
```json
{
  "nombre": "Tour Manglares",
  "ubicacionLat": 9.963,
  "ubicacionLng": -84.778,
  "precio": 55.0,
  "duracion": 90,
  "guiaId": 1
}
```

## Users Service (8002)
Base: `http://localhost:8002`

- `GET /api/users` → list users
- `POST /api/users` → create user
- `GET /api/users/{id}` → get user by id (used by bookings-service)

Create payload:
```json
{
  "nombre": "Usuario Demo",
  "email": "usuario@example.com",
  "telefono": "8888-0002",
  "tipo": "USER"
}
```

## Bookings Service (8003)
Base: `http://localhost:8003`

- `GET /api/bookings` → list bookings
- `POST /api/bookings` → create booking
- `GET /api/bookings/{id}` → get booking by id

Create payload (date format `YYYY-MM-DD`):
```json
{
  "tourId": 1,
  "userId": 2,
  "fechaReserva": "2026-04-03",
  "numPersonas": 2,
  "estado": "PENDING"
}
```

If the referenced tour/user does not exist, the service responds with `400`.
