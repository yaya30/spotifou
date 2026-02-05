---
stepsCompleted: [1, 2, 3, 4, 5, 6, 7, 8]
status: 'complete'
completedAt: '2026-02-02'
inputDocuments:
  - '_bmad-output/planning-artifacts/prd.md'
  - '_bmad-output/planning-artifacts/ux-design-specification.md'
workflowType: 'architecture'
project_name: 'spotifou'
user_name: 'ayaco'
date: '2026-01-30'
---

# Architecture Decision Document

_This document builds collaboratively through step-by-step discovery. Sections are appended as we work through each architectural decision together._

## Project Context Analysis

### Requirements Overview

**Functional Requirements (38 total):**

| Category | Count | Architectural Impact |
|----------|-------|---------------------|
| User Authentication | 6 | Auth service, JWT middleware, protected routes |
| Music Catalog | 5 | REST endpoints, JPA entities, pagination |
| Audio Playback | 6 | Audio service, global player state, streaming |
| Search | 5 | Search API, indexing strategy, debounce |
| Playlist Management | 8 | CRUD operations, many-to-many relationships |
| User Library | 4 | Favorites table, toggle operations |
| Error Handling | 4 | Global error boundary, toast system |

**Non-Functional Requirements (18 total):**

| Category | Key Requirements | Architectural Impact |
|----------|------------------|---------------------|
| Performance | <3s load, <200ms API, <500ms audio | Caching, lazy loading, CDN for audio |
| Security | JWT, bcrypt, HTTPS, validation | Auth middleware, input sanitization |
| Scalability | Stateless, indexed, cacheable | Horizontal scaling ready, Redis potential |
| Reliability | Graceful errors, persistence | Error boundaries, optimistic updates |

### Scale & Complexity

- **Primary domain:** Full-stack web application (React SPA + Spring Boot API)
- **Complexity level:** Low-Medium
- **Estimated architectural components:** ~15 frontend, ~10 backend

### Technical Constraints & Dependencies

**Frontend:**
- Node.js 24.0.13
- React 18+ with Vite
- TanStack Query for server state
- Zustand or Context for UI state
- Tailwind CSS + Shadcn/ui
- HTML5 Audio API

**Backend:**
- Spring Boot 4.0.2
- Spring Data JPA
- Spring Security (JWT)
- PostgreSQL (assumed relational DB)

### Cross-Cutting Concerns

| Concern | Scope | Implementation Approach |
|---------|-------|------------------------|
| **Authentication** | All protected resources | JWT tokens, auth context, API interceptors |
| **Error Handling** | Entire application | Error boundaries, toast notifications, retry logic |
| **Loading States** | All data fetching | TanStack Query states, skeleton components |
| **Player State** | Global (persists across views) | Zustand store or Context + useReducer |
| **Caching** | Catalog data, user data | TanStack Query cache, stale-while-revalidate |

## Starter Template Evaluation

### Primary Technology Domain

**Full-stack web application** with separate frontend (React SPA) and backend (Spring Boot API) projects in a monorepo structure.

### Starter Options Considered

**Frontend:**
- React Router v7 Framework + Shadcn/ui (selected)
- Official Vite + React template (rejected - React Router Framework provides better structure)
- Pre-configured GitHub templates (rejected - prefer learning standard setup)

**Backend:**
- Spring Initializr (selected)
- Pre-configured JWT templates (rejected - prefer understanding each piece)

### Selected Starters

#### Frontend: React Router v7 Framework + Shadcn/ui

**Initialization Commands:**
```bash
npx create-react-router@latest frontend
cd frontend
npm install
npx shadcn@latest init
```

**Shadcn Init Options:**
- Style: Default
- Base color: Slate
- CSS variables: Yes
- Components path: `app/components/ui`
- Utils path: `app/lib/utils`

**Additional Dependencies:**
```bash
npm install @tanstack/react-query zustand framer-motion lucide-react
```

**Note:** React Router v7 Framework mode uses file-based routing in `app/routes/` and provides SSR capabilities. This is the modern recommended approach for React applications with routing.

#### Backend: Spring Initializr

**Initialization:**
```
https://start.spring.io/
- Project: Maven
- Language: Java
- Spring Boot: 4.0.2
- Group: com.spotifou
- Artifact: backend
- Packaging: Jar
- Java: 21

Dependencies:
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL Driver
- Validation
- Lombok
```

