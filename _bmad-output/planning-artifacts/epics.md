---
stepsCompleted: [1, 2, 3, 4]
inputDocuments:
  - '_bmad-output/planning-artifacts/prd.md'
  - '_bmad-output/planning-artifacts/architecture.md'
  - '_bmad-output/planning-artifacts/ux-design-specification.md'
---

# spotifou - Epic Breakdown

## Overview

This document provides the complete epic and story breakdown for spotifou, decomposing the requirements from the PRD, UX Design, and Architecture into implementable stories.

## Requirements Inventory

### Functional Requirements

**User Authentication & Account Management (6)**
- FR1: Users can register a new account with email and password
- FR2: Users can log in with existing credentials
- FR3: Users can log out of their account
- FR4: Users can remain logged in across browser sessions (token persistence)
- FR5: System automatically redirects unauthenticated users to login
- FR6: Users can view their profile information

**Music Catalog & Discovery (5)**
- FR7: Users can browse all available artists
- FR8: Users can view an artist's albums
- FR9: Users can view an album's track list
- FR10: Users can view track details (title, artist, album, duration)
- FR11: Users can browse the music catalog from the home dashboard

**Audio Playback (6)**
- FR12: Users can play a track
- FR13: Users can pause a playing track
- FR14: Users can see playback progress (current time / total duration)
- FR15: Users can seek to a specific position in a track
- FR16: Users can see which track is currently playing
- FR17: Users can control playback from a persistent player component

**Search (5)**
- FR18: Users can search for tracks by title
- FR19: Users can search for artists by name
- FR20: Users can search for albums by title
- FR21: Users can see search results in a unified list
- FR22: System displays "no results" message when search yields nothing

**Playlist Management (8)**
- FR23: Users can create a new playlist with a name
- FR24: Users can view their playlists
- FR25: Users can view tracks within a playlist
- FR26: Users can add a track to a playlist
- FR27: Users can remove a track from a playlist
- FR28: Users can delete a playlist
- FR29: Users can rename a playlist
- FR30: Users can play all tracks in a playlist sequentially

**User Library - Favorites (4)**
- FR31: Users can add a track to their favorites
- FR32: Users can remove a track from their favorites
- FR33: Users can view all their favorited tracks
- FR34: Users can see if a track is already favorited

**Error Handling & Edge Cases (4)**
- FR35: System displays empty state messages for empty lists (library, playlists, search)
- FR36: System displays error messages when operations fail
- FR37: System provides retry option for failed network requests
- FR38: System gracefully handles session expiration

### NonFunctional Requirements

**Performance (5)**
- NFR1: Page load time < 3 seconds
- NFR2: API response time < 200ms for standard operations
- NFR3: Audio playback starts within 500ms of play action
- NFR4: Search results return within 500ms
- NFR5: UI interactions respond within 100ms

**Security (5)**
- NFR6: Passwords are hashed using bcrypt (never stored in plaintext)
- NFR7: Authentication uses JWT tokens with expiration
- NFR8: All protected API endpoints require valid authentication token
- NFR9: All traffic encrypted via HTTPS
- NFR10: Server-side validation on all user inputs

**Scalability (5)**
- NFR11: Backend architecture supports horizontal scaling (stateless services)
- NFR12: Database design supports efficient querying at scale (proper indexing)
- NFR13: API design follows RESTful patterns for future load balancing
- NFR14: Frontend state management supports growing data sets (pagination, virtualization-ready)
- NFR15: Caching strategy in place for frequently accessed data (catalog, user preferences)

**Reliability (3)**
- NFR16: System provides graceful error handling with user feedback
- NFR17: User data (playlists, favorites) persists reliably across sessions
- NFR18: Session state recovers after browser refresh via token persistence

### Additional Requirements

**From Architecture:**
- **STARTER TEMPLATE**: Frontend via `npm create vite@latest` + Shadcn/ui init; Backend via Spring Initializr
- **AUTH OVERRIDE**: Architecture specifies Google OAuth (not email/password). FR1-FR2 should use Google OAuth instead.
- Docker Compose setup for PostgreSQL required
- Flyway migrations for database schema
- API endpoints under `/api/v1/` prefix
- httpOnly cookies for session management
- OpenAPI/Swagger documentation for all endpoints
- RFC 7807 error response format

