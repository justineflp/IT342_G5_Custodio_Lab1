# IT342 Auth – Android (Kotlin)

Android app for the same Spring Boot backend. UI and copy match the web app: Register, Login, Dashboard, Logout.

## Requirements

- **Android Studio Iguana (2023.2)** – project uses Gradle 8.2, AGP 8.2.2, Kotlin 1.9.22
- JDK 17
- Backend running at `http://localhost:8080` (or update `ApiConstants.BASE_URL`)

## Backend URL

- **Emulator:** default is `http://10.0.2.2:8080/` (emulator’s alias for host `localhost`).
- **Physical device:** set `ApiConstants.BASE_URL` in `app/src/main/java/com/it342/custodio/auth/api/ApiConstants.kt` to `http://<your-PC-IP>:8080/` (e.g. `http://192.168.1.5:8080/`).

## Open and run

1. Start the Spring Boot backend (from repo root: `cd backend && ./mvnw spring-boot:run` or use your IDE).
2. Open the `mobile` folder in Android Studio.
3. Let Gradle sync (Android Studio will download the wrapper if needed).
4. Run on an emulator or device (min SDK 24).

## Features (aligned with web)

- **Register** – Full name, Email, Password (min 6 chars); card layout; outline button; “Already have an account? Login”.
- **Login** – Email, Password; “Don’t have an account? Register”; loading: “Signing in…” / “Creating account…”.
- **Dashboard** – “Dashboard” title; profile block (ID, Full name, Email); note: “You are logged in. Use the Logout button in the header to sign out.”; “Loading profile…” while fetching.
- **Logout** – calls `/api/auth/logout` and clears token. Same colors as web: white background, #333 text, #ddd borders, outline buttons.

## Project structure

- `app/src/main/java/com/it342/custodio/auth/` – activities and session.
- `app/src/main/java/.../api/` – Retrofit API, DTOs, error parsing.
- `SessionManager` – stores JWT in `SharedPreferences`.
