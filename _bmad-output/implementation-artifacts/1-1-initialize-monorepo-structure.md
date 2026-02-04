# Story 1.1: Initialize Monorepo Structure

Status: ready-for-dev

## Story

As a **developer**,
I want **a monorepo with frontend and backend folders and shared configuration**,
so that **I can develop both parts of the application in a single repository**.

## Acceptance Criteria

1. **AC1:** Root directory contains README.md with project overview
2. **AC2:** `frontend/` directory exists (empty, ready for Story 1.2)
3. **AC3:** `backend/` directory exists (empty, ready for Story 1.3)
4. **AC4:** `docker-compose.yml` exists with PostgreSQL service configured
5. **AC5:** `.gitignore` contains appropriate entries for Node.js, Java/Maven, and IDE files
6. **AC6:** `.env.example` provides environment variable template
7. **AC7:** `docker-compose up -d` successfully starts PostgreSQL on port 5432

## Tasks / Subtasks

- [ ] **Task 1: Create root directory structure** (AC: 1, 2, 3)
  - [ ] Create `frontend/` directory with `.gitkeep`
  - [ ] Create `backend/` directory with `.gitkeep`
  - [ ] Create `README.md` with project overview

- [ ] **Task 2: Create Docker Compose configuration** (AC: 4, 7)
  - [ ] Create `docker-compose.yml` with PostgreSQL 16 service
  - [ ] Configure volume for data persistence
  - [ ] Set environment variables for database credentials
  - [ ] Expose port 5432

- [ ] **Task 3: Create environment configuration** (AC: 5, 6)
  - [ ] Create `.gitignore` with comprehensive entries
  - [ ] Create `.env.example` with documented variables

- [ ] **Task 4: Verify setup** (AC: 7)
  - [ ] Run `docker-compose up -d`
  - [ ] Verify PostgreSQL is accessible on port 5432
  - [ ] Test database connection

## Dev Notes

### Architecture Compliance

**From Architecture Document:**
- Monorepo structure with separate `frontend/` and `backend/` directories
- PostgreSQL database via Docker Compose for local development
- Environment variables managed via `.env` files

### Technical Requirements

**PostgreSQL Configuration:**
```yaml
# docker-compose.yml requirements
services:
  postgres:
    image: postgres:16-alpine
    container_name: spotifou-db
    environment:
      POSTGRES_USER: spotifou
      POSTGRES_PASSWORD: spotifou_dev
      POSTGRES_DB: spotifou
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

**Environment Variables (.env.example):**
```bash
# Database
POSTGRES_USER=spotifou
POSTGRES_PASSWORD=spotifou_dev
POSTGRES_DB=spotifou
POSTGRES_PORT=5432

# Frontend (to be used in Story 1.2)
VITE_API_URL=http://localhost:8080/api/v1
VITE_GOOGLE_CLIENT_ID=your-google-client-id

# Backend (to be used in Story 1.3)
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/spotifou
SPRING_DATASOURCE_USERNAME=spotifou
SPRING_DATASOURCE_PASSWORD=spotifou_dev
```

### .gitignore Requirements

Must include entries for:
- **Node.js:** `node_modules/`, `.env`, `dist/`, `.vite/`
- **Java/Maven:** `target/`, `*.jar`, `*.class`, `.mvn/wrapper/maven-wrapper.jar`
- **IDE:** `.idea/`, `.vscode/`, `*.iml`
- **OS:** `.DS_Store`, `Thumbs.db`
- **Docker:** Local overrides but NOT docker-compose.yml
- **Environment:** `.env` (but NOT `.env.example`)

### README.md Content Requirements

Include:
- Project name and description (Spotifou - Spotify clone for learning)
- Tech stack overview (React + Vite + Spring Boot + PostgreSQL)
- Prerequisites (Node.js 24+, Java 21, Docker)
- Quick start instructions
- Project structure overview
- Link to architecture document

### Project Structure Notes

**Final structure after this story:**
```
spotifou/
├── README.md
├── docker-compose.yml
├── .gitignore
├── .env.example
├── frontend/
│   └── .gitkeep
└── backend/
    └── .gitkeep
```

### References

- [Source: architecture.md#Starter Template Evaluation]
- [Source: architecture.md#Infrastructure & Deployment]
- [Source: architecture.md#Project Structure & Boundaries]
- [Source: epics.md#Story 1.1]

## Dev Agent Record

### Agent Model Used

_To be filled by dev agent_

### Debug Log References

_To be filled during implementation_

### Completion Notes List

_To be filled after implementation_

### File List

**Files to create:**
- `README.md`
- `docker-compose.yml`
- `.gitignore`
- `.env.example`
- `frontend/.gitkeep`
- `backend/.gitkeep`