**From UX Design:**
- Desktop-first responsive design (tablet secondary)
- Persistent bottom player bar (never reloads on navigation)
- Midnight Blue dark theme with 8px spacing base
- Framer Motion for animations
- Keyboard shortcuts for power users (spacebar for play/pause)
- Classic Spotify-style layout (sidebar + main content + bottom player)
- Empty state guidance with clear CTAs
- Skeleton loading components

### FR Coverage Map

| FR | Epic | Description |
|----|------|-------------|
| FR1 | Epic 2 | Google OAuth sign-in (replaces email/password) |
| FR2 | Epic 2 | Google OAuth login flow |
| FR3 | Epic 2 | Logout functionality |
| FR4 | Epic 2 | Session persistence via httpOnly cookie |
| FR5 | Epic 2 | Redirect unauthenticated users |
| FR6 | Epic 2 | View profile information |
| FR7 | Epic 3 | Browse all artists |
| FR8 | Epic 3 | View artist's albums |
| FR9 | Epic 3 | View album's track list |
| FR10 | Epic 3 | View track details |
| FR11 | Epic 3 | Browse catalog from home |
| FR12 | Epic 4 | Play a track |
| FR13 | Epic 4 | Pause a track |
| FR14 | Epic 4 | See playback progress |
| FR15 | Epic 4 | Seek to position |
| FR16 | Epic 4 | See current track info |
| FR17 | Epic 4 | Persistent player controls |
| FR18 | Epic 5 | Search tracks by title |
| FR19 | Epic 5 | Search artists by name |
| FR20 | Epic 5 | Search albums by title |
| FR21 | Epic 5 | Unified search results |
| FR22 | Epic 5 | No results message |
| FR23 | Epic 6 | Create playlist |
| FR24 | Epic 6 | View playlists |
| FR25 | Epic 6 | View playlist tracks |
| FR26 | Epic 6 | Add track to playlist |
| FR27 | Epic 6 | Remove track from playlist |
| FR28 | Epic 6 | Delete playlist |
| FR29 | Epic 6 | Rename playlist |
| FR30 | Epic 6 | Play playlist sequentially |
| FR31 | Epic 7 | Add to favorites |
| FR32 | Epic 7 | Remove from favorites |
| FR33 | Epic 7 | View all favorites |
| FR34 | Epic 7 | See favorite indicator |
| FR35 | Epic 8 | Empty state messages |
| FR36 | Epic 8 | Error messages |
| FR37 | Epic 8 | Retry option |
| FR38 | Epic 8 | Session expiration handling |

## Epic List

### Epic 1: Foundation & Dev Environment
Users can have a working development environment with both frontend and backend projects initialized and connected.
**FRs covered:** Architecture requirements (starter templates, Docker, database)

### Epic 2: User Authentication
Users can sign in with their Google account, stay logged in across sessions, and securely access protected features.
**FRs covered:** FR1, FR2, FR3, FR4, FR5, FR6

### Epic 3: Music Catalog Browsing
Users can browse the complete music catalog, exploring artists, their albums, and individual tracks from the home dashboard.
**FRs covered:** FR7, FR8, FR9, FR10, FR11

### Epic 4: Audio Playback
Users can play music with a persistent player that shows progress, allows seeking, and remains accessible across all views.
**FRs covered:** FR12, FR13, FR14, FR15, FR16, FR17

### Epic 5: Search & Discovery
Users can search for tracks, artists, and albums with instant results and clear feedback when nothing is found.
**FRs covered:** FR18, FR19, FR20, FR21, FR22

### Epic 6: Playlist Management
Users can create, manage, and play custom playlists to organize their music exactly how they want.
**FRs covered:** FR23, FR24, FR25, FR26, FR27, FR28, FR29, FR30

### Epic 7: User Library (Favorites)
Users can mark tracks as favorites and quickly access their personal collection of liked music.
**FRs covered:** FR31, FR32, FR33, FR34

### Epic 8: Error Handling & Polish
Users experience graceful handling of empty states, errors, and session issues with clear guidance.
**FRs covered:** FR35, FR36, FR37, FR38

