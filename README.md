# Invoice Processing System - Backend

Spring Boot REST API für die Verwaltung von Rechnungen mit JWT-basierter Authentifizierung und PostgreSQL-Datenbank.

## 🎯 Features

- ✅ REST API mit Spring Boot
- ✅ JWT-basierte Authentifizierung
- ✅ Benutzerregistrierung und -verwaltung
- ✅ Rechnungsmanagement (CRUD)
- ✅ BCrypt Passwort-Hashing
- ✅ Globale Exception Handling
- ✅ PostgreSQL Datenbank Integration
- ✅ CORS-Unterstützung mit Spring Security
- ✅ Umfangreiche Validierung
- ✅ Docker-ready für Production Deployment

## 🛠️ Tech Stack

- **Java** 21
- **Spring Boot** 3.5.9
- **Spring Data JPA** (Hibernate)
- **Spring Security** (CORS, BCrypt)
- **JWT (JJWT)** 0.11.5
- **PostgreSQL** 14+
- **Maven** (Build Tool)
- **Docker** (Containerization)

## 📦 Installation

### Voraussetzungen

- Java 21 oder höher
- Maven 3.8+
- PostgreSQL 14+
- Port 8080 verfügbar

### Setup

1. **Repository klonen**
   ```bash
   cd server
   ```

2. **PostgreSQL Datenbank erstellen**
   
   Lokal:
   ```sql
   CREATE DATABASE invoicedb;
   CREATE USER invoiceuser WITH PASSWORD 'secure_password';
   GRANT ALL PRIVILEGES ON DATABASE invoicedb TO invoiceuser;
   ```

3. **Datenbank-Konfiguration**
   
   `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/invoicedb
   spring.datasource.username=invoiceuser
   spring.datasource.password=secure_password
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

4. **Dependencies installieren und compilieren**
   ```bash
   mvn clean install
   ```

5. **Development Server starten**
   ```bash
   mvn spring-boot:run
   ```
   
   Oder mit IDE (IntelliJ IDEA, Eclipse):
   - Öffne `ServerApplication.java`
   - Klicke auf Run (Shift+F10)

## 🚀 Verfügbare Maven Befehle

| Befehl | Beschreibung |
|--------|-------------|
| `mvn clean install` | Dependencies installieren |
| `mvn spring-boot:run` | Development Server starten |
| `mvn clean package` | Production Build (JAR) |
| `mvn test` | Unit Tests ausführen |
| `mvn clean build` | Build with clean |

## 📁 Projektstruktur

```
server/
├── src/
│   ├── main/
│   │   ├── java/com/invoiceprocessing/server/
│   │   │   ├── ServerApplication.java          # Main Entry Point
│   │   │   ├── config/
│   │   │   │   ├── WebConfig.java              # CORS & Web Konfiguration
│   │   │   │   └── SecurityConfig.java          # Spring Security Config
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java         # Auth REST Endpoints
│   │   │   │   └── InvoiceController.java      # Invoice REST Endpoints
│   │   │   ├── service/
│   │   │   │   ├── UserService.java            # User Business Logic
│   │   │   │   ├── UserServiceImpl.java
│   │   │   │   ├── InvoiceService.java         # Invoice Business Logic
│   │   │   │   └── InvoiceServiceImpl.java
│   │   │   ├── model/
│   │   │   │   ├── User.java                   # User Entity
│   │   │   │   └── Invoice.java                # Invoice Entity
│   │   │   ├── dao/
│   │   │   │   ├── UserRepository.java         # User JPA Repository
│   │   │   │   └── InvoiceRepository.java      # Invoice JPA Repository
│   │   │   ├── exception/
│   │   │   │   ├── AuthenticationException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── dto/                            # Data Transfer Objects
│   │   └── resources/
│   │       ├── application.properties           # Dev Konfiguration
│   │       └── application-prod.properties      # Prod Konfiguration
│   └── test/
│       └── java/com/invoiceprocessing/server/
│           └── ServerApplicationTests.java
├── target/                                       # Build Output
├── pom.xml                                       # Maven Konfiguration
├── Dockerfile                                    # Docker Konfiguration
├── render.yaml                                   # Render Deployment
└── README.md
```

## 🔐 Authentifizierung

### Registrierung

**POST** `/api/auth/register`

```json
{
  "username": "max.mustermann",
  "password": "SecurePass123!"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "username": "max.mustermann",
  "message": "Benutzer erfolgreich registriert"
}
```

**Error Responses:**
- 400: INVALID_USERNAME, WEAK_PASSWORD
- 409: USERNAME_ALREADY_EXISTS

### Login

**POST** `/api/auth/login`

```json
{
  "username": "max.mustermann",
  "password": "SecurePass123!"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "max.mustermann",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Erfolgreich angemeldet"
}
    "id": 1,
    "username": "max.mustermann"
  }
}
```

**Error Response (401 Unauthorized):**
```json
{
  "status": "error",
  "error": "INVALID_CREDENTIALS",
  "message": "Benutzername oder Passwort falsch"
}
```

### Logout

**POST** `/api/auth/logout`

- Token wird auf Client-Seite gelöscht
- Server aktualisiert nichts

### Token Format

- **Type**: JWT (JSON Web Token)
- **Encoding**: HS256
- **Expiration**: 24 Stunden (86400000 ms)
- **Header**: `Authorization: Bearer <token>`

## 📡 REST API Endpoints

### Authentifizierung

| Methode | Endpoint | Beschreibung |
|---------|----------|-------------|
| POST | `/api/auth/register` | Benutzer registrieren |
| POST | `/api/auth/login` | Benutzer anmelden |
| POST | `/api/auth/logout` | Benutzer abmelden |

### Rechnungen

| Methode | Endpoint | Beschreibung | Auth |
|---------|----------|-------------|------|
| GET | `/api/invoices` | Alle Rechnungen abrufen | ✅ |
| POST | `/api/invoices` | Neue Rechnung erstellen | ✅ |
| GET | `/api/invoices/{id}` | Einzelne Rechnung abrufen | ✅ |
| PUT | `/api/invoices/{id}` | Rechnung aktualisieren | ✅ |
| DELETE | `/api/invoices/{id}` | Rechnung löschen | ✅ |

### Beispiele

**GET /api/invoices** (mit Token)
```bash
curl -H "Authorization: Bearer <token>" \
  http://localhost:8080/api/invoices