### Architectural Decisions Provided by Starters

**Frontend (React Router v7 Framework):**

| Decision | Value |
|----------|-------|
| Language | TypeScript (strict mode) |
| Build Tool | Vite 7 via React Router |
| Routing | File-based routing (`app/routes/`) |
| Styling | Tailwind CSS 4 + CSS Variables |
| Components | Shadcn/ui (copy-paste, customizable) |
| Path Aliases | `~/` → `app/` |

**Backend (Spring Boot):**

| Decision | Value |
|----------|-------|
| Language | Java 21 |
| Build Tool | Maven |
| Framework | Spring Boot 4.0.2 |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security 7 |

### Project Structure

```
spotifou/
├── frontend/
│   ├── app/
│   │   ├── components/
│   │   │   ├── ui/            # Shadcn components
│   │   │   └── common/        # App-wide components
│   │   ├── features/          # Feature modules
│   │   ├── routes/            # File-based routing
│   │   ├── hooks/
│   │   ├── stores/
│   │   ├── services/
│   │   ├── lib/
│   │   ├── types/
│   │   ├── root.tsx           # App root layout
│   │   └── routes.ts          # Route configuration
│   ├── package.json
│   └── vite.config.ts
├── backend/
│   ├── src/main/java/com/spotifou/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── config/
│   │   └── security/
│   └── pom.xml
└── README.md
```

## Core Architectural Decisions

### Decision Priority Analysis

**Critical Decisions (Block Implementation):**
- Authentication: Google OAuth
- Database: PostgreSQL with Flyway migrations
- API: REST with URL versioning

**Important Decisions (Shape Architecture):**
- State Management: TanStack Query + Zustand
- Forms: React Hook Form + Zod
- Caching: In-memory (MVP)

**Deferred Decisions (Post-MVP):**
- Redis caching
- Magic Link authentication
- Production deployment target

### Data Architecture

| Decision | Choice | Rationale |
|----------|--------|-----------|
| **Database** | PostgreSQL | Relational, mature, good for learning |
| **ORM** | Spring Data JPA | Standard Spring approach |
| **Migrations** | Flyway | SQL-based, version control friendly |
| **Caching (MVP)** | In-memory (Caffeine BE, TanStack Query FE) | Simple, no infrastructure |
| **Caching (Growth)** | Redis | If horizontal scaling needed |

### Authentication & Security

| Decision | Choice | Rationale |
|----------|--------|-----------|
| **Auth Method** | Google OAuth | Passwordless, secure, better UX |
| **Token Strategy** | Google ID tokens validated by backend | Simpler than custom JWT |
| **Session** | Stateless (token-based) | Scalable architecture |
| **CORS** | Single origin (frontend URL) | Security best practice |

**Implementation:**
- Frontend: `@react-oauth/google`
- Backend: Spring Security OAuth2 Resource Server

### API & Communication

| Decision | Choice | Rationale |
|----------|--------|-----------|
| **API Style** | REST | Learning goal, Spring standard |
| **Documentation** | OpenAPI/Swagger (springdoc-openapi) | Auto-generated, interactive |
| **Versioning** | URL path (`/api/v1/`) | Simple, explicit, future-proof |
| **Error Format** | RFC 7807 Problem Details | Standard error responses |

### Frontend Architecture

| Decision | Choice | Rationale |
|----------|--------|-----------|
| **Framework** | React Router v7 Framework | File-based routing, SSR-ready, modern approach |
| **Routing** | File-based (`app/routes/`) | Convention over configuration |
| **Server State** | TanStack Query | Caching, loading states, mutations |
| **UI State** | Zustand | Simple, minimal boilerplate |
| **Forms** | React Hook Form + Zod | Performant, type-safe validation |
| **Error Boundaries** | Built-in (React Router) | ErrorBoundary in root.tsx |

### Infrastructure & Deployment

| Decision | Choice | Rationale |
|----------|--------|-----------|
| **Local Dev** | Docker Compose | PostgreSQL container, consistent env |
| **Config (FE)** | .env files | Vite env variable handling |
| **Config (BE)** | Spring Profiles | dev, prod configurations |
| **Deployment** | TBD | Focus on building first |

### Decision Impact Analysis

**Implementation Sequence:**
1. Docker Compose setup (PostgreSQL)
2. Spring Boot project with Security + JPA
3. Google OAuth integration
4. React project with Router + Query
5. API endpoints with Swagger
6. Frontend features

