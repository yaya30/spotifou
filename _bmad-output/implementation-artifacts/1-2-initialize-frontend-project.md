# Story 1.2: Initialize Frontend Project

Status: done

## Story

As a **developer**,
I want **a React frontend with Vite, TypeScript, and Shadcn/ui initialized**,
so that **I can start building UI components with the chosen stack**.

## Acceptance Criteria

1. **AC1:** `frontend/` contains a Vite + React + TypeScript project
2. **AC2:** Shadcn/ui is initialized with Slate theme and CSS variables enabled
3. **AC3:** TanStack Query, Zustand, React Router, Framer Motion, and Lucide React are installed
4. **AC4:** Path alias `@/` is configured to resolve to `src/`
5. **AC5:** Tailwind CSS 4 is configured and working
6. **AC6:** `npm run dev` starts the development server on port 5173
7. **AC7:** The default page renders without errors in the browser

## Tasks / Subtasks

- [ ] **Task 1: Create Vite React TypeScript project** (AC: 1)
  - [ ] Run `npm create vite@latest frontend -- --template react-ts` from project root
  - [ ] Verify TypeScript strict mode is enabled in `tsconfig.json`
  - [ ] Update `vite.config.ts` with path alias configuration

- [ ] **Task 2: Initialize Shadcn/ui** (AC: 2, 5)
  - [ ] Run `npx shadcn@latest init` inside `frontend/`
  - [ ] Select options: Style=Default, Base color=Slate, CSS variables=Yes
  - [ ] Verify `components.json` is created with correct configuration
  - [ ] Verify Tailwind CSS 4 is configured (`tailwind.config.js` or `tailwind.config.ts`)

- [ ] **Task 3: Install required dependencies** (AC: 3)
  - [ ] Run: `npm install @tanstack/react-query zustand react-router-dom framer-motion lucide-react`
  - [ ] Verify all packages are listed in `package.json`

- [ ] **Task 4: Configure path alias** (AC: 4)
  - [ ] Update `tsconfig.json` with paths configuration
  - [ ] Update `vite.config.ts` with resolve alias
  - [ ] Test that `@/` imports work correctly

- [ ] **Task 5: Create basic folder structure** (AC: 1)
  - [ ] Create `src/components/ui/` (for Shadcn components)
  - [ ] Create `src/components/common/` (for app-wide components)
  - [ ] Create `src/features/` (for feature modules)
  - [ ] Create `src/hooks/` (for shared hooks)
  - [ ] Create `src/stores/` (for Zustand stores)
  - [ ] Create `src/services/` (for API utilities)
  - [ ] Create `src/lib/` (for utilities)
  - [ ] Create `src/types/` (for shared types)
  - [ ] Create `src/pages/` (for page components)
  - [ ] Create `src/router/` (for React Router config)

- [ ] **Task 6: Create environment configuration** (AC: 6)
  - [ ] Create `frontend/.env.example` with documented variables
  - [ ] Create `frontend/.env` for local development
  - [ ] Ensure `.env` is in `.gitignore`

- [ ] **Task 7: Verify setup** (AC: 6, 7)
  - [ ] Run `npm run dev`
  - [ ] Verify server starts on port 5173
  - [ ] Verify page renders without errors in browser
  - [ ] Verify no TypeScript or build errors

## Dev Notes

### Architecture Compliance

**From Architecture Document:**
- Frontend uses Vite 7 with SWC for fast builds
- TypeScript strict mode is mandatory
- Feature-based folder structure (`features/{name}/components/`, `features/{name}/hooks/`, etc.)
- Shadcn/ui for component primitives (copy-paste, fully customizable)
- TanStack Query for server state (never use useState/useEffect for API data)
- Zustand for UI state only (player, sidebar, modals)

### Technical Requirements

**Initialization Commands (exact order):**

```bash
# 1. Create Vite project (from spotifou/ root)
npm create vite@latest frontend -- --template react-ts

# 2. Navigate to frontend
cd frontend

# 3. Install dependencies
npm install

# 4. Initialize Shadcn/ui
npx shadcn@latest init
# Select: Style = Default, Base color = Slate, CSS variables = Yes

# 5. Install additional dependencies
npm install @tanstack/react-query zustand react-router-dom framer-motion lucide-react
```

**tsconfig.json Path Alias Configuration:**

```json
{
  "compilerOptions": {
    "baseUrl": ".",
    "paths": {
      "@/*": ["./src/*"]
    }
  }
}
```

**vite.config.ts with Path Alias:**

```typescript
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import path from 'path'

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    port: 5173,
  },
})
```

**Environment Variables (.env.example):**

```bash
# API Configuration
VITE_API_URL=http://localhost:8080/api/v1

# Google OAuth (to be configured in Story 2.3)
VITE_GOOGLE_CLIENT_ID=your-google-client-id
```