```

**POST /api/invoices**
```json
{
  "invoiceNumber": "INV-2024-001",
  "amount": 1500.00,
  "description": "Beratungsleistungen",
  "dueDate": "2024-02-15"
}
```

## 💾 Datenmodelle

### User Entity
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password; // BCrypt gehashed
}
```

### Invoice Entity
```java
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String invoiceNumber;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

## 🚨 Exception Handling

Alle Fehler werden standardisiert zurückgegeben:

```json
{
  "status": "error",
  "error": "ERROR_CODE",
  "message": "Deutsche Fehlermeldung"
}
```

### Fehler-Codes

| HTTP Status | Error Code | Beschreibung |
|------------|-----------|-------------|
| 400 | INVALID_USERNAME | Benutzername ungültig |
| 400 | WEAK_PASSWORD | Passwort zu schwach |
| 401 | INVALID_CREDENTIALS | Falsche Anmeldedaten |
| 409 | USERNAME_ALREADY_EXISTS | Benutzername existiert bereits |
| 500 | INTERNAL_SERVER_ERROR | Serverfehler |

## 🔗 Environment Variables (Production)

### Render PostgreSQL
```env
DATABASE_URL=postgresql://user:password@host:5432/database
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=your-super-secret-key-minimum-32-characters-long
CORS_ALLOWED_ORIGINS=https://your-netlify-app.netlify.app
```

### Local Development
```env
spring.datasource.url=jdbc:postgresql://localhost:5432/invoicedb
spring.datasource.username=invoiceuser
spring.datasource.password=secure_password
```

## 📦 Dependencies

```xml
<!-- Core Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- JPA/Hibernate -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Security (BCrypt) -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```
## 🚀 Deployment

### Docker Build
```bash
docker build -t invoice-backend .
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host:5432/invoicedb \
  -e DB_USERNAME=invoiceuser \
  -e DB_PASSWORD=secure_password \
  invoice-backend
```

### Deployment auf Render

1. **GitHub Repository vorbereiten**
   ```bash
   git add .
   git commit -m "Production ready"
   git push origin main
   ```

2. **PostgreSQL Datenbank erstellen in Render**
   - Dashboard → New → PostgreSQL
   - Name: `invoice-database`
   - Plan: Free
   - Region: Frankfurt
   - Warte bis Status "Available"

3. **Web Service erstellen**
   - Dashboard → New → Web Service
   - Repository: Dein GitHub Repo
   - Branch: main
   - Root Directory: `server`
   - Environment: Docker
   - Region: Frankfurt
   - Plan: Free

4. **Environment Variables konfigurieren**
   - `DATABASE_URL`: Wird automatisch von Render PostgreSQL gesetzt
   - Optional: `SPRING_PROFILES_ACTIVE=prod`

5. **Automatisches Deployment**
   - Render deployed automatisch bei jedem `git push`
   - Logs in Render Dashboard prüfen

### Mit render.yaml (Alternative)
Die `render.yaml` ist bereits konfiguriert:
```bash
git push origin main
# Render deployed automatisch
```

### Manuelle Schritte
1. GitHub Repository mit Render verbinden
2. Environment Variables setzen (DATABASE_URL wird automatisch gesetzt)
3. Render baut mit Docker
4. PostgreSQL Datenbank wird automatisch erstellt

## 📝 Best Practices

- ✅ Alle Passwörter mit BCrypt gehashed
- ✅ JWT Tokens mit HS256 signiert
- ✅ CORS auf spezifische Origins beschränkt
- ✅ Validation auf allen Inputs
- ✅ Globale Exception Handling
- ✅ Detaillierte Error Codes für Frontend