## Implementation Patterns & Consistency Rules

### Naming Patterns

**Database Naming:**

| Element | Convention | Example |
|---------|------------|---------|
| Tables | snake_case, plural | `users`, `playlists`, `playlist_tracks` |
| Columns | snake_case | `user_id`, `created_at`, `track_name` |
| Foreign Keys | `<table>_id` | `user_id`, `playlist_id` |
| Indexes | `idx_<table>_<column>` | `idx_users_email` |

**API Naming:**

| Element | Convention | Example |
|---------|------------|---------|
| Endpoints | plural nouns, kebab for multi-word | `/api/v1/users`, `/api/v1/playlist-tracks` |
| Nested resources | parent/id/child | `/api/v1/playlists/{id}/tracks` |
| Query params | camelCase | `?sortBy=createdAt&limit=20` |

**Code Naming:**

| Layer | Convention | Example |
|-------|------------|---------|
| React Components | PascalCase files | `TrackRow.tsx`, `PlayerBar.tsx` |
| React Hooks | `use` prefix | `usePlayer.ts`, `useAuth.ts` |
| Utilities | camelCase | `formatDuration.ts` |
| Java Classes | PascalCase | `UserService.java` |
| Java Methods | camelCase | `findByEmail()` |

### Structure Patterns

**Frontend (Feature-based with React Router Framework):**

```
frontend/app/
├── components/ui/          # Shadcn components
├── components/common/      # App-wide (PlayerBar, Sidebar)
├── features/
│   ├── auth/
│   ├── catalog/
│   ├── playlists/
│   ├── library/
│   ├── search/
│   └── player/
├── routes/                 # File-based routing
├── hooks/                  # Shared hooks
├── stores/                 # Zustand stores
├── services/               # API functions
├── lib/                    # Utilities
├── types/                  # TypeScript types
├── root.tsx                # App root layout
└── routes.ts               # Route configuration
```

**Backend (Layer-based):**

```
backend/src/main/java/com/spotifou/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── config/
├── security/
└── exception/
```

**Tests:** Co-located (`*.test.tsx`) for frontend, mirrored structure for backend.

### Format Patterns

**API Responses:**

```json
// Success
{ "data": { ... }, "meta": { "page": 1, "total": 100 } }

// Error (RFC 7807)
{ "type": "validation_error", "title": "Invalid request", "status": 400, "detail": "..." }
```

**Data Conventions:**

| Format | Convention |
|--------|------------|
| JSON fields | camelCase |
| Dates | ISO 8601 |
| IDs | Long (auto-increment) |
| Empty arrays | `[]` (never null) |

### Process Patterns

**Authentication Flow (httpOnly Cookie):**
1. Google OAuth → ID token
2. POST `/api/v1/auth/google` with token
3. Backend validates, sets httpOnly cookie
4. Cookie auto-sent with requests

**Error Handling:**
- Global error boundary at app root
- Toast for action errors
- Inline for form validation

**Loading States:**
- TanStack Query states (`isPending`, `isError`)
- Skeleton components for content
- Button spinner for actions

### Enforcement Guidelines

**All AI Agents MUST:**
- Follow naming conventions exactly
- Place files in correct directories
- Use defined API response format
- Handle errors consistently

## Project Structure & Boundaries

### Complete Project Directory Structure