---

## Epic 1: Foundation & Dev Environment

Users can have a working development environment with both frontend and backend projects initialized and connected.

### Story 1.1: Initialize Monorepo Structure

As a **developer**,
I want **a monorepo with frontend and backend folders and shared configuration**,
So that **I can develop both parts of the application in a single repository**.

**Acceptance Criteria:**

**Given** an empty project directory
**When** the monorepo structure is created
**Then** the following structure exists:
- `spotifou/` root with README.md
- `spotifou/frontend/` directory
- `spotifou/backend/` directory
- `spotifou/docker-compose.yml` with PostgreSQL service
- `spotifou/.gitignore` with appropriate entries
**And** `docker-compose up -d` successfully starts PostgreSQL on port 5432

### Story 1.2: Initialize Frontend Project

As a **developer**,
I want **a React frontend with Vite, TypeScript, and Shadcn/ui initialized**,
So that **I can start building UI components with the chosen stack**.

**Acceptance Criteria:**

**Given** the monorepo structure from Story 1.1
**When** the frontend is initialized
**Then** `frontend/` contains:
- Vite + React + TypeScript project
- Shadcn/ui initialized with Slate theme and CSS variables
- TanStack Query, Zustand, React Router, Framer Motion installed
- Path alias `@/` configured for `src/`
- Tailwind CSS 4 configured
**And** `npm run dev` starts the development server on port 5173
**And** the default Vite page renders without errors

### Story 1.3: Initialize Backend Project

As a **developer**,
I want **a Spring Boot backend with JPA, Security, and Flyway initialized**,
So that **I can start building API endpoints with the chosen stack**.

**Acceptance Criteria:**

**Given** the monorepo structure from Story 1.1
**When** the backend is initialized via Spring Initializr
**Then** `backend/` contains:
- Spring Boot 4.0.2 Maven project
- Dependencies: Web, JPA, Security, PostgreSQL, Validation, Lombok
- `application.yml` with dev profile for PostgreSQL connection
- `application-dev.yml` pointing to Docker PostgreSQL
- springdoc-openapi dependency for Swagger UI
**And** `./mvnw spring-boot:run` starts the application on port 8080
**And** Swagger UI is accessible at `/swagger-ui.html`

### Story 1.4: Configure CORS and API Base Path

As a **developer**,
I want **CORS configured to allow frontend requests and API versioning set up**,
So that **frontend can communicate with backend under `/api/v1/` prefix**.

**Acceptance Criteria:**

**Given** the backend project from Story 1.3
**When** CORS and API configuration is added
**Then** `CorsConfig.java` allows requests from `http://localhost:5173`
**And** all API endpoints are prefixed with `/api/v1/`
**And** a health check endpoint `GET /api/v1/health` returns `{"status": "ok"}`
**And** frontend can successfully call the health endpoint without CORS errors

---

## Epic 2: User Authentication

Users can sign in with their Google account, stay logged in across sessions, and securely access protected features.

### Story 2.1: Create User Entity and Repository

As a **developer**,
I want **a User entity and repository to store authenticated users**,
So that **user data can be persisted after Google OAuth login**.

**Acceptance Criteria:**

**Given** the backend project with Flyway configured
**When** the User entity is created
**Then** a Flyway migration `V1__create_users_table.sql` creates the `users` table with:
- `id` (BIGSERIAL PRIMARY KEY)
- `email` (VARCHAR UNIQUE NOT NULL)
- `name` (VARCHAR)
- `picture_url` (VARCHAR)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)
**And** `User.java` entity maps to this table with Lombok annotations
**And** `UserRepository.java` extends JpaRepository with `findByEmail(String email)`

### Story 2.2: Implement Google OAuth Backend Authentication

As a **user**,
I want **to authenticate with my Google account**,
So that **I can access the application without creating a new password**.

**Acceptance Criteria:**

**Given** a valid Google ID token from the frontend
**When** I POST to `/api/v1/auth/google` with `{ "idToken": "..." }`
**Then** the backend validates the token with Google
**And** creates a new user if email doesn't exist, or finds existing user
**And** sets an httpOnly cookie with a session token
**And** returns `{ "data": { "id", "email", "name", "pictureUrl" } }`

