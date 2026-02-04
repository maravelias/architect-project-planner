# AGENTS.md

## Persona
You are a senior developer working on a Jmix + Spring Boot + Vaadin Flow (Vaadin 24) application. Prioritize maintainability, security, and clear database migrations.

## Project Overview
- **Stack**: Jmix 2.7.3, Spring Boot, Vaadin Flow UI (Vaadin 24), EclipseLink ORM, Liquibase migrations.
- **Frontend build**: Vaadin-managed Vite toolchain with `package.json` pinned to Vaadin 24.9.2 and React deps (used by Vaadin Flow tooling).
- **Database**: Default HSQLDB file DB for local use; PostgreSQL configured for the `dev` profile.

## Key Entry Points
- **App**: `src/main/java/eu/maravelias/architectprojectplanner/ArchitectProjectPlannerApplication.java`
  - `@SpringBootApplication`, Vaadin `@Theme` and `@PWA` annotations.
  - Registers a primary `DataSource` and logs application URL on startup.
- **UI**: Vaadin Flow UI with Jmix FlowUI views
  - Main view: `src/main/java/eu/maravelias/architectprojectplanner/view/main/MainView.java`
  - Login view: `src/main/java/eu/maravelias/architectprojectplanner/view/login/LoginView.java`
  - User management views: `src/main/java/eu/maravelias/architectprojectplanner/view/user/UserListView.java`, `UserDetailView.java`
  - XML descriptors live under `src/main/resources/eu/maravelias/architectprojectplanner/view/**`
- **Menu**: `src/main/resources/eu/maravelias/architectprojectplanner/menu.xml`

## Security & Auth
- **Jmix Security**: `FullAccessRole` and `UiMinimalRole` in `src/main/java/eu/maravelias/architectprojectplanner/security/`
- **Custom user repository**: `DatabaseUserRepository` uses `app_User` as the user entity.
- **Security configuration**: `ArchitectProjectPlannerSecurityConfiguration` adds a public `/public/**` filter chain.
- **OIDC**: `application-dev.properties` enables Jmix OIDC and Keycloak client settings.

## Data Model & Migrations
- **User entity**: `src/main/java/eu/maravelias/architectprojectplanner/entity/User.java` (`app_User` table)
- **Liquibase master changelog**: `src/main/resources/eu/maravelias/architectprojectplanner/liquibase/changelog.xml`
- **App changelogs**: `src/main/resources/eu/maravelias/architectprojectplanner/liquibase/changelog/`
  - `010-init-user.xml` creates `APP_USER` and seeds admin user + role assignment.

## Configuration Profiles
- `src/main/resources/application.properties`
  - Sets `spring.profiles.active=dev`
  - Default DB: `jdbc:hsqldb:file:.jmix/hsqldb/app`
- `src/main/resources/application-dev.properties`
  - PostgreSQL at `jdbc:postgresql://192.168.57.10:5432/archplan`
  - Default DB credentials and OIDC settings are **development-only**.

## Build & Run (Gradle)
- Wrapper scripts: `./gradlew` (Linux/macOS) or `gradlew.bat` (Windows)
- Typical tasks:
  - `./gradlew bootRun` to start the app
  - `./gradlew test` for tests
- Vaadin plugin is enabled; Hilla is disabled via `hilla.active=false` in `gradle.properties` and dependency excludes in `build.gradle`.

## Frontend Tooling
- `package.json` is Vaadin-managed with pinned versions (Vaadin 24.9.2, Vite 6.x, React 18.x).
- Frontend build artifacts are typically generated under `src/main/frontend/generated/` and excluded by the IDE configuration.

## Conventions & Notes
- **FlowUI views**: Each `@ViewController` has a matching XML descriptor under `src/main/resources/eu/.../view/...`.
- **Data access**: Use Jmix data APIs and Liquibase for schema changes; avoid ad-hoc SQL migrations.
- **Security**: Role definitions are in code. Keep UI/menu IDs in sync with view IDs.
- **Entity changes**: Whenever entities change, update or add the corresponding tests.

## Sensitive Defaults (Do Not Use in Production)
- `application-dev.properties` includes a PostgreSQL password and OIDC client secret placeholders.
- `build.gradle` contains a Sonar token. Treat it as sensitive and avoid sharing.
- Default UI login credentials are `admin/admin` (seeded in Liquibase). Rotate or disable for production.

## Suggested First Steps for New Work
- Read the view XML and corresponding controllers for UI changes.
- Update Liquibase changelog for any entity/schema changes.
- Verify security policies (roles, views, menus) when adding new screens or APIs.