```
spotifou/
├── README.md
├── docker-compose.yml                 # PostgreSQL + local dev services
├── .gitignore
├── .env.example                       # Environment template
│
├── frontend/
│   ├── package.json
│   ├── package-lock.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── react-router.config.ts        # React Router configuration
│   ├── components.json               # Shadcn configuration
│   ├── .env                          # VITE_API_URL, VITE_GOOGLE_CLIENT_ID
│   ├── .env.example
│   ├── .gitignore
│   │
│   ├── public/
│   │   └── favicon.ico
│   │
│   ├── app/                          # React Router Framework app folder
│   │   ├── root.tsx                  # App root layout with providers
│   │   ├── routes.ts                 # Route configuration
│   │   ├── app.css                   # Global styles + Tailwind
│   │   │
│   │   ├── routes/                   # File-based routing
│   │   │   ├── _index.tsx            # Home page (/)
│   │   │   ├── login.tsx             # Login page (/login)
│   │   │   ├── artist.$id.tsx        # Artist page (/artist/:id)
│   │   │   ├── album.$id.tsx         # Album page (/album/:id)
│   │   │   ├── playlist.$id.tsx      # Playlist page (/playlist/:id)
│   │   │   ├── library.tsx           # Library page (/library)
│   │   │   └── search.tsx            # Search page (/search)
│   │   │
│   │   ├── components/
│   │   │   ├── ui/                   # Shadcn components
│   │   │   │   ├── button.tsx
│   │   │   │   ├── input.tsx
│   │   │   │   ├── card.tsx
│   │   │   │   ├── dialog.tsx
│   │   │   │   ├── dropdown-menu.tsx
│   │   │   │   ├── slider.tsx
│   │   │   │   ├── skeleton.tsx
│   │   │   │   └── toast.tsx
│   │   │   │
│   │   │   └── common/               # App-wide shared components
│   │   │       ├── Layout.tsx        # Main app layout
│   │   │       ├── Sidebar.tsx       # Navigation sidebar
│   │   │       ├── PlayerBar.tsx     # Persistent bottom player
│   │   │       ├── Header.tsx        # Top header with search
│   │   │       └── EmptyState.tsx    # Reusable empty state
│   │   │
│   │   ├── features/
│   │   │   ├── auth/
│   │   │   │   ├── components/
│   │   │   │   ├── hooks/
│   │   │   │   ├── services/
│   │   │   │   └── types/
│   │   │   ├── catalog/
│   │   │   ├── player/
│   │   │   ├── playlists/
│   │   │   ├── library/
│   │   │   └── search/
│   │   │
│   │   ├── hooks/                    # Shared hooks
│   │   │   └── useDebounce.ts
│   │   │
│   │   ├── stores/                   # Zustand stores
│   │   │   ├── playerStore.ts        # Audio player state
│   │   │   └── uiStore.ts            # UI state (sidebar, modals)
│   │   │
│   │   ├── services/                 # Shared API utilities
│   │   │   ├── apiClient.ts          # Fetch/Axios with interceptors
│   │   │   └── queryClient.ts        # TanStack Query client config
│   │   │
│   │   ├── lib/                      # Utilities
│   │   │   ├── utils.ts              # cn() and helpers
│   │   │   └── formatDuration.ts
│   │   │
│   │   └── types/                    # Shared TypeScript types
│   │       ├── api.types.ts          # API response wrappers
│   │       └── index.ts
│   │
│   └── tests/
│       ├── setup.ts
│       ├── components/
│       └── features/
│
└── backend/
    ├── pom.xml
    ├── mvnw
    ├── mvnw.cmd
    ├── .mvn/
    │   └── wrapper/
    ├── Dockerfile
    ├── .env                          # Local dev env vars
    ├── .env.example
    │
    └── src/
        ├── main/
        │   ├── java/com/spotifou/
        │   │   ├── SpotifouApplication.java
        │   │   │
        │   │   ├── config/
        │   │   │   ├── SecurityConfig.java
        │   │   │   ├── CorsConfig.java
        │   │   │   ├── OpenApiConfig.java
        │   │   │   └── CacheConfig.java
        │   │   │
        │   │   ├── security/
        │   │   │   ├── GoogleTokenValidator.java
        │   │   │   ├── JwtAuthenticationFilter.java
        │   │   │   └── UserPrincipal.java
        │   │   │
        │   │   ├── controller/
        │   │   │   ├── AuthController.java
        │   │   │   ├── ArtistController.java
        │   │   │   ├── AlbumController.java
        │   │   │   ├── TrackController.java
        │   │   │   ├── PlaylistController.java
        │   │   │   ├── FavoriteController.java
        │   │   │   └── SearchController.java
        │   │   │
        │   │   ├── service/
        │   │   │   ├── AuthService.java
        │   │   │   ├── UserService.java
        │   │   │   ├── ArtistService.java
        │   │   │   ├── AlbumService.java
        │   │   │   ├── TrackService.java
        │   │   │   ├── PlaylistService.java
        │   │   │   ├── FavoriteService.java
        │   │   │   └── SearchService.java
        │   │   │
        │   │   ├── repository/
        │   │   │   ├── UserRepository.java
        │   │   │   ├── ArtistRepository.java
        │   │   │   ├── AlbumRepository.java
        │   │   │   ├── TrackRepository.java
        │   │   │   ├── PlaylistRepository.java
        │   │   │   ├── PlaylistTrackRepository.java
        │   │   │   ├── PlaylistUserRepository.java
        │   │   │   ├── AlbumArtistRepository.java
        │   │   │   └── FavoriteRepository.java
        │   │   │
        │   │   ├── model/
        │   │   │   ├── User.java
        │   │   │   ├── Artist.java
        │   │   │   ├── Album.java
        │   │   │   ├── AlbumArtist.java
        │   │   │   ├── Track.java
        │   │   │   ├── Playlist.java
        │   │   │   ├── PlaylistTrack.java
        │   │   │   ├── PlaylistUser.java
        │   │   │   └── Favorite.java
        │   │   │
        │   │   ├── dto/
        │   │   │   ├── request/
        │   │   │   │   ├── GoogleAuthRequest.java
        │   │   │   │   ├── CreatePlaylistRequest.java
        │   │   │   │   ├── UpdatePlaylistRequest.java
        │   │   │   │   └── AddTrackRequest.java
        │   │   │   └── response/
        │   │   │       ├── AuthResponse.java
        │   │   │       ├── UserResponse.java
        │   │   │       ├── ArtistResponse.java
        │   │   │       ├── AlbumResponse.java
        │   │   │       ├── TrackResponse.java
        │   │   │       ├── PlaylistResponse.java
        │   │   │       ├── SearchResponse.java
        │   │   │       └── PageResponse.java
        │   │   │
        │   │   └── exception/
        │   │       ├── GlobalExceptionHandler.java
        │   │       ├── ResourceNotFoundException.java
        │   │       ├── UnauthorizedException.java
        │   │       └── ValidationException.java
        │   │
        │   └── resources/
        │       ├── application.yml
        │       ├── application-dev.yml
        │       ├── application-prod.yml
        │       └── db/migration/         # Flyway migrations (created during implementation)
        │
        └── test/
            └── java/com/spotifou/
                ├── SpotifouApplicationTests.java
                ├── controller/
                ├── service/
                └── repository/
```

