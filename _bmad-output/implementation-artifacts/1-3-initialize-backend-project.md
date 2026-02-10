# Story 1.3: Initialize Backend Project

Status: done

## Story

As a **developer**,
I want **a Spring Boot backend with JPA, Security, and Flyway initialized**,
so that **I can start building API endpoints with the chosen stack**.

## Acceptance Criteria

1. **AC1:** `backend/` contains a Spring Boot 4.0.2 Maven project
2. **AC2:** Dependencies include: Web, JPA, Security, PostgreSQL, Validation, Lombok
3. **AC3:** `application.yml` exists with proper configuration structure
4. **AC4:** `application-dev.yml` exists pointing to Docker PostgreSQL (localhost:5432)
5. **AC5:** springdoc-openapi dependency is added for Swagger UI
6. **AC6:** Flyway dependency is added for database migrations
7. **AC7:** `./mvnw spring-boot:run` starts the application on port 8080
8. **AC8:** Swagger UI is accessible at `/swagger-ui.html`

## Tasks / Subtasks

- [x] **Task 1: Add missing dependencies to pom.xml** (AC: 5, 6)
  - [x] Add springdoc-openapi-starter-webmvc-ui dependency
  - [x] Add Flyway dependency (flyway-core and flyway-database-postgresql)
  - [x] Verify all dependencies are compatible with Spring Boot 4.0.2

- [x] **Task 2: Create application.yml configuration** (AC: 3)
  - [x] Convert/replace application.properties to application.yml
  - [x] Configure server port (8080)
  - [x] Configure JPA/Hibernate settings (ddl-auto: none for Flyway-managed schema)
  - [x] Configure OpenAPI/Swagger settings
  - [x] Configure logging levels
  - [x] Set up active profile system

- [x] **Task 3: Create application-dev.yml profile** (AC: 4)
  - [x] Configure PostgreSQL datasource for Docker (localhost:5432)
  - [x] Set database name: spotifou
  - [x] Configure credentials (spotifou/change_me - matching existing Docker config)
  - [x] Enable SQL logging for debugging

- [x] **Task 4: Create backend folder structure** (AC: 1)
  - [x] Create `src/main/java/com/ayaco/spotifou/controller/` (kept existing package)
  - [x] Create `src/main/java/com/ayaco/spotifou/service/`
  - [x] Create `src/main/java/com/ayaco/spotifou/repository/`
  - [x] Create `src/main/java/com/ayaco/spotifou/model/`
  - [x] Create `src/main/java/com/ayaco/spotifou/dto/request/`
  - [x] Create `src/main/java/com/ayaco/spotifou/dto/response/`
  - [x] Create `src/main/java/com/ayaco/spotifou/config/`
  - [x] Create `src/main/java/com/ayaco/spotifou/security/`
  - [x] Create `src/main/java/com/ayaco/spotifou/exception/`
  - [x] Create `src/main/resources/db/migration/` for Flyway

- [x] **Task 5: Create initial Flyway migration** (AC: 6)
  - [x] Create `V1__initial_schema.sql` with placeholder statement
  - [x] Verify Flyway can run on application startup

- [x] **Task 6: Create OpenAPI configuration** (AC: 8)
  - [x] Create `OpenApiConfig.java` in config package
  - [x] Configure API title, version, description
  - [x] Configure server URL for local development

- [x] **Task 7: Disable Spring Security for initial setup** (AC: 7, 8)
  - [x] Create temporary `SecurityConfig.java` that permits all requests
  - [x] This allows testing without authentication (will be replaced in Epic 2)

- [x] **Task 8: Verify setup** (AC: 7, 8)
  - [x] Ensure Docker PostgreSQL is running
  - [x] Run `./mvnw spring-boot:run -Dspring.profiles.active=dev`
  - [x] Verify application starts on port 8080
  - [x] Verify Swagger UI at `http://localhost:8080/swagger-ui/index.html`
  - [x] Verify Flyway migration setup is correct

## Dev Notes

### CRITICAL: Package Name Change Required

The existing backend uses package `com.ayaco.spotifou` but the architecture specifies `com.spotifou`. You must either:

**Option A (Recommended):** Keep existing package `com.ayaco.spotifou`
- Simpler, less refactoring
- Update architecture references mentally