**Given** an invalid or expired Google ID token
**When** I POST to `/api/v1/auth/google`
**Then** the response is 401 Unauthorized with RFC 7807 error format

### Story 2.3: Implement Frontend Google OAuth Login

As a **user**,
I want **to click a "Sign in with Google" button**,
So that **I can authenticate quickly without typing credentials**.

**Acceptance Criteria:**

**Given** I am on the login page and not authenticated
**When** I click "Sign in with Google"
**Then** the Google OAuth popup appears
**And** after successful Google auth, the ID token is sent to backend
**And** on success, I am redirected to the home dashboard
**And** my name and profile picture are displayed in the UI

**Given** I cancel the Google OAuth popup
**When** the popup closes
**Then** I remain on the login page with no error

### Story 2.4: Implement Session Persistence and Protected Routes

As a **user**,
I want **to remain logged in when I refresh the page or return later**,
So that **I don't have to sign in every time**.

**Acceptance Criteria:**

**Given** I am authenticated with a valid session cookie
**When** I refresh the page or close and reopen the browser
**Then** I am still authenticated and can access protected routes
**And** the frontend fetches my user profile from `GET /api/v1/auth/me`

**Given** I am not authenticated (no cookie or expired)
**When** I try to access a protected route
**Then** I am redirected to the login page
**And** after login, I am redirected back to my intended destination

### Story 2.5: Implement Logout

As a **user**,
I want **to log out of my account**,
So that **I can secure my session on shared devices**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I click the "Logout" button
**Then** POST `/api/v1/auth/logout` is called
**And** the httpOnly session cookie is cleared
**And** I am redirected to the login page
**And** I can no longer access protected routes

### Story 2.6: Display User Profile

As a **user**,
I want **to view my profile information**,
So that **I can see my account details**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I navigate to my profile or click my avatar
**Then** I see my name, email, and profile picture from Google
**And** the data comes from `GET /api/v1/auth/me`

---

## Epic 3: Music Catalog Browsing

Users can browse the complete music catalog, exploring artists, their albums, and individual tracks from the home dashboard.

### Story 3.1: Create Catalog Entities and Seed Data

As a **developer**,
I want **Artist, Album, and Track entities with seed data**,
So that **users have music to browse in the catalog**.

**Acceptance Criteria:**

**Given** the backend project
**When** the catalog entities are created
**Then** Flyway migrations create:
- `artists` table (id, name, image_url, created_at)
- `albums` table (id, title, image_url, release_year, created_at)
- `album_artists` join table (album_id, artist_id, role)
- `tracks` table (id, title, duration_ms, track_number, album_id, audio_url, created_at)
**And** JPA entities with proper relationships are created
**And** a seed migration inserts at least 5 artists, 10 albums, 30 tracks

### Story 3.2: Implement Artist List API and UI

As a **user**,
I want **to browse all available artists**,
So that **I can discover music by artist**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I call `GET /api/v1/artists`
**Then** I receive a paginated list of artists with id, name, imageUrl
**And** the response format is `{ "data": [...], "meta": { "page", "size", "total" } }`

**Given** I am on the home dashboard or artists page
**When** the page loads
**Then** I see a grid of artist cards with name and image
**And** skeleton loading shows while data is fetching
**And** clicking an artist card navigates to the artist detail page

### Story 3.3: Implement Artist Detail and Albums API/UI

As a **user**,
I want **to view an artist's albums**,
So that **I can explore their discography**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I call `GET /api/v1/artists/{id}`
**Then** I receive the artist details with id, name, imageUrl
**And** I call `GET /api/v1/artists/{id}/albums`
**Then** I receive a list of albums for that artist

**Given** I am on an artist detail page
**When** the page loads
**Then** I see the artist name and image prominently
**And** I see a grid of their albums with title and cover art
**And** clicking an album navigates to the album detail page

### Story 3.4: Implement Album Detail and Track List API/UI

As a **user**,
I want **to view an album's track list**,
So that **I can see what songs are available**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I call `GET /api/v1/albums/{id}`
**Then** I receive album details with id, title, imageUrl, releaseYear, artists
**And** I call `GET /api/v1/albums/{id}/tracks`
**Then** I receive a list of tracks with id, title, durationMs, trackNumber

