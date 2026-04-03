# Architecture

## Overview
This monorepo contains 3 independent Micronaut microservices plus a React + Tailwind frontend.

- tours-service (8001): publishes and lists tours (SQLite)
- users-service (8002): manages users/profiles (SQLite)
- bookings-service (8003): creates reservations (SQLite) and validates references via HTTP calls to tours-service and users-service
- frontend (3000): consumes the microservices

## Separation
Each microservice has its own:
- codebase (`models`, `business_logic`, `repositories`, `services`, `controllers`)
- SQLite database file under its own `./data/` folder at runtime
- Gradle build and wrapper

There is no shared code between services.
