# Contact Manager - Full Stack Application

A modern full-stack contact management application built with Spring Boot (Java 21) backend, React frontend with Tailwind CSS and shadcn components, and PostgreSQL database.

## Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                    Docker Compose Network               │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  ┌──────────────────┐  ┌──────────────────┐             │
│  │   React App      │  │ Spring Boot API  │             │
│  │  (Port 5173)     │  │  (Port 8080)     │             │
│  │                  ├──┤                  │             │
│  │  - Node.js       │  │ - Java 21        │             │
│  │  - TypeScript    │  │ - Spring Boot 3.2│             │
│  │  - Vite          │  │ - RESTful API    │             │
│  │  - Tailwind CSS  │  │                  │             │
│  │  - shadcn        │  │                  │             │
│  └──────────────────┘  └────────┬─────────┘             │
│                                  │                       │
│                         ┌────────▼──────────┐            │
│                         │  PostgreSQL DB    │            │
│                         │  (Port 5432)      │            │
│                         │                   │            │
│                         │ - contact_app_db  │            │
│                         │ - Sample data     │            │
│                         │ - Persistent vol. │            │
│                         └───────────────────┘            │
│                                                           │
└─────────────────────────────────────────────────────────┘
```

## Project Structure

```
fullstack-vibe/
├── contact-app-backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/contactapp/
│   │   │   │   ├── ContactAppBackendApplication.java
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── model/
│   │   │   │   ├── dto/
│   │   │   │   └── exception/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── pom.xml
│   └── Dockerfile
├── contact-app-frontend/
│   ├── src/
│   │   ├── api/
│   │   │   └── contactsApi.ts
│   │   ├── components/
│   │   │   ├── ui/
│   │   │   │   ├── Button.tsx
│   │   │   │   ├── Input.tsx
│   │   │   │   └── Dialog.tsx
│   │   │   └── ContactComponents.tsx
│   │   ├── App.tsx
│   │   ├── main.tsx
│   │   └── index.css
│   ├── package.json
│   ├── vite.config.ts
│   ├── tailwind.config.js
│   ├── tsconfig.json
│   ├── Dockerfile
│   └── .env.example
├── init-db/
│   └── 01-init.sql         # Database initialization with sample data
├── docker-compose.yml      # Orchestrates all services
└── README.md
```

## Prerequisites

- **Docker** (version 20.10+)
- **Docker Compose** (version 1.29+)
- Or locally: Node.js 20+, Java 21, Maven 3.9+, PostgreSQL 15+

## Quick Start with Docker

### 1. Start All Services

```bash
cd /home/padhu/projects/fullstack-vibe

# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Check service status
docker-compose ps
```

### 2. Wait for Services to Be Ready

The services have health checks configured. Wait for all services to be healthy:

```bash
docker-compose ps
```

Expected output:
```
NAME                       STATUS
contact-app-db            Up (healthy)
contact-app-backend       Up (healthy)
contact-app-frontend      Up
```

### 3. Access the Application

- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080/api
- **Database**: localhost:5432 (postgres / postgres)

### 4. Sample Data

The database is automatically initialized with 10 sample contacts. Once the application loads, you can:

- View all contacts in the table
- Search for contacts by name or email
- Create new contacts
- Edit existing contacts
- Delete contacts
- View full contact details

## Features

### Backend (Spring Boot)

- ✅ RESTful API with pagination support
- ✅ CRUD operations for contacts
- ✅ Full-text search functionality
- ✅ File upload support for contact photos
- ✅ Global exception handling
- ✅ CORS configuration
- ✅ Java 21 with Spring Boot 3.2
- ✅ PostgreSQL with JPA/Hibernate
- ✅ Data validation

### Frontend (React + TypeScript)

- ✅ Modern React 18 with TypeScript
- ✅ Vite for fast development and build
- ✅ Tailwind CSS for styling
- ✅ shadcn components for UI
- ✅ Axios for API calls
- ✅ Dialog modals for forms
- ✅ Search functionality
- ✅ Pagination support
- ✅ Form validation
- ✅ Responsive design

## Development

### Backend Development

```bash
cd contact-app-backend

