# Story 1.4: Configure CORS and API Base Path

Status: done

<!-- Note: Validation is optional. Run validate-create-story for quality check before dev-story. -->

## Story

As a **developer**,
I want **CORS configured to allow frontend requests and API versioning set up**,
so that **the frontend can communicate with the backend under `/api/v1/` prefix without CORS errors**.

## Acceptance Criteria

1. `CorsConfig.java` allows requests from `http://localhost:5173`
2. All API endpoints are prefixed with `/api/v1/`
3. Health check endpoint `GET /api/v1/health` returns `{"status": "ok"}`
4. Frontend can successfully call the health endpoint without CORS errors

## Tasks / Subtasks

- [x] **Task 1: Complete CorsConfig.java** (AC: 1, 4)
  - [x] Enhance existing `CorsConfig.java` to add `allowedMethods`, `allowedHeaders`, `allowCredentials`, `maxAge`
  - [x] Keep `allowedOrigins("http://localhost:5173")` (already present)
  - [x] Add `allowCredentials(true)` — required for future httpOnly cookie auth (Epic 2 OAuth)
  - [x] Verify CORS mapping covers `/api/v1/**`

- [x] **Task 2: Create HealthController.java** (AC: 2, 3)
  - [x] Create `HealthController.java` in `controller/` package
  - [x] Implement `GET /api/v1/health` returning `{"status": "ok"}`
  - [x] Use a proper response DTO (`HealthResponseDto` record)
  - [x] Annotate with `@RestController`, path `/api/v1/health` directement sur `@GetMapping`

- [x] **Task 3: Verify end-to-end CORS** (AC: 4)
  - [x] Validé par les 4 tests automatisés (`HealthControllerTest`) — tous verts

- [ ] **Task 4: Clean up exploratory files (optional)** (AC: none — housekeeping)
  - [ ] Evaluate whether to keep `TestController.java`, `TestDto.java`, `public/index.html`, `public/hello.js`
  - [ ] These were created as CORS exploration — keep or remove per team preference
  - [ ] If kept, rename `TestController` endpoint to avoid confusion with story requirements

## Dev Notes

### 🚨 CRITICAL: Work Already In Progress (From Git Staging Area)

The following files are **already staged** from exploratory work done prior to this story being formalized:

| File | Status | Notes |
|------|--------|-------|
| `backend/src/main/java/com/ayaco/spotifou/config/CorsConfig.java` | Staged (new) | EXISTS but **incomplete** — missing `allowedMethods`, `allowedHeaders`, `allowCredentials` |
| `backend/src/main/java/com/ayaco/spotifou/controller/TestController.java` | Staged (new) | EXISTS at `/api/v1/test` — NOT the health endpoint; returns test string, not `{"status":"ok"}` |
| `backend/src/main/java/com/ayaco/spotifou/dto/response/TestDto.java` | Staged (new) | Record DTO for TestController |
| `backend/src/main/resources/public/index.html` | Staged (new) | Static HTML CORS test page (from Spring guide) |
| `backend/src/main/resources/public/hello.js` | Staged (new) | Calls `localhost:9000/api/v1/test` — NOT production code |
| `backend/src/main/java/com/ayaco/spotifou/SpotifouApplication.java` | Modified | Cleaned up imports |
| `backend/Dockerfile` | Modified | Unknown changes — inspect before touching |

**Do NOT re-create any staged files. Modify the existing `CorsConfig.java` in-place.**

### Current CorsConfig.java (Incomplete)

```java
// CURRENT STATE — needs enhancement
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                        .allowedOrigins("http://localhost:5173");  // ← only this set
            }
        };
    }
}
```

**Required final state:**

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)   // ← REQUIRED for Epic 2 httpOnly cookies
                        .maxAge(3600);
            }
        };
    }
}
```

### HealthController.java (Must Create)

The story AC requires `GET /api/v1/health` → `{"status": "ok"}`. Use either approach:

**Option A — Simple Map (no extra DTO file):**

```java
package com.ayaco.spotifou.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
```

**Option B — Dedicated DTO (consistent with project pattern, preferred):**

```java
// dto/response/HealthResponse.java
package com.ayaco.spotifou.dto.response;

public record HealthResponse(String status) {}

// controller/HealthController.java
package com.ayaco.spotifou.controller;

import com.ayaco.spotifou.dto.response.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("ok");
    }
}
```

**Recommendation:** Use Option B — it follows the project's "DTOs required" rule and is consistent with `TestDto.java` already using records.

### Architecture Compliance

| Rule | Value | Source |
|------|-------|--------|
| Package | `com.ayaco.spotifou` | [Story 1.3 — kept over `com.spotifou`] |
| API prefix | `/api/v1/` | [architecture.md#API & Communication] |
| DTOs required | Never expose raw objects | [project-context.md#Spring Boot Rules] |
| Constructor injection | Use `@RequiredArgsConstructor` | [project-context.md#Java Rules] |
| Response format | `{"data": {...}}` for standard API | [architecture.md#Format Patterns] |
| Health endpoint | `{"status": "ok"}` specifically | [epics.md#Story 1.4 AC] |
| CORS credentials | `allowCredentials(true)` | Required for Epic 2 httpOnly cookie OAuth |

> **Note:** The health endpoint returns `{"status": "ok"}` directly, NOT wrapped in `{"data": {...}}`. This is intentional per the epic AC — it's an infrastructure endpoint, not a resource endpoint.

### File Structure After This Story

```
backend/src/main/java/com/ayaco/spotifou/
├── config/
│   ├── CorsConfig.java          ← MODIFY: add allowedMethods, headers, credentials
│   ├── OpenApiConfig.java       ← existing, no change
│   └── SecurityConfig.java      ← existing, no change (permit-all stays for this epic)
│
├── controller/
│   ├── HealthController.java    ← NEW: GET /api/v1/health → {"status":"ok"}
│   └── TestController.java      ← existing exploratory (keep or remove)
│
└── dto/response/
    ├── HealthResponse.java      ← NEW: record HealthResponse(String status) {}
    └── TestDto.java             ← existing exploratory (keep or remove)