**Given** I am on an album detail page
**When** the page loads
**Then** I see the album cover, title, artist(s), and release year
**And** I see a list of tracks with track number, title, and duration (formatted as mm:ss)
**And** hovering a track row shows a play button

### Story 3.5: Implement Home Dashboard with Catalog Browse

As a **user**,
I want **to browse the music catalog from the home dashboard**,
So that **I can quickly discover music when I open the app**.

**Acceptance Criteria:**

**Given** I am authenticated and on the home dashboard
**When** the page loads
**Then** I see sections for "Featured Artists" and "Recent Albums"
**And** each section shows a horizontal scrollable list of cards
**And** clicking any card navigates to the appropriate detail page
**And** the layout follows the UX design (sidebar + main content area)

---

## Epic 4: Audio Playback

Users can play music with a persistent player that shows progress, allows seeking, and remains accessible across all views.

### Story 4.1: Create Player Store and Audio Service

As a **developer**,
I want **a Zustand store and audio service to manage playback state**,
So that **audio state persists across navigation and components can access it**.

**Acceptance Criteria:**

**Given** the frontend project
**When** the player store is created
**Then** `playerStore.ts` manages:
- `currentTrack` (track object or null)
- `isPlaying` (boolean)
- `currentTime` (number in seconds)
- `duration` (number in seconds)
- `volume` (number 0-1)
**And** actions: `play(track)`, `pause()`, `resume()`, `setCurrentTime()`, `setVolume()`
**And** a singleton HTML5 Audio element is managed internally
**And** the store updates `currentTime` as the audio plays

### Story 4.2: Implement Persistent Player Bar Component

As a **user**,
I want **a player bar at the bottom of the screen that stays visible**,
So that **I can control playback from any page**.

**Acceptance Criteria:**

**Given** I am authenticated and have played a track
**When** I navigate between pages
**Then** the player bar remains fixed at the bottom
**And** it shows the current track's title, artist, and album art
**And** it displays play/pause button, progress bar, and volume control
**And** the layout matches the UX design (Spotify-style bottom bar)

**Given** no track has been played yet
**When** I view the app
**Then** the player bar is hidden or shows an empty state

### Story 4.3: Implement Play and Pause Functionality

As a **user**,
I want **to play and pause tracks**,
So that **I can control when music plays**.

**Acceptance Criteria:**

**Given** I am viewing a track list (album, playlist, or search results)
**When** I click the play button on a track row
**Then** that track starts playing via HTML5 Audio
**And** the player bar updates to show the track info
**And** the play button changes to a pause icon

**Given** a track is currently playing
**When** I click the pause button (in player bar or track row)
**Then** the audio pauses
**And** the pause button changes to a play icon
**And** clicking play again resumes from the same position

### Story 4.4: Implement Progress Display and Seeking

As a **user**,
I want **to see playback progress and skip to any position**,
So that **I can track where I am and jump around**.

**Acceptance Criteria:**

**Given** a track is playing
**When** I look at the player bar
**Then** I see current time and total duration (formatted as m:ss)
**And** a progress bar shows the current position visually
**And** the progress updates in real-time as the track plays

**Given** a track is playing or paused
**When** I click or drag on the progress bar
**Then** the playback jumps to that position
**And** the current time updates immediately
**And** if playing, audio continues from the new position

### Story 4.5: Implement Now Playing Display

As a **user**,
I want **to see which track is currently playing**,
So that **I know what I'm listening to**.

**Acceptance Criteria:**

**Given** a track is playing
**When** I view any page
**Then** the player bar shows:
- Album artwork (thumbnail)
- Track title
- Artist name(s)
**And** clicking the track info navigates to the album page

**Given** I am on a track list where the current track appears
**When** I view the list
**Then** the currently playing track is visually highlighted
**And** an animated "playing" indicator shows next to it

---

## Epic 5: Search & Discovery

Users can search for tracks, artists, and albums with instant results and clear feedback when nothing is found.

### Story 5.1: Implement Search API Endpoint