**Option B:** Refactor to `com.spotifou`
- Requires renaming `SpotifouApplication.java` location
- Update `pom.xml` groupId

**Recommendation:** Use Option A - keep `com.ayaco.spotifou` to avoid unnecessary refactoring.

### Architecture Compliance

**From Architecture Document:**
- Backend: Spring Boot 4.0.2, Spring Security 7, Spring Data JPA
- Database: PostgreSQL with Flyway migrations
- API Documentation: OpenAPI/Swagger via springdoc-openapi
- API Prefix: All endpoints under `/api/v1/`
- Error Format: RFC 7807 Problem Details

### Previous Story Learnings (Story 1.2)

**From 1-2-initialize-frontend-project.md:**
- Frontend folder structure was created successfully
- Environment configuration pattern established (.env.example + .env)
- Tailwind CSS 4 and Shadcn/ui are configured
- Path alias `@/` → `src/` is working

### Technical Requirements

**Dependencies to Add to pom.xml:**

```xml
<!-- OpenAPI/Swagger -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.4</version>
</dependency>

<!-- Flyway -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-database-postgresql</artifactId>
</dependency>
```

**application.yml Structure:**

```yaml
spring:
  application:
    name: spotifou
  profiles:
    active: dev
  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        format_sql: true
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true

server:
  port: 8080
  servlet:
    context-path: /

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true

logging:
  level:
    root: INFO
    com.ayaco.spotifou: DEBUG
    org.springframework.security: DEBUG
```

**application-dev.yml Structure:**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/spotifou
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: validate
```

### Folder Structure Requirements

**Required structure after this story:**

```
backend/
├── pom.xml                                  # Updated with new dependencies
├── mvnw / mvnw.cmd
├── Dockerfile
│
└── src/
    ├── main/
    │   ├── java/com/ayaco/spotifou/
    │   │   ├── SpotifouApplication.java     # Existing
    │   │   │
    │   │   ├── config/
    │   │   │   ├── OpenApiConfig.java       # NEW
    │   │   │   └── SecurityConfig.java      # NEW (temporary permit-all)
    │   │   │
    │   │   ├── controller/                  # Empty, ready for Epic 2+
    │   │   ├── service/                     # Empty, ready for Epic 2+
    │   │   ├── repository/                  # Empty, ready for Epic 2+
    │   │   ├── model/                       # Empty, ready for Epic 2+
    │   │   ├── dto/
    │   │   │   ├── request/                 # Empty
    │   │   │   └── response/                # Empty
    │   │   ├── security/                    # Empty, ready for Epic 2
    │   │   └── exception/                   # Empty, ready for Epic 8
    │   │
    │   └── resources/
    │       ├── application.yml              # NEW (replaces .properties)
    │       ├── application-dev.yml          # NEW
    │       └── db/migration/
    │           └── V1__initial_schema.sql   # NEW (placeholder)
    │
    └── test/
        └── java/com/ayaco/spotifou/
            └── SpotifouApplicationTests.java  # Existing
```

### OpenApiConfig.java Template

```java
package com.ayaco.spotifou.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI spotifouOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Spotifou API")
                .description("Backend API for Spotifou music streaming application")
                .version("1.0.0"))
            .servers(List.of(
                new Server().url("http://localhost:8080").description("Local development")
            ));
    }
}
```

### SecurityConfig.java Template (Temporary)

```java
package com.ayaco.spotifou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
```

**Note:** This is a TEMPORARY configuration. It will be replaced with proper OAuth2/JWT security in Epic 2 (Stories 2.1-2.2).

### Initial Flyway Migration (Placeholder)

**V1__initial_schema.sql:**

```sql
-- Spotifou Initial Schema
-- This is a placeholder migration. Actual schema will be created in Epic 2+.

-- Schema version tracking comment
-- V1: Initial setup - no tables yet
```

### Docker PostgreSQL Requirements

**Ensure docker-compose.yml (from Story 1.1) has:**

```yaml
services:
  postgres:
    image: postgres:16
    environment:
      POSTGRES_DB: spotifou
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

**Before running backend:**
```bash
# Start PostgreSQL
docker-compose up -d

# Verify it's running
docker ps
```

### Critical Implementation Rules

**From project-context.md:**

