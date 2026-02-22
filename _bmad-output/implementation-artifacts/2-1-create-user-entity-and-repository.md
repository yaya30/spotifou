# Story 2.1 : Créer l'entité User et le Repository

Status: done

<!-- Note: Validation is optional. Run validate-create-story for quality check before dev-story. -->

## Story

En tant que **développeur**,
Je veux **une entité User et un repository pour stocker les utilisateurs authentifiés**,
afin que **les données utilisateur puissent être persistées après la connexion Google OAuth**.

## Acceptance Criteria

1. Une migration Flyway `V1__create_users_table.sql` crée la table `users` avec : `id` (BIGSERIAL PK), `email` (VARCHAR UNIQUE NOT NULL), `name` (VARCHAR NOT NULL), `picture_url` (VARCHAR), `created_at` (TIMESTAMP), `updated_at` (TIMESTAMP)
2. `User.java` est une entité JPA mappée sur la table `users` avec les annotations Lombok
3. `UserRepository.java` étend `JpaRepository<User, Long>` et expose `Optional<User> findByEmail(String email)`

## Tasks / Subtasks

- [ ] **Tâche 1 : Migration Flyway** (AC: 1)
  - [ ] Créer `backend/src/main/resources/db/migration/V1__create_users_table.sql`
  - [ ] Définir la table `users` avec tous les champs requis (voir SQL de référence ci-dessous)
  - [ ] Ajouter un index sur `email` : `CREATE INDEX idx_users_email ON users (email)`