As a **user**,
I want **to search the music catalog via a single endpoint**,
So that **I can find tracks, artists, and albums with one query**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I call `GET /api/v1/search?q={query}`
**Then** I receive results grouped by type: `{ "data": { "tracks": [...], "artists": [...], "albums": [...] } }`
**And** each result includes id, name/title, and relevant metadata
**And** empty arrays are returned for types with no matches
**And** the response returns within 500ms

**Given** the query is less than 2 characters
**When** I call the search endpoint
**Then** I receive an empty result set (not an error)

### Story 5.2: Implement Search UI with Unified Results

As a **user**,
I want **a search bar that shows results as I type**,
So that **I can quickly find what I'm looking for**.

**Acceptance Criteria:**

**Given** I am on any authenticated page
**When** I click the search bar or press `/` (keyboard shortcut)
**Then** the search input is focused

**Given** I type 2+ characters in the search bar
**When** I pause typing (300ms debounce)
**Then** a search request is made to the API
**And** skeleton loading shows while fetching
**And** results appear grouped by Artists, Albums, Tracks

**Given** search results are displayed
**When** I click on an artist result
**Then** I navigate to the artist detail page
**When** I click on an album result
**Then** I navigate to the album detail page
**When** I click on a track result
**Then** that track starts playing

### Story 5.3: Implement No Results State

As a **user**,
I want **clear feedback when my search finds nothing**,
So that **I know the search worked but found no matches**.

**Acceptance Criteria:**

**Given** I search for a term that matches nothing
**When** the API returns empty results
**Then** I see a "No results found" message
**And** the message includes the search term I entered
**And** a suggestion to try different keywords is displayed

**Given** I clear the search input
**When** the input becomes empty
**Then** the search results panel closes or shows recent/suggested content

---

## Epic 6: Playlist Management

Users can create, manage, and play custom playlists to organize their music exactly how they want.

### Story 6.1: Create Playlist Entity and API

As a **developer**,
I want **a Playlist entity with tracks association**,
So that **users can have persistent playlists stored in the database**.

**Acceptance Criteria:**

**Given** the backend project
**When** the Playlist entity is created
**Then** Flyway migration creates:
- `playlists` table (id, name, user_id, created_at, updated_at)
- `playlist_tracks` join table (playlist_id, track_id, position, added_at)
**And** JPA entities with proper relationships are created
**And** `PlaylistRepository` extends JpaRepository with `findByUserId(Long userId)`

### Story 6.2: Create and View Playlists

As a **user**,
I want **to create new playlists and see all my playlists**,
So that **I can start organizing my music**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I call `POST /api/v1/playlists` with `{ "name": "My Playlist" }`
**Then** a new playlist is created for my user
**And** the response returns the playlist with id, name, trackCount (0)

**Given** I am authenticated
**When** I call `GET /api/v1/playlists`
**Then** I receive a list of my playlists with id, name, trackCount
**And** playlists are ordered by most recently updated

**Given** I am on the app with the sidebar visible
**When** I view the sidebar
**Then** I see a "Your Playlists" section with my playlists listed
**And** I see a "Create Playlist" button
**When** I click "Create Playlist"
**Then** a modal/dialog appears to enter the playlist name
**And** after entering a name and confirming, the playlist is created and appears in the sidebar

### Story 6.3: View Playlist Details and Tracks

As a **user**,
I want **to view a playlist's tracks**,
So that **I can see what music is in my playlist**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I call `GET /api/v1/playlists/{id}`
**Then** I receive playlist details with id, name, trackCount, tracks array
**And** each track includes id, title, artist, album, duration, position

**Given** I click on a playlist in the sidebar
**When** the playlist page loads
**Then** I see the playlist name as the page title
**And** I see a list of tracks with track number, title, artist, album, duration
**And** hovering a track shows play and remove buttons
**And** if the playlist is empty, I see an empty state with guidance to add tracks

### Story 6.4: Add Track to Playlist

As a **user**,
I want **to add tracks to my playlists**,
So that **I can build my custom collections**.

**Acceptance Criteria:**

**Given** I am authenticated and viewing a track (in album, search, or elsewhere)
**When** I click the "Add to Playlist" option (context menu or button)
**Then** I see a list of my playlists to choose from

