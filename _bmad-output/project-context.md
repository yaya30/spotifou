---
project_name: 'spotifou'
user_name: 'ayaco'
date: '2026-02-02'
sections_completed: ['technology_stack', 'language_rules', 'framework_rules', 'testing_rules', 'quality_rules', 'workflow_rules', 'anti_patterns']
status: 'complete'
rule_count: 45
optimized_for_llm: true
---

# Project Context for AI Agents

_This file contains critical rules and patterns that AI agents must follow when implementing code in this project. Focus on unobvious details that agents might otherwise miss._

---

## Technology Stack & Versions

### Frontend
| Technology | Version | Notes |
|------------|---------|-------|
| Node.js | 24.0.13 | Required runtime |
| React | 18+ | With strict mode |
| Vite | 7 | Build tool with SWC |
| TypeScript | strict | Path alias: `~/` → `app/` |
| TanStack Query | latest | Server state management |
| Zustand | latest | UI state only |
| Tailwind CSS | 4 | With CSS variables |
| Shadcn/ui | latest | Copy-paste components |
| React Router | v7 Framework | File-based routing (`app/routes/`) |
| React Hook Form | latest | With Zod validation |
| Framer Motion | latest | Animations |
| @react-oauth/google | latest | OAuth client |

### Backend
| Technology | Version | Notes |
|------------|---------|-------|
| Java | 21 | LTS version |
| Spring Boot | 4.0.2 | Main framework |
| Spring Security | 7 | OAuth2 Resource Server |
| Spring Data JPA | - | With Hibernate |
| PostgreSQL | latest | Primary database |
| Flyway | latest | SQL migrations |
| Lombok | latest | Boilerplate reduction |
| springdoc-openapi | latest | Swagger/OpenAPI |

---

## Critical Implementation Rules

### Language-Specific Rules

**TypeScript (Frontend):**
- **Strict mode enabled** - never use `any` type, always define interfaces
- **Path alias** - use `~/` for all imports from `app/` (e.g., `~/components/ui/button`)
- **File extensions** - use `.tsx` for components, `.ts` for utilities/hooks/types
- **Named exports** - prefer named exports over default exports (except pages)
- **Type definitions** - co-locate types in feature `types/` folders, shared types in `app/types/`

**Java (Backend):**
- **Lombok required** - use `@Data`, `@Builder`, `@RequiredArgsConstructor` to reduce boilerplate
- **Record classes** - use Java records for DTOs when immutability is desired
- **Optional usage** - return `Optional<T>` from repository methods, never return null
- **Stream API** - prefer streams over loops for collection processing
- **Constructor injection** - use `@RequiredArgsConstructor` instead of `@Autowired` on fields

### Framework-Specific Rules

**React Rules:**
- **TanStack Query for server state** - never use useState/useEffect for API data
  - Use `useQuery` for GET requests
  - Use `useMutation` for POST/PUT/DELETE
  - Configure `staleTime` per query type (catalog: longer, user data: shorter)
- **Zustand for UI state only** - player state, sidebar state, modal state
  - Never mix server state in Zustand stores
  - Use selectors to prevent unnecessary re-renders
- **Component organization:**
  - Feature components in `app/features/{name}/components/`
  - Shared UI in `app/components/common/`
  - Shadcn primitives in `app/components/ui/`
  - Routes in `app/routes/` (file-based routing)
- **React Hook Form + Zod** - all forms must use this combination
  - Define Zod schema first, infer TypeScript types from it
- **Error boundaries** - wrap feature sections, not individual components

**Spring Boot Rules:**
- **Layer separation** - Controller → Service → Repository (never skip layers)
- **DTOs required** - never expose JPA entities directly in API responses
  - Request DTOs in `dto/request/`
  - Response DTOs in `dto/response/`
- **Service methods** - business logic only, no HTTP concerns
- **Repository methods** - use Spring Data method naming or `@Query` annotations
- **Exception handling** - throw custom exceptions, let `GlobalExceptionHandler` convert to RFC 7807
- **Validation** - use `@Valid` on controller parameters, `@NotNull`/`@Size` etc. on DTOs

### Testing Rules

**Frontend Testing:**
- **Test file location** - co-located with components: `ComponentName.test.tsx`
- **Test naming** - describe block = component name, it blocks = behavior descriptions
- **Mock API calls** - use MSW (Mock Service Worker) or mock TanStack Query
- **Component testing priority:**
  1. User interactions (clicks, form submissions)
  2. Conditional rendering
  3. Error states
- **Never test implementation details** - test behavior, not internal state

