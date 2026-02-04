[![CI-Tests](https://github.com/maravelias/architect-project-planner/actions/workflows/ci.yml/badge.svg)](https://github.com/maravelias/architect-project-planner/actions/workflows/ci.yml)

# Architect Project Planner

Jmix 2.7.3 + Spring Boot + Vaadin Flow (Vaadin 24) application for planning projects and managing related data.
Uses EclipseLink ORM and Liquibase migrations, with Vaadin-managed frontend assets.

## Summary

- Jmix-based backend with FlowUI views for user and project management.
- Liquibase-managed schema migrations and seeded admin user.
- Default local DB is HSQLDB; dev profile targets PostgreSQL.

## Development Setup

### Prerequisites

- Java 21
- Git

### Run Locally (default HSQLDB)

```bash
./gradlew bootRun
```

Open the app at the URL printed in the startup logs. Default UI credentials:
- Username: `admin`
- Password: `admin`

### Run Tests

```bash
./gradlew test
```

### Dev Profile (PostgreSQL)

The `dev` profile is enabled by default in `src/main/resources/application.properties` and expects:
- PostgreSQL at `jdbc:postgresql://192.168.57.10:5432/archplan`
- Credentials from `src/main/resources/application-dev.properties`

If you want to use local HSQLDB instead, disable the `dev` profile or override `spring.profiles.active`.

### Frontend

Vaadin manages frontend tooling. `package.json` is pinned by Vaadin 24 and should not be manually upgraded.