1. **Lombok required** - Use `@Data`, `@Builder`, `@RequiredArgsConstructor`
2. **Constructor injection** - Use `@RequiredArgsConstructor` instead of `@Autowired` on fields
3. **Optional usage** - Return `Optional<T>` from repository methods
4. **Layer separation** - Controller → Service → Repository (never skip)
5. **DTOs required** - Never expose JPA entities in API responses

### API Response Format

**All API responses must follow this format:**

```json
// Success
{ "data": { ... }, "meta": { "page": 1, "total": 100 } }

// Error (RFC 7807)
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Validation failed",
  "instance": "/api/v1/example"
}
```

### Verification Checklist

After completing all tasks, verify:

- [ ] Docker PostgreSQL is running (`docker ps`)
- [ ] `./mvnw clean compile` succeeds without errors
- [ ] `./mvnw spring-boot:run -Dspring.profiles.active=dev` starts successfully
- [ ] Application logs show "Started SpotifouApplication"
- [ ] Swagger UI accessible at `http://localhost:8080/swagger-ui.html`
- [ ] Flyway migration ran (check logs for "Successfully applied 1 migration")
- [ ] No Spring Security authentication prompts (permit-all working)

### Project Structure Notes

- This story creates the **foundation** for all backend work
- No actual API endpoints are built yet (that's Stories 1.4, 2.1+)
- Security is intentionally disabled to allow Swagger access
- Empty folders ensure package structure is ready for future stories

### References

- [Source: architecture.md#Starter Template Evaluation - Backend: Spring Initializr]
- [Source: architecture.md#Core Architectural Decisions - Data Architecture]
- [Source: architecture.md#Project Structure & Boundaries - Complete Project Directory Structure]
- [Source: project-context.md#Technology Stack & Versions - Backend]
- [Source: project-context.md#Critical Implementation Rules - Spring Boot Rules]
- [Source: epics.md#Story 1.3: Initialize Backend Project]

## Dev Agent Record

### Agent Model Used

Claude Opus 4.5 (claude-opus-4-5-20251101)

### Debug Log References

- Application startup verified at 2026-02-06T19:28:47
- Swagger UI accessible at HTTP 200
- PostgreSQL connection established via HikariPool

### Completion Notes List

- Added springdoc-openapi-starter-webmvc-ui v3.0.1 (compatible with Spring Boot 4.0.2)
- Added Flyway core and PostgreSQL dialect dependencies
- Created application.yml with JPA, Flyway, and OpenAPI configuration
- Created application-dev.yml with PostgreSQL connection (user: spotifou, password: change_me - matching existing Docker setup)
- Used ddl-auto: none since Flyway manages schema migrations
- Kept existing package `com.ayaco.spotifou` (Option A from Dev Notes) to avoid unnecessary refactoring
- Created all required folder structure with .gitkeep files
- Created placeholder Flyway migration V1__initial_schema.sql
- Created OpenApiConfig.java with API metadata
- Created SecurityConfig.java with permit-all (temporary for development)
- Verified: Application starts on port 8080, Swagger UI accessible, PostgreSQL connected

### File List

**Files created:**
- `backend/src/main/resources/application.yml`
- `backend/src/main/resources/application-dev.yml`
- `backend/src/main/java/com/ayaco/spotifou/config/OpenApiConfig.java`
- `backend/src/main/java/com/ayaco/spotifou/config/SecurityConfig.java`
- `backend/src/main/resources/db/migration/V1__initial_schema.sql`
- `backend/src/main/java/com/ayaco/spotifou/controller/.gitkeep`
- `backend/src/main/java/com/ayaco/spotifou/service/.gitkeep`
- `backend/src/main/java/com/ayaco/spotifou/repository/.gitkeep`
- `backend/src/main/java/com/ayaco/spotifou/model/.gitkeep`
- `backend/src/main/java/com/ayaco/spotifou/dto/request/.gitkeep`
- `backend/src/main/java/com/ayaco/spotifou/dto/response/.gitkeep`
- `backend/src/main/java/com/ayaco/spotifou/security/.gitkeep`
- `backend/src/main/java/com/ayaco/spotifou/exception/.gitkeep`

**Files modified:**
- `backend/pom.xml` (added springdoc-openapi v3.0.1, flyway-core, flyway-database-postgresql)

**Files deleted:**
- `backend/src/main/resources/application.properties` (replaced by .yml)