### Folder Structure Requirements

**Required structure after this story:**

```
frontend/
├── package.json
├── package-lock.json
├── vite.config.ts
├── tsconfig.json
├── tsconfig.node.json
├── tailwind.config.js (or .ts)
├── postcss.config.js
├── components.json            # Shadcn configuration
├── index.html
├── .env
├── .env.example
├── .eslintrc.cjs
├── .prettierrc (optional)
│
├── public/
│   └── favicon.ico
│
└── src/
    ├── main.tsx               # App entry point
    ├── App.tsx                # Root component
    ├── index.css              # Global styles + Tailwind
    ├── vite-env.d.ts
    │
    ├── components/
    │   ├── ui/                # Shadcn components (empty for now)
    │   └── common/            # App-wide shared components (empty for now)
    │
    ├── features/              # Feature modules (empty for now)
    │
    ├── pages/                 # Page components (empty for now)
    │
    ├── hooks/                 # Shared hooks (empty for now)
    │
    ├── stores/                # Zustand stores (empty for now)
    │
    ├── services/              # API utilities (empty for now)
    │
    ├── lib/
    │   └── utils.ts           # cn() helper from Shadcn
    │
    ├── types/                 # Shared TypeScript types (empty for now)
    │
    └── router/                # React Router configuration (empty for now)
```

### Design System Configuration

**From UX Design Specification:**

- **Theme:** Midnight Blue (Dark)
- **Primary Accent:** `#3B82F6` (blue-500)
- **Background:** `#0F172A` (slate-900)
- **Surface:** `#1E293B` (slate-800)
- **Font:** Inter (system font via Tailwind defaults)
- **Base spacing:** 8px

**CSS Variables (index.css) - Will be configured by Shadcn init:**

```css
@tailwind base;
@tailwind components;
@tailwind utilities;

@layer base {
  :root {
    --background: 222.2 84% 4.9%;
    --foreground: 210 40% 98%;
    /* ... other Shadcn CSS variables */
  }
}
```

### Critical Implementation Rules

**From project-context.md:**

1. **TypeScript Strict Mode** - Never use `any` type, always define interfaces
2. **Path Alias** - Use `@/` for ALL imports from `src/`
3. **File Extensions** - `.tsx` for components, `.ts` for utilities/hooks/types
4. **Named Exports** - Prefer named exports over default exports (except pages)
5. **Folder Structure** - Feature components in `features/{name}/components/`

### Dependencies Version Check

Ensure the following packages are installed:

| Package | Purpose |
|---------|---------|
| `@tanstack/react-query` | Server state management |
| `zustand` | UI state management |
| `react-router-dom` | Client-side routing |
| `framer-motion` | Animations |
| `lucide-react` | Icons (Shadcn default) |

### .gitignore Updates

Add these entries to the root `.gitignore` if not present:

```gitignore
# Frontend
frontend/node_modules/
frontend/dist/
frontend/.env
frontend/.env.local
frontend/.vite/
```

### Verification Checklist

After completing all tasks, verify:

- [ ] `npm run dev` starts without errors
- [ ] Browser shows React app at `http://localhost:5173`
- [ ] No TypeScript errors in console or terminal
- [ ] `@/` imports resolve correctly (test with a sample import)
- [ ] Shadcn Button component can be added: `npx shadcn@latest add button`

### Project Structure Notes

- This story creates the **foundation** for all frontend work
- No actual UI components are built yet (that's Stories 2.3+, 3.2+, 4.2+)
- The folder structure follows the architecture document exactly
- Empty folders with `.gitkeep` files ensure structure is committed

### References

- [Source: architecture.md#Starter Template Evaluation]
- [Source: architecture.md#Frontend Architecture]
- [Source: architecture.md#Project Structure & Boundaries]
- [Source: ux-design-specification.md#Design System Foundation]
- [Source: project-context.md#Technology Stack & Versions]
- [Source: epics.md#Story 1.2]

## Dev Agent Record

### Agent Model Used

_To be filled by dev agent_

### Debug Log References

_To be filled during implementation_

### Completion Notes List

_To be filled after implementation_

### File List

**Files to create:**
- `frontend/` (entire Vite project)
- `frontend/.env.example`
- `frontend/.env`
- `frontend/src/components/ui/.gitkeep`
- `frontend/src/components/common/.gitkeep`
- `frontend/src/features/.gitkeep`
- `frontend/src/pages/.gitkeep`
- `frontend/src/hooks/.gitkeep`
- `frontend/src/stores/.gitkeep`
- `frontend/src/services/.gitkeep`
- `frontend/src/types/.gitkeep`
- `frontend/src/router/.gitkeep`

**Files to modify:**
- `frontend/vite.config.ts` (add path alias)
- `frontend/tsconfig.json` (add path alias)
- Root `.gitignore` (add frontend entries)