**Backend Testing:**
- **Test location** - mirror structure in `src/test/java/com/spotifou/`
- **Unit tests** - `@ExtendWith(MockitoExtension.class)` for service tests
- **Integration tests** - `@SpringBootTest` + `@AutoConfigureMockMvc` for controller tests
- **Database tests** - use `@DataJpaTest` with H2 for repository tests
- **Test data** - use `@Builder` pattern for creating test entities
- **Naming convention** - `methodName_condition_expectedResult`

**Test Coverage:**
- Focus on critical paths: auth flow, playlist operations, playback controls
- Don't chase 100% coverage - prioritize business logic

### Code Quality & Style Rules

**Naming Conventions:**

| Context | Convention | Example |
|---------|------------|---------|
| React components | PascalCase | `TrackRow.tsx`, `PlayerBar.tsx` |
| React hooks | camelCase with `use` prefix | `usePlayer.ts`, `useAuth.ts` |
| TypeScript utilities | camelCase | `formatDuration.ts` |
| Java classes | PascalCase | `UserService.java` |
| Java methods | camelCase | `findByEmail()` |
| Database tables | snake_case, plural | `users`, `playlist_tracks` |
| Database columns | snake_case | `user_id`, `created_at` |
| API endpoints | kebab-case, plural | `/api/v1/playlist-tracks` |
| JSON fields | camelCase | `createdAt`, `trackName` |

**File Organization:**
- One component per file (except small related components)
- Index files only for re-exports, no logic
- Keep files under 300 lines - split if larger

**Code Style:**
- No magic numbers - use named constants
- Early returns over nested conditions
- Destructure props and state at function start
- Prefer `const` over `let`, never use `var`

### Development Workflow Rules

**Local Development:**
- Start PostgreSQL via `docker-compose up -d`
- Backend runs on port 8080, frontend on port 5173
- Use Spring profile `dev` for local development
- Frontend `.env` must have `VITE_API_URL=http://localhost:8080/api/v1`

**API Design:**
- All endpoints under `/api/v1/` prefix
- Response format: `{ "data": {...}, "meta": {...} }`
- Error format: RFC 7807 Problem Details
- Pagination: `?page=0&size=20&sort=createdAt,desc`
- Always return empty arrays `[]`, never null for lists

**Database Migrations:**
- Flyway migrations in `resources/db/migration/`
- Naming: `V{n}__{description}.sql` (double underscore)
- Never modify existing migrations - create new ones
- Test migrations on fresh database before commit

**Authentication Flow:**
1. Frontend: Google OAuth → receive ID token
2. POST `/api/v1/auth/google` with token in body
3. Backend validates with Google, creates/updates user
4. Backend sets httpOnly cookie with session token
5. All subsequent requests auto-include cookie

### Critical Don't-Miss Rules

**Security - MUST FOLLOW:**
- NEVER store tokens in localStorage - use httpOnly cookies only
- NEVER expose Google client secret in frontend code
- NEVER trust client-side user ID - always extract from validated token
- ALWAYS validate JWT on every protected endpoint
- ALWAYS use parameterized queries (JPA handles this, but never concatenate SQL)

**Anti-Patterns to Avoid:**
- ❌ `useEffect` for data fetching → ✅ use TanStack Query
- ❌ Prop drilling through 3+ levels → ✅ use Zustand or context
- ❌ `@Autowired` on fields → ✅ constructor injection with `@RequiredArgsConstructor`
- ❌ Returning JPA entities from controllers → ✅ map to DTOs
- ❌ Business logic in controllers → ✅ keep in service layer
- ❌ Catching generic `Exception` → ✅ catch specific exceptions

**Data Model Gotchas:**
- Album ↔ Artist is **many-to-many** (via `album_artists` table)
- Playlist ↔ User is **many-to-many** (via `playlist_users` with role)
- Always check user role before playlist modifications
- Track belongs to ONE album (many-to-one), but album has multiple artists

**Frontend State Gotchas:**
- Player state persists across page navigation (global Zustand store)
- Auth state must be checked on app initialization (before routing)
- TanStack Query cache invalidation after mutations is required

**API Response Gotchas:**
- Dates must be ISO 8601 format: `2026-02-02T14:30:00Z`
- IDs are `Long` type (not UUID)
- Empty results return `{ "data": [], "meta": { "total": 0 } }`, not 404

---

## Usage Guidelines

**For AI Agents:**
- Read this file before implementing any code
- Follow ALL rules exactly as documented
- When in doubt, prefer the more restrictive option
- Reference `architecture.md` for detailed structural decisions

**For Humans:**
- Keep this file lean and focused on agent needs
- Update when technology stack changes
- Review quarterly for outdated rules
- Remove rules that become obvious over time

---

_Last Updated: 2026-02-02_