**When** I select a playlist
**Then** `POST /api/v1/playlists/{id}/tracks` is called with `{ "trackId": ... }`
**And** the track is added to the end of the playlist
**And** I see a confirmation toast "Added to {playlist name}"

**Given** the track is already in the selected playlist
**When** I try to add it again
**Then** I see a message "Track already in playlist"

### Story 6.5: Remove Track from Playlist

As a **user**,
I want **to remove tracks from my playlists**,
So that **I can curate my collections**.

**Acceptance Criteria:**

**Given** I am viewing a playlist with tracks
**When** I click the remove button on a track row
**Then** a confirmation appears (or instant removal with undo)
**And** `DELETE /api/v1/playlists/{id}/tracks/{trackId}` is called
**And** the track is removed from the list
**And** remaining tracks maintain their order

### Story 6.6: Delete and Rename Playlist

As a **user**,
I want **to delete or rename my playlists**,
So that **I can manage my playlist collection**.

**Acceptance Criteria:**

**Given** I am viewing a playlist
**When** I click "Delete Playlist" in the playlist options
**Then** a confirmation dialog appears
**And** on confirm, `DELETE /api/v1/playlists/{id}` is called
**And** the playlist is removed from the sidebar
**And** I am redirected to home or another playlist

**Given** I am viewing a playlist
**When** I click "Rename" or double-click the playlist name
**Then** the name becomes editable
**And** on save, `PATCH /api/v1/playlists/{id}` is called with `{ "name": "..." }`
**And** the new name is reflected in the sidebar and page title

### Story 6.7: Play Playlist Sequentially

As a **user**,
I want **to play all tracks in a playlist in order**,
So that **I can enjoy my curated music continuously**.

**Acceptance Criteria:**

**Given** I am viewing a playlist with tracks
**When** I click the "Play" button on the playlist header
**Then** the first track starts playing
**And** the player store is updated with the playlist queue

**Given** a track from the playlist finishes playing
**When** the audio ends
**Then** the next track in the playlist starts automatically
**And** the player bar updates to show the new track

**Given** the last track in the playlist finishes
**When** the audio ends
**Then** playback stops (or loops if user has loop enabled)

---

## Epic 7: User Library (Favorites)

Users can mark tracks as favorites and quickly access their personal collection of liked music.

### Story 7.1: Create Favorites Entity and API

As a **developer**,
I want **a Favorites entity to store user's liked tracks**,
So that **users can have persistent favorites stored in the database**.

**Acceptance Criteria:**

**Given** the backend project
**When** the Favorites entity is created
**Then** Flyway migration creates:
- `user_favorites` table (id, user_id, track_id, created_at)
- Unique constraint on (user_id, track_id)
**And** JPA entity with proper relationships is created
**And** `FavoriteRepository` with `findByUserId(Long userId)` and `existsByUserIdAndTrackId(Long userId, Long trackId)`

### Story 7.2: Add and Remove Favorites

As a **user**,
I want **to add or remove tracks from my favorites**,
So that **I can quickly save music I love**.

**Acceptance Criteria:**

**Given** I am authenticated and viewing a track that is not favorited
**When** I click the heart/favorite icon
**Then** `POST /api/v1/favorites` is called with `{ "trackId": ... }`
**And** the heart icon fills in (visual feedback)
**And** the response confirms the favorite was added

**Given** I am authenticated and viewing a track that is already favorited
**When** I click the filled heart icon
**Then** `DELETE /api/v1/favorites/{trackId}` is called
**And** the heart icon becomes unfilled
**And** the response confirms the favorite was removed

**Given** I toggle favorite on a track
**When** the action completes
**Then** the UI updates optimistically (instant feedback)
**And** if the request fails, the UI reverts and shows an error

### Story 7.3: View Favorites Library

As a **user**,
I want **to view all my favorited tracks**,
So that **I can quickly access my liked music**.

**Acceptance Criteria:**

**Given** I am authenticated
**When** I call `GET /api/v1/favorites`
**Then** I receive a list of my favorited tracks with track details (id, title, artist, album, duration, favoritedAt)
**And** tracks are ordered by most recently favorited

