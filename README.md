# IT342 – User Registration and Authentication System (Lab 1)

**Repository:** IT342_G6_Custodio_Lab1

## Project Structure

```
IT342_G6_<Lastname>_Lab1/
├── web/          # React web application
├── backend/      # Spring Boot REST API
├── mobile/       # Mobile app (empty – Session 2)
├── docs/         # FRS PDF and diagrams
├── README.md
└── TASK_CHECKLIST.md
```

## Session 1 Scope

### Backend (Spring Boot)
- `POST /api/auth/register` – User registration
- `POST /api/auth/login` – User login
- `GET /api/user/me` – Protected current user profile
- MySQL database
- BCrypt password encryption

### Web (React)
- Register page
- Login page
- Dashboard/Profile page (protected)
- Logout

## Quick Start

### Backend
```bash
cd backend
# Configure MySQL in application.properties (url, username, password)
# Windows: mvnw.cmd spring-boot:run   |   Linux/Mac: ./mvnw spring-boot:run
mvn spring-boot:run
```

### Web
```bash
cd web
npm install
npm start
```

Default: Backend runs on `http://localhost:8080`, Web on `http://localhost:3000`.

## Documentation

See `/docs` for FRS (ERD, UML, Web UI screenshots).