### Architectural Boundaries

**API Boundaries:**

| Boundary | Endpoint Prefix | Description |
|----------|----------------|-------------|
| Authentication | `/api/v1/auth` | Google OAuth token exchange, session management |
| Artists | `/api/v1/artists` | Artist CRUD, artist albums |
| Albums | `/api/v1/albums` | Album CRUD, album tracks, album artists |
| Tracks | `/api/v1/tracks` | Track details, streaming URL |
| Playlists | `/api/v1/playlists` | User playlist CRUD, track management, collaborators |
| Favorites | `/api/v1/favorites` | Add/remove/list favorites |
| Search | `/api/v1/search` | Unified search across entities |

**Component Boundaries (Frontend):**

| Boundary | Communication Pattern |
|----------|----------------------|
| Features → Pages | Pages compose feature components |
| Features → API | Each feature owns its API calls via TanStack Query |
| Components → Store | Read-only via Zustand selectors |
| Store → Audio | `playerStore` controls HTML5 Audio API |
| Pages → Router | React Router manages page transitions |

**Service Boundaries (Backend):**

| Layer | Responsibility | Calls |
|-------|---------------|-------|
| Controller | HTTP handling, validation, DTO mapping | Service |
| Service | Business logic, authorization checks | Repository |
| Repository | Data access, JPA queries | Database |
| Security | Token validation, user extraction | Google API |

**Data Boundaries:**

| Entity | Relationships |
|--------|---------------|
| **User** | Many-to-many: Playlists (via `playlist_users` with role) |
| **Artist** | Many-to-many: Albums (via `album_artists`) |
| **Album** | Many-to-many: Artists (via `album_artists`), One-to-many: Tracks |
| **Track** | Many-to-one: Album (primary), Many-to-many: Playlists |
| **Playlist** | Many-to-many: Users (via `playlist_users`), Many-to-many: Tracks |
| **Favorite** | User + Track composite key |

**Join Tables:**

| Table | Columns | Purpose |
|-------|---------|---------|
| `album_artists` | album_id, artist_id, role | Links albums to multiple artists (primary, featured, producer) |
| `playlist_users` | playlist_id, user_id, role, added_at | Links playlists to users with ownership/collaborator roles |
| `playlist_tracks` | playlist_id, track_id, position, added_at | Links playlists to tracks with ordering |

### Requirements to Structure Mapping