**Given** I click "Favorites" or "Liked Songs" in the sidebar
**When** the favorites page loads
**Then** I see a list of all my favorited tracks
**And** each track shows title, artist, album, duration, and a filled heart icon
**And** hovering a track shows play button
**And** clicking a track plays it

**Given** I have no favorited tracks
**When** I view the favorites page
**Then** I see an empty state with message "No favorites yet"
**And** a CTA to browse music and add favorites

### Story 7.4: Display Favorite Status on Tracks

As a **user**,
I want **to see if a track is already favorited wherever it appears**,
So that **I know which tracks I've liked**.

**Acceptance Criteria:**

**Given** I am viewing tracks in album view, search results, or playlist
**When** the tracks load
**Then** each track shows a heart icon
**And** favorited tracks show a filled heart
**And** non-favorited tracks show an outline heart

**Given** the backend returns track data
**When** I call `GET /api/v1/albums/{id}/tracks` or similar endpoints
**Then** each track includes `isFavorited: boolean` field based on my user

**Given** I favorite/unfavorite a track from any view
**When** the action completes
**Then** all visible instances of that track update their heart icon state

---

## Epic 8: Error Handling & Polish

Users experience graceful handling of empty states, errors, and session issues with clear guidance.

### Story 8.1: Implement Global Error Handling

As a **user**,
I want **to see clear error messages when something goes wrong**,
So that **I understand what happened and what to do next**.

**Acceptance Criteria:**

**Given** an API request fails with a 4xx or 5xx error
**When** the response is received
**Then** the frontend parses the RFC 7807 error format
**And** displays a user-friendly error message (toast or inline)
**And** the error message is based on the error type, not raw technical details

**Given** a network error occurs (no internet, timeout)
**When** the request fails
**Then** I see "Connection error. Please check your internet."
**And** the error is distinguishable from server errors

**Given** an unexpected error occurs
**When** the error is caught
**Then** I see a generic "Something went wrong" message
**And** the error is logged for debugging (console in dev)

### Story 8.2: Implement Retry Functionality

As a **user**,
I want **to retry failed operations**,
So that **I can recover from temporary failures without refreshing**.

**Acceptance Criteria:**

**Given** an API request fails due to network or server error
**When** the error is displayed
**Then** a "Retry" button is shown alongside the error message

**Given** I click the "Retry" button
**When** the retry is initiated
**Then** the same request is made again
**And** a loading indicator shows during retry
**And** on success, the error clears and data loads
**And** on failure, the error message updates

**Given** TanStack Query is managing the request
**When** a query fails
**Then** I can use the built-in `refetch()` for retry functionality
**And** mutations show retry option in error toast

### Story 8.3: Implement Empty States

As a **user**,
I want **helpful guidance when lists are empty**,
So that **I know the feature works and how to populate it**.

**Acceptance Criteria:**

**Given** I have no playlists
**When** I view the playlists section
**Then** I see an empty state illustration/icon
**And** message "No playlists yet"
**And** a CTA button "Create your first playlist"

**Given** I have no favorites
**When** I view the favorites page
**Then** I see an empty state with "No liked songs"
**And** a CTA "Explore music to add favorites"

**Given** a search returns no results
**When** I view the search results
**Then** I see "No results for '{query}'"
**And** suggestions to try different keywords

**Given** a playlist has no tracks
**When** I view the playlist
**Then** I see "This playlist is empty"
**And** a CTA "Add tracks from your library"

### Story 8.4: Handle Session Expiration

As a **user**,
I want **graceful handling when my session expires**,
So that **I can re-authenticate without losing my place**.

**Acceptance Criteria:**

**Given** my session token has expired
**When** I make an API request
**Then** the backend returns 401 Unauthorized

**Given** the frontend receives a 401 response
**When** the error is processed
**Then** I see a modal/toast "Session expired. Please sign in again."
**And** I am redirected to the login page
**And** my intended destination is saved for post-login redirect

**Given** I was in the middle of an action (e.g., adding to playlist)
**When** my session expires
**Then** the action fails gracefully
**And** after re-login, I can retry the action

**Given** I refresh the page with an expired token
**When** the app loads
**Then** I am redirected to login before seeing protected content
**And** no flash of authenticated UI occurs

