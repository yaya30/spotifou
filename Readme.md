# Spotifou                                                                 
                                                                             
  A Spotify clone built for learning full-stack web development with modern  
  technologies.                                                              
                                                                             
  ## Tech Stack                                                              
                                                                             
  **Frontend:**                                                              
  - React 18+ with TypeScript                                                
  - Vite 7 (build tool)                                                      
  - TanStack Query (server state)                                            
  - Zustand (UI state)                                                       
  - Tailwind CSS + Shadcn/ui                                                 
  - Framer Motion (animations)                                               
                                                                             
  **Backend:**                                                               
  - Spring Boot 4.0.2                                                        
  - Spring Data JPA                                                          
  - Spring Security (Google OAuth)                                           
  - PostgreSQL 18                                                            
                                                                             
  **Infrastructure:**                                                        
  - Docker Compose (local development)                                       
  - Flyway (database migrations)                                             
                                                                             
  ## Prerequisites                                                           
                                                                             
  - Node.js 24+                                                              
  - Java 21                                                                  
  - Docker & Docker Compose                                                  
  - Google Cloud Console account (for OAuth)                                 
                                                                             
  ## Quick Start                                                             
                                                                             
  1. **Clone the repository**                                                
     ```bash                                                                 
     git clone <repository-url>                                              
     cd spotifou                                                             
                                                                             
  2. Start the database                                                      
  docker compose up -d                                                       
  3. Start the backend (after Story 1.3)                                     
  cd backend                                                                 
  ./mvnw spring-boot:run                                                     
  4. Start the frontend (after Story 1.2)                                    
  cd frontend                                                                
  npm install                                                                
  npm run dev                                                                
  5. Access the application                                                  
    - Frontend: http://localhost:5173                                        
    - Backend API: http://localhost:8080/api/v1                              
    - Adminer (DB UI): http://localhost:9090                                 
                                                                             
  Project Structure                                                          
                                                                             
  spotifou/                                                                  
  ├── README.md                                                              
  ├── compose.yaml              # Docker Compose for local dev               
  ├── .gitignore                                                             
  ├── .env.example              # Environment template                       
  ├── frontend/                 # React + Vite application                   
  └── backend/                  # Spring Boot application                    
                                                                             
  Documentation                                                              
                                                                             
  - ./_bmad-output/planning-artifacts/architecture.md                        
  - ./_bmad-output/planning-artifacts/prd.md                                 
  - ./_bmad-output/planning-artifacts/ux-design-specification.md             
  - ./_bmad-output/planning-artifacts/epics.md 