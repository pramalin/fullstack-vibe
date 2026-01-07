# Setup and Deployment Guide

## Quick Start (Docker Compose)

### Prerequisites
- Docker (20.10+)
- Docker Compose (1.29+)

### Step 1: Navigate to Project Directory
```bash
cd /home/padhu/projects/fullstack-vibe
```

### Step 2: Start All Services
```bash
# Using docker-compose directly
docker-compose up -d

# OR using the convenience script
./docker-manage.sh start
```

### Step 3: Wait for Services to Initialize
```bash
# Check service status
docker-compose ps

# Or use the script
./docker-manage.sh status
```

All services should show as "Up" or "Up (healthy)".

### Step 4: Access the Application
- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080/api
- **Database**: localhost:5432 (credentials: postgres / postgres)

---

## What Gets Initialized

### Database
The PostgreSQL database automatically initializes with:
- Contact table with proper schema
- 10 sample contacts (pre-loaded)
- Indexes for optimized queries
- All required fields and constraints

Sample contacts include:
- John Doe (Senior Software Engineer)
- Jane Smith (UX Designer)
- Michael Johnson (Financial Analyst)
- Sarah Williams (Marketing Manager)
- Robert Brown (Project Manager)
- Emily Davis (DevOps Engineer)
- David Martinez (Data Scientist)
- Lisa Anderson (Founder & CEO)
- James Taylor (Senior Attorney)
- Jessica Wilson (Operations Director)

### Backend
- Spring Boot application running on port 8080
- Connected to PostgreSQL database
- RESTful API ready to serve requests
- CORS configured for frontend communication
- Health checks enabled

### Frontend
- React application running on port 5173
- Built with Vite for fast loading
- Connected to backend API
- Ready to view, create, edit, and delete contacts

---

## Management Commands

### Using docker-compose directly

```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f [service-name]
# service-name: postgres, backend, frontend

# Check status
docker-compose ps

# Remove everything (including data)
docker-compose down -v

# Rebuild images
docker-compose build --no-cache
```

### Using the convenience script

```bash
./docker-manage.sh start              # Start all services
./docker-manage.sh stop               # Stop all services
./docker-manage.sh restart            # Restart all services
./docker-manage.sh status             # Show service status
./docker-manage.sh logs [service]     # View logs
./docker-manage.sh rebuild            # Rebuild images
./docker-manage.sh cleanup            # Remove everything
./docker-manage.sh db                 # Access database CLI
./docker-manage.sh shell-backend      # Access backend shell
./docker-manage.sh shell-frontend     # Access frontend shell
```

---

## Accessing Services

### Frontend
Open browser: http://localhost:5173

Features:
- View all contacts in a paginated table
- Search contacts by name or email
- Create new contacts via modal dialog
- Edit existing contacts
- Delete contacts (with confirmation)
- View full contact details in a modal

### Backend API
Base URL: http://localhost:8080/api

**List contacts:**
```bash
curl http://localhost:8080/api/contacts?page=0&size=10
```

**Search contacts:**
```bash
curl http://localhost:8080/api/contacts/search?searchTerm=john&page=0&size=10
```

**Get single contact:**
```bash
curl http://localhost:8080/api/contacts/1
```

**Create contact:**
```bash
curl -X POST http://localhost:8080/api/contacts \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "email": "test@example.com",
    "phone": "+1234567890"
  }'
```

**Update contact:**
```bash
curl -X PUT http://localhost:8080/api/contacts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Updated",
    "lastName": "Name",
    "email": "updated@example.com"
  }'
```

**Delete contact:**
```bash
curl -X DELETE http://localhost:8080/api/contacts/1
```

### Database
```bash
# Using Docker
docker-compose exec postgres psql -U postgres -d contact_app_db

# Common SQL commands
\dt                                    # List tables
SELECT * FROM contacts;               # View all contacts
SELECT * FROM contacts WHERE id = 1;  # View specific contact
\q                                     # Exit
```

---

## Troubleshooting

### Services won't start

**Issue:** Docker error or port conflict

**Solution:**
1. Make sure ports 5173, 8080, 5432 are available:
   ```bash
   sudo lsof -i :5173
   sudo lsof -i :8080
   sudo lsof -i :5432
   ```
2. Kill conflicting processes if needed
3. Ensure Docker daemon is running: `docker ps`
4. Try: `docker-compose down -v && docker-compose up -d`

### Frontend can't connect to backend

**Issue:** API calls return 403 or 404

**Solution:**
1. Check backend is running: `docker-compose ps`
2. Check backend logs: `docker-compose logs backend`
3. Verify network: `docker network ls`
4. Check API URL in frontend: should be `http://localhost:8080/api`

### Database not initialized

**Issue:** No sample data or tables missing

**Solution:**
1. Check if `init-db/01-init.sql` exists
2. Verify database logs: `docker-compose logs postgres`
3. Clear and restart:
   ```bash
   docker-compose down -v
   docker-compose up -d
   ```

### Port already in use

**Issue:** "Address already in use" error

**Solution:**
1. Find process using port:
   ```bash
   sudo lsof -i :PORT_NUMBER
   ```
2. Kill process:
   ```bash
   sudo kill -9 PID
   ```
3. Or modify docker-compose.yml port mapping

### Permission denied on script

**Issue:** `./docker-manage.sh: Permission denied`

**Solution:**
```bash
chmod +x /home/padhu/projects/fullstack-vibe/docker-manage.sh
```

---

## Development Mode

For local development without Docker:

### Backend
```bash
cd contact-app-backend

# Install dependencies
mvn clean install

# Run application
mvn spring-boot:run

# Run tests
mvn test
```

Requires: Java 21, Maven 3.9+, PostgreSQL 15+

### Frontend
```bash
cd contact-app-frontend

# Install dependencies
npm install

# Development server (hot reload)
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

Requires: Node.js 20+

---

## Performance Tips

1. **Database Queries:** Indexes on `email` and `name` fields speed up searches
2. **Pagination:** Default 10 items per page reduces data transfer
3. **Caching:** Browser caches static assets (CSS, JS)
4. **Lazy Loading:** Components load on demand

---

## Security Notes

- Database credentials in docker-compose for development only
- Use secrets manager for production
- CORS configured for localhost testing
- Email uniqueness enforced in database
- Input validation on frontend and backend

---

## Common Issues & Solutions

| Issue | Cause | Solution |
|-------|-------|----------|
| `docker: not found` | Docker not installed | Install Docker Desktop |
| `ConnectionRefusedError` | Backend not running | Check `docker-compose ps` |
| `EADDRINUSE: address already in use` | Port conflict | Kill process or change port |
| `Error: Cannot find module 'react'` | Node modules not installed | Run `npm install` |
| `java.sql.SQLException: Cannot create PoolableConnectionFactory` | DB connection failed | Verify DB is running and healthy |
| No sample data | Init script not executed | Restart with `docker-compose down -v && up -d` |

---

## Next Steps

1. ✅ Start services: `./docker-manage.sh start`
2. ✅ Open http://localhost:5173
3. ✅ View sample contacts
4. ✅ Create a new contact
5. ✅ Edit and delete contacts
6. ✅ Search by name or email
7. ✅ Check backend health at http://localhost:8080/api/contacts

---

## Documentation Links

- [Main README](./README.md) - Full project documentation
- [Backend README](./contact-app-backend/README.md) - Backend specifics
- [Frontend README](./contact-app-frontend/README.md) - Frontend specifics
- [API Documentation](#) - API endpoints and usage

---

For more information, see the main [README.md](./README.md)