- [ ] **Tâche 2 : Entité User.java** (AC: 2)
  - [ ] Créer `backend/src/main/java/com/ayaco/spotifou/model/User.java`
  - [ ] Annoter avec `@Entity`, `@Table(name = "users")`
  - [ ] Utiliser Lombok : `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
  - [ ] Mapper `picture_url` → `pictureUrl` avec `@Column(name = "picture_url")`
  - [ ] Gérer `createdAt`/`updatedAt` via `@PrePersist`/`@PreUpdate`

- [ ] **Tâche 3 : UserRepository.java** (AC: 3)
  - [ ] Créer `backend/src/main/java/com/ayaco/spotifou/repository/UserRepository.java`
  - [ ] Étendre `JpaRepository<User, Long>`
  - [ ] Déclarer `Optional<User> findByEmail(String email)`

- [ ] **Tâche 4 : Test UserRepositoryTest.java** (AC: 1, 2, 3)
  - [ ] Créer `backend/src/test/java/com/ayaco/spotifou/repository/UserRepositoryTest.java`
  - [ ] Tester `findByEmail` : utilisateur existant retourne l'entité, email absent retourne `Optional.empty()`
  - [ ] Tester la contrainte d'unicité sur `email` (second `save` avec même email → exception)
  - [ ] Tester `save` : `createdAt` et `updatedAt` sont auto-remplis par `@PrePersist`

## Dev Notes

### 🚨 Points Critiques

- **Package** : `com.ayaco.spotifou` — PAS `com.spotifou` (erreur courante, confirmé Story 1.3/1.4)
- **Première migration Flyway** → préfixe obligatoire `V1__` (double underscore, pas simple)
- **Répertoire migrations** : `backend/src/main/resources/db/migration/` (doit être créé)
- **Scope strict** : NE PAS créer `UserService` ni `UserController` — périmètre Story 2.2+
- **SecurityConfig** : ne pas modifier — reste permit-all jusqu'à Story 2.2
- **`@Column(name = "picture_url")` obligatoire** : Hibernate ne déduit pas automatiquement le snake_case depuis `pictureUrl` dans toutes les configurations

### Architecture Compliance

| Règle | Valeur | Source |
|-------|--------|--------|
| Package entités | `com.ayaco.spotifou.model` | architecture.md#Structure Patterns |
| Package repositories | `com.ayaco.spotifou.repository` | architecture.md#Structure Patterns |
| Lombok obligatoire | `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` | project-context.md#Java Rules |
| `Optional<T>` | Toujours retourner `Optional` depuis les repositories, jamais `null` | project-context.md#Java Rules |
| Nommage table | snake_case, pluriel → `users` | architecture.md#Database Naming |
| Nommage colonnes | snake_case → `picture_url`, `created_at` | architecture.md#Database Naming |
| Index | `idx_<table>_<column>` → `idx_users_email` | architecture.md#Database Naming |
| ID type | `Long` auto-increment (pas UUID) | project-context.md#API Response Gotchas |
| Injection | Constructor via `@RequiredArgsConstructor` (pas `@Autowired`) | project-context.md#Anti-Patterns |

### Implémentation de Référence

**`V1__create_users_table.sql` :**
```sql
CREATE TABLE users (
    id          BIGSERIAL    PRIMARY KEY,
    email       VARCHAR      NOT NULL UNIQUE,
    name        VARCHAR      NOT NULL,
    picture_url VARCHAR,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

CREATE INDEX idx_users_email ON users (email);
```

**`User.java` :**
```java
package com.ayaco.spotifou.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

**`UserRepository.java` :**
```java
package com.ayaco.spotifou.repository;

import com.ayaco.spotifou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

### Tests — Points d'attention Spring Boot 4

**⚠️ Package migré** : comme `@WebMvcTest` a migré vers `org.springframework.boot.webmvc.test.autoconfigure`, `@DataJpaTest` est probablement dans `org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest` en Spring Boot 4.

**Vérifier H2 sur le classpath** avant d'écrire le test :
```bash
./mvnw dependency:tree -Dscope=test | grep h2
```
- Si H2 présent → `@DataJpaTest` fonctionne out-of-the-box avec base en mémoire
- Si H2 absent → ajouter en scope test dans `pom.xml` :
  ```xml
  <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>test</scope>
  </dependency>
  ```

**Structure du test :**
```java
// package : com.ayaco.spotifou.repository
// @DataJpaTest — vérifier l'import Spring Boot 4 exact
class UserRepositoryTest {
    // findByEmail : utilisateur présent → Optional.of(user)
    // findByEmail : email absent → Optional.empty()
    // save : contrainte unique email → DataIntegrityViolationException
    // save : @PrePersist remplit createdAt et updatedAt
}
```

### Intelligence de la Story Précédente (1.4)

- ✅ Package `com.ayaco.spotifou` confirmé opérationnel
- ✅ Lombok configuré dans `maven-compiler-plugin` → fonctionne
- ✅ Flyway configuré mais **aucune migration existante** → `V1__` est bien la première
- ⚠️ Spring Boot 4 migre les packages de test — toujours vérifier les imports avant compilation
- ⚠️ `SpotifouApplication.java` a des imports morts (Bean, CorsRegistry, WebMvcConfigurer) — ne pas toucher dans cette story

### File Structure After This Story

```
backend/src/main/
├── java/com/ayaco/spotifou/
│   ├── model/
│   │   └── User.java                       ← NEW
│   └── repository/
│       └── UserRepository.java             ← NEW
└── resources/
    └── db/migration/
        └── V1__create_users_table.sql      ← NEW (répertoire à créer)

backend/src/test/java/com/ayaco/spotifou/
└── repository/
    └── UserRepositoryTest.java             ← NEW
```

### Références

- [Source: epics.md#Story 2.1 — User story et Acceptance Criteria]
- [Source: architecture.md#Data Architecture — PostgreSQL, Flyway, Spring Data JPA]
- [Source: architecture.md#Structure Patterns — Backend Layer-based]
- [Source: architecture.md#Database Naming — snake_case, index naming]
- [Source: project-context.md#Java Rules — Lombok, Optional, constructor injection]
- [Source: project-context.md#Testing Rules — @DataJpaTest pour les repositories]
- [Source: 1-4-configure-cors-and-api-base-path.md#Completion Notes — package com.ayaco.spotifou, Spring Boot 4 test packages]

## Dev Agent Record

### Agent Model Used

claude-sonnet-4-6

### Debug Log References

### Completion Notes List

### File List

- `backend/src/main/resources/db/migration/V1__create_users_table.sql`
- `backend/src/main/java/com/ayaco/spotifou/model/User.java`
- `backend/src/main/java/com/ayaco/spotifou/repository/UserRepository.java`
- `backend/src/test/java/com/ayaco/spotifou/repository/UserRepositoryTest.java`