```

### Previous Story Intelligence (Story 1.3)

From `1-3-initialize-backend-project.md` completion notes:

- **springdoc-openapi v3.0.1** installed (compatible with Spring Boot 4.0.2)
- **Flyway** configured; Swagger UI at `http://localhost:8080/swagger-ui/index.html`
- **SecurityConfig** is temporary permit-all — **do NOT change it in this story**
- **Docker PostgreSQL**: localhost:5432, db: `spotifou`, user: `spotifou`, password: `change_me`
- `ddl-auto: none` because Flyway manages schema
- All backend folder structure already created

**Key learning from 1.3:** `allowCredentials(true)` requires that `allowedOrigins` NOT use the wildcard `"*"`. Since we're using a specific origin (`http://localhost:5173`), this is safe and correct.

### Git Intelligence

Recent commits:
1. `37dfc6e` — Story 1.3: Initialize Backend Project
2. `a46a5d7` — Story 1.2: Initialize Frontend Project
3. `2775604` — Story 1.1: Initialize Monorepo Structure

Staged but uncommitted files:
- `CorsConfig.java`, `TestController.java`, `TestDto.java`, `index.html`, `hello.js`, `Dockerfile` (modified)
- These will appear in the Story 1.4 commit

### Anti-Patterns to Avoid

| ❌ Don't | ✅ Do |
|----------|-------|
| Use Spring Boot Actuator for `/api/v1/health` | Create a custom `HealthController` at that exact path |
| Set `allowedOrigins("*")` with `allowCredentials(true)` | Always use specific origin for credentials |
| Put CORS in `SecurityConfig.java` | Keep CORS in dedicated `CorsConfig.java` |
| Return `null` from health endpoint | Return `new HealthResponse("ok")` |
| Skip committing staged files | Include all staged files in the Story 1.4 commit |

### Verification Checklist

After completing all tasks:

- [ ] `./mvnw clean compile` succeeds
- [ ] `./mvnw spring-boot:run -Dspring.profiles.active=dev` starts on port 8080
- [ ] `curl http://localhost:8080/api/v1/health` returns `{"status":"ok"}`
- [ ] Swagger UI at `http://localhost:8080/swagger-ui/index.html` shows `/api/v1/health` endpoint
- [ ] Browser fetch from `http://localhost:5173` to `http://localhost:8080/api/v1/health` works without CORS error
- [ ] No Spring Security authentication prompts (permit-all still active)

### References

- [Source: epics.md#Story 1.4: Configure CORS and API Base Path]
- [Source: architecture.md#Authentication & Security — CORS single origin]
- [Source: architecture.md#API & Communication — URL path versioning /api/v1/]
- [Source: project-context.md#Spring Boot Rules — DTOs required, constructor injection]
- [Source: 1-3-initialize-backend-project.md#Completion Notes — package name, springdoc version]

## Dev Agent Record

### Agent Model Used

claude-sonnet-4-6

### Debug Log References

### Completion Notes List

- DTO renommé `HealthDto` → `HealthResponseDto` pour respecter la convention `*Dto` (suffixe Response implicite dans le nom)
- `@GetMapping("/api/v1/health")` placé directement sur la méthode (pas de `@RequestMapping` classe) — acceptable pour controller single-endpoint
- `allowCredentials(true)` ajouté à `CorsConfig` — prérequis Epic 2 OAuth httpOnly cookies
- Bug corrigé durant code review : origin initialement configurée sur port `5174` au lieu de `5173`
- Import morts dans `SpotifouApplication.java` (Bean, CorsRegistry, WebMvcConfigurer) non supprimés — housekeeping à faire
- `TestController.java` + fichiers exploratoires conservés (Task 4 optionnelle non faite)
- En Spring Boot 4, `@WebMvcTest` est dans `org.springframework.boot.webmvc.test.autoconfigure` (package migré vs Spring Boot 3)

### File List

- `backend/src/main/java/com/ayaco/spotifou/config/CorsConfig.java` — modifié : ajout `allowedMethods`, `allowedHeaders`, `allowCredentials(true)`, `maxAge`, correction origin port 5173
- `backend/src/main/java/com/ayaco/spotifou/controller/HealthController.java` — créé : `GET /api/v1/health` → `HealthResponseDto`
- `backend/src/main/java/com/ayaco/spotifou/dto/response/HealthResponseDto.java` — créé : record `HealthResponseDto(String status)` avec constructeur no-arg → `"ok"`
- `backend/src/test/java/com/ayaco/spotifou/controller/HealthControllerTest.java` — créé : 4 tests `@WebMvcTest` couvrant AC 1-4