# Build
mvn clean package

# Run locally
mvn spring-boot:run

# Run tests
mvn test
```

### Frontend Development

```bash
cd contact-app-frontend

# Install dependencies
npm install

# Development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

## API Endpoints

### Contacts

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/contacts` | Get all contacts (paginated) |
| POST | `/contacts` | Create a new contact |
| GET | `/contacts/{id}` | Get contact by ID |
| PUT | `/contacts/{id}` | Update a contact |
| DELETE | `/contacts/{id}` | Delete a contact |
| GET | `/contacts/search` | Search contacts |

### Query Parameters

- `page` - Page number (default: 0)
- `size` - Page size (default: 10)
- `search` or `searchTerm` - Search query

### Example Requests

**Get all contacts (page 0, 10 per page):**
```bash
curl http://localhost:8080/api/contacts?page=0&size=10
```

**Search contacts:**
```bash
curl http://localhost:8080/api/contacts/search?searchTerm=john&page=0&size=10
```

**Create contact:**
```bash
curl -X POST http://localhost:8080/api/contacts \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane@example.com",
    "phone": "+1234567890"
  }'
```

## Database Schema

The application uses PostgreSQL with the following main table:

```sql
CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    company VARCHAR(255),
    job_title VARCHAR(255),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100),
    notes TEXT,
    photo_path VARCHAR(500),
    photo_filename VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## Environment Variables

### Backend (application.properties)

```properties
spring.datasource.url=jdbc:postgresql://postgres:5432/contact_app_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=validate
server.port=8080
```

### Frontend (.env)

```
VITE_API_BASE_URL=http://localhost:8080/api
```

## Docker Commands

```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs for specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres

# Remove volumes (delete database)
docker-compose down -v

# Rebuild images
docker-compose build --no-cache

# Execute command in container
docker-compose exec backend sh
docker-compose exec frontend sh
docker-compose exec postgres psql -U postgres -d contact_app_db
```

## Database Access

### Using Docker

```bash
docker-compose exec postgres psql -U postgres -d contact_app_db

# List tables
\dt

# View contacts
SELECT * FROM contacts;

# Exit
\q
```

### Using psql locally

```bash
psql -h localhost -p 5432 -U postgres -d contact_app_db
```

## Troubleshooting

### Services not starting

1. Check if ports 5173, 8080, 5432 are available
2. Check Docker daemon is running
3. View logs: `docker-compose logs`

### Frontend can't connect to backend

1. Ensure backend is healthy: `docker-compose ps`
2. Check network: `docker network ls`
3. Verify VITE_API_BASE_URL in frontend

### Database connection errors

1. Wait for PostgreSQL to be healthy (check health checks)
2. Verify environment variables in docker-compose.yml
3. Check PostgreSQL logs: `docker-compose logs postgres`

### Sample data not loaded

1. Check if init-db folder exists and has 01-init.sql
2. Verify database volume is fresh: `docker-compose down -v`
3. Check init logs: `docker-compose logs postgres` | grep init

## Performance Optimization

- Database indexes on `email` and `name` fields
- Pagination to limit data transfer
- React component memoization
- Tailwind CSS for optimized styling
- Alpine Linux for minimal Docker images

## Security Considerations

- CORS configured to allow specific origins
- Email uniqueness constraint
- Form validation on frontend and backend
- No sensitive data in logs
- Environment variables for secrets

## Future Enhancements

- [ ] User authentication and authorization
- [ ] Contact photo upload and display
- [ ] Export contacts to CSV/PDF
- [ ] Bulk operations
- [ ] Contact groups/categories
- [ ] Integration with email services
- [ ] Mobile app
- [ ] Advanced search filters
- [ ] Contact history/audit logs
- [ ] Real-time updates with WebSockets

## License

This project is open source and available under the MIT License.

## Support

For issues, questions, or suggestions, please create an issue in the repository.

---

**Created**: January 2026  
**Java Version**: 21 (LTS)  
**Node Version**: 20+  
**PostgreSQL**: 15