**Feature Mapping:**

| Feature | Frontend Location | Backend Location |
|---------|------------------|------------------|
| FR1-6: Auth | `features/auth/`, `pages/LoginPage.tsx` | `controller/AuthController`, `security/` |
| FR7-11: Catalog | `features/catalog/`, `pages/Artist*.tsx`, `pages/Album*.tsx` | `controller/*Controller`, `service/*Service` |
| FR12-17: Playback | `features/player/`, `components/common/PlayerBar.tsx`, `stores/playerStore.ts` | (audio files served statically) |
| FR18-22: Search | `features/search/`, `pages/SearchPage.tsx` | `controller/SearchController`, `service/SearchService` |
| FR23-30: Playlists | `features/playlists/`, `pages/PlaylistPage.tsx` | `controller/PlaylistController`, `service/PlaylistService` |
| FR31-34: Library | `features/library/`, `pages/LibraryPage.tsx` | `controller/FavoriteController`, `service/FavoriteService` |
| FR35-38: Errors | `components/common/ErrorFallback.tsx`, `components/common/EmptyState.tsx` | `exception/GlobalExceptionHandler.java` |

**Cross-Cutting Concerns:**

| Concern | Frontend Location | Backend Location |
|---------|------------------|------------------|
| Authentication | `features/auth/hooks/useAuth.ts`, `services/apiClient.ts` | `security/`, `config/SecurityConfig.java` |
| Error Handling | `components/common/ErrorFallback.tsx`, TanStack Query | `exception/GlobalExceptionHandler.java` |
| Loading States | TanStack Query `isPending`, `components/ui/skeleton.tsx` | N/A |
| Caching | `services/queryClient.ts` (staleTime config) | `config/CacheConfig.java` (Caffeine) |

### Integration Points

**Internal Communication:**
- Frontend → Backend: REST API via `apiClient.ts` (Axios with credentials)
- State → UI: Zustand selectors for player state
- Features → Queries: TanStack Query hooks per feature

**External Integrations:**
- Google OAuth: `@react-oauth/google` (FE) → `GoogleTokenValidator` (BE)
- Audio Streaming: HTML5 Audio API → static audio files

**Data Flow:**
```
User Action → React Component → TanStack Query Hook → API Client → REST API
                                                                      ↓
UI Update ← Store Update ← Query Cache ← Response ← Service ← Repository ← DB
```

## Architecture Validation Results

### Coherence Validation ✅

**Decision Compatibility:**
All technology choices work together without conflicts:
- React 18 + Vite 7 + TypeScript: Standard, well-supported combination
- TanStack Query + Zustand: Complementary (server state + UI state)
- Spring Boot 4.0.2 + Spring Security 7: Matching major versions
- Google OAuth + httpOnly cookies: Secure, standard flow
- PostgreSQL + Flyway + JPA: Standard Spring data stack

**Pattern Consistency:**
- Naming conventions align across DB (snake_case), API (kebab-case), code (camelCase/PascalCase)
- Feature-based frontend structure supports TanStack Query's per-feature hooks pattern
- Layer-based backend supports clear service boundaries

**Structure Alignment:**
- Project structure supports all 6 features defined in PRD
- Separation of `features/` and `components/common/` aligns with Zustand store split

### Requirements Coverage Validation ✅

**Functional Requirements (38/38 covered):**

| Category | FRs | Architectural Support |
|----------|-----|----------------------|
| User Auth (FR1-6) | 6/6 ✅ | Google OAuth flow, `features/auth/`, `security/` |
| Music Catalog (FR7-11) | 5/5 ✅ | `features/catalog/`, REST endpoints, JPA entities |
| Audio Playback (FR12-17) | 6/6 ✅ | `playerStore`, `PlayerBar`, HTML5 Audio |
| Search (FR18-22) | 5/5 ✅ | `features/search/`, `SearchController` |
| Playlist Management (FR23-30) | 8/8 ✅ | `features/playlists/`, many-to-many relations |
| User Library (FR31-34) | 4/4 ✅ | `features/library/`, `Favorite` entity |
| Error Handling (FR35-38) | 4/4 ✅ | `ErrorFallback`, `GlobalExceptionHandler`, RFC 7807 |

**Non-Functional Requirements (18/18 covered):**

| Category | Coverage |
|----------|----------|
| Performance (NFR1-5) | ✅ TanStack Query caching, Caffeine BE cache, pagination-ready |
| Security (NFR6-10) | ✅ Google OAuth (no passwords), httpOnly cookies, CORS, validation |
| Scalability (NFR11-15) | ✅ Stateless, indexed DB, REST, pagination patterns |
| Reliability (NFR16-18) | ✅ Error boundaries, token persistence, graceful degradation |

### Implementation Readiness Validation ✅

**Decision Completeness:**
- ✅ All technology versions specified (Spring Boot 4.0.2, Node 24.0.13, Java 21)
- ✅ Initialization commands documented (Vite, Shadcn, Spring Initializr)
- ✅ Third-party packages listed (`@react-oauth/google`, `@tanstack/react-query`, etc.)

**Structure Completeness:**
- ✅ Complete directory tree for both frontend and backend
- ✅ All entities and join tables defined
- ✅ Feature-to-file mapping documented

**Pattern Completeness:**
- ✅ API response format (data/meta wrapper, RFC 7807 errors)
- ✅ Auth flow documented step-by-step
- ✅ Error handling pattern per layer

### Gap Analysis Results

**No Critical Gaps Found.**

Minor observations (implementation details, not architectural gaps):
- Audio file storage location assumed to be static files
- Seed data for catalog to be defined during implementation
- Standard library patterns (React Hook Form + Zod) follow library documentation

### Architecture Completeness Checklist

**✅ Requirements Analysis**
- [x] Project context thoroughly analyzed
- [x] Scale and complexity assessed
- [x] Technical constraints identified
- [x] Cross-cutting concerns mapped

**✅ Architectural Decisions**
- [x] Critical decisions documented with versions
- [x] Technology stack fully specified
- [x] Integration patterns defined
- [x] Performance considerations addressed

**✅ Implementation Patterns**
- [x] Naming conventions established
- [x] Structure patterns defined
- [x] Communication patterns specified
- [x] Process patterns documented

**✅ Project Structure**
- [x] Complete directory structure defined
- [x] Component boundaries established
- [x] Integration points mapped
- [x] Requirements to structure mapping complete

### Architecture Readiness Assessment

**Overall Status:** READY FOR IMPLEMENTATION

**Confidence Level:** High

**Key Strengths:**
- Clear separation of concerns (features, layers, boundaries)
- Modern, well-documented technology choices
- Secure-by-default authentication (Google OAuth, httpOnly cookies)
- Flexible data model supporting future collaboration features

**Areas for Future Enhancement:**
- Redis caching (if horizontal scaling needed)
- Magic link authentication (alternative to Google)
- Real-time features (WebSockets for collaborative playlists)

### Implementation Handoff

**AI Agent Guidelines:**
- Follow all architectural decisions exactly as documented
- Use implementation patterns consistently across all components
- Respect project structure and boundaries
- Refer to this document for all architectural questions

**First Implementation Priority:**
1. `docker-compose.yml` with PostgreSQL
2. Spring Boot project via Spring Initializr
3. React project via Vite + Shadcn init
4. Google OAuth integration (both ends)

## Architecture Completion Summary

### Workflow Completion

**Architecture Decision Workflow:** COMPLETED ✅
**Total Steps Completed:** 8
**Date Completed:** 2026-02-02
**Document Location:** `_bmad-output/planning-artifacts/architecture.md`

### Final Architecture Deliverables

**Complete Architecture Document**
- All architectural decisions documented with specific versions
- Implementation patterns ensuring AI agent consistency
- Complete project structure with all files and directories
- Requirements to architecture mapping
- Validation confirming coherence and completeness

**Implementation Ready Foundation**
- 20+ architectural decisions made
- 15+ implementation patterns defined
- 6 feature areas + cross-cutting concerns specified
- 38 functional + 18 non-functional requirements fully supported

**AI Agent Implementation Guide**
- Technology stack with verified versions
- Consistency rules that prevent implementation conflicts
- Project structure with clear boundaries
- Integration patterns and communication standards

### Development Sequence

1. Initialize project using documented starter templates
2. Set up development environment per architecture (Docker Compose)
3. Implement core architectural foundations (auth, data layer)
4. Build features following established patterns
5. Maintain consistency with documented rules

---

**Architecture Status:** READY FOR IMPLEMENTATION ✅

**Next Phase:** Create epics and stories, then begin implementation using the architectural decisions and patterns documented herein.

