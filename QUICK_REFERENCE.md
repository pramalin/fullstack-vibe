# Quick Reference Card

## 🚀 Start in 3 Steps

```bash
cd /home/padhu/projects/fullstack-vibe
./docker-manage.sh start
# Open http://localhost:5173
```

---

## 📍 Access Points

| Service | URL | Credentials |
|---------|-----|-------------|
| Frontend | http://localhost:5173 | N/A |
| Backend | http://localhost:8080/api | N/A |
| Database | localhost:5432 | postgres / postgres |

---

## 📝 Management Commands

```bash
# Start everything
./docker-manage.sh start

# Stop everything
./docker-manage.sh stop

# Restart services
./docker-manage.sh restart

# Check status
./docker-manage.sh status

# View logs (all services)
./docker-manage.sh logs

# View specific service logs
./docker-manage.sh logs backend
./docker-manage.sh logs frontend
./docker-manage.sh logs postgres

# Access database CLI
./docker-manage.sh db

# Access backend container shell
./docker-manage.sh shell-backend

# Access frontend container shell
./docker-manage.sh shell-frontend

# Rebuild Docker images
./docker-manage.sh rebuild

# Remove everything (including data)
./docker-manage.sh cleanup

# Show help
./docker-manage.sh help
```

---

## 🌐 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Contacts
```bash
# Get all contacts (page 0, 10 per page)
GET /contacts?page=0&size=10

# Search contacts
GET /contacts/search?searchTerm=john&page=0&size=10

# Get single contact
GET /contacts/{id}

# Create contact
POST /contacts
Content-Type: application/json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "company": "Tech Corp",
  "jobTitle": "Developer"
}

# Update contact
PUT /contacts/{id}
Content-Type: application/json
{ ...same as create }

# Delete contact
DELETE /contacts/{id}
```

---

## 🗄️ Database Commands

### Connect to Database
```bash
# Via Docker
./docker-manage.sh db

# Or manually
docker-compose exec postgres psql -U postgres -d contact_app_db
```

### Common SQL
```sql
-- View all contacts
SELECT * FROM contacts;

-- View specific contact
SELECT * FROM contacts WHERE id = 1;

-- Count contacts
SELECT COUNT(*) FROM contacts;

-- Search by email
SELECT * FROM contacts WHERE email LIKE '%john%';

-- Search by name
SELECT * FROM contacts WHERE first_name LIKE '%John%' 
  OR last_name LIKE '%Doe%';

-- Update contact
UPDATE contacts SET company = 'New Corp' WHERE id = 1;

-- Delete contact
DELETE FROM contacts WHERE id = 1;

-- Exit
\q
```

---

## 🎨 Frontend Features

| Feature | How to Use |
|---------|-----------|
| View Contacts | Table displays all contacts with pagination |
| Search | Type in search box, updates in real-time |
| Create | Click "New Contact" button, fill form, save |
| Edit | Click edit icon in table row |
| Delete | Click trash icon, confirm deletion |
| View Details | Click eye icon to see full details |

---

## 📁 Key Files

```
README.md                   ← Start here for full documentation
SETUP.md                   ← Setup and troubleshooting
docker-compose.yml         ← Service configuration
docker-manage.sh           ← Management commands
init-db/01-init.sql        ← Database initialization

Frontend:
  contact-app-frontend/src/App.tsx           ← Main app
  contact-app-frontend/src/components/       ← UI components
  contact-app-frontend/src/api/contactsApi.ts ← API client

Backend:
  contact-app-backend/src/main/java/        ← Java code
  contact-app-backend/pom.xml                ← Dependencies
```

---

## 🐛 Troubleshooting

### Services won't start
```bash
# Check if ports are in use
sudo lsof -i :5173
sudo lsof -i :8080
sudo lsof -i :5432

# Restart everything
docker-compose down -v
docker-compose up -d
```

### Can't connect to API
```bash
# Check backend is running
./docker-manage.sh status

# View backend logs
./docker-manage.sh logs backend

# Test API directly
curl http://localhost:8080/api/contacts
```

### No sample data
```bash
# Reinitialize database
docker-compose down -v
docker-compose up -d
```

### Port already in use
```bash
# Kill process using port
sudo kill -9 $(lsof -t -i :PORT)
```

---

## 🛠️ Development

### Frontend Development
```bash
cd contact-app-frontend

# Install dependencies
npm install

# Start dev server (with hot reload)
npm run dev

# Build production
npm run build

# Preview build
npm run preview
```

### Backend Development
```bash
cd contact-app-backend

# Build
mvn clean install

# Run
mvn spring-boot:run

# Test
mvn test
```

---

## 📊 Sample Data

10 pre-loaded contacts:
1. John Doe - Senior Software Engineer
2. Jane Smith - UX Designer
3. Michael Johnson - Financial Analyst
4. Sarah Williams - Marketing Manager
5. Robert Brown - Project Manager
6. Emily Davis - DevOps Engineer
7. David Martinez - Data Scientist
8. Lisa Anderson - Founder & CEO
9. James Taylor - Senior Attorney
10. Jessica Wilson - Operations Director

---

## 📦 Tech Stack

| Layer | Technologies |
|-------|--------------|
| Frontend | React 18, TypeScript, Vite, Tailwind CSS, shadcn |
| Backend | Spring Boot 3.2, Java 21 |
| Database | PostgreSQL 15 |
| DevOps | Docker, Docker Compose |

---

## 📱 Responsive Design

Frontend is fully responsive:
- ✅ Desktop (1024px+)
- ✅ Tablet (768px - 1024px)
- ✅ Mobile (320px - 768px)

---

## 🔐 Security Notes

- Database credentials for development only
- Use secrets manager for production
- CORS configured for localhost testing
- Input validation on frontend and backend
- Email uniqueness enforced

---

## 🚨 Important Paths

```
Project Root:
/home/padhu/projects/fullstack-vibe/

Frontend:
/home/padhu/projects/fullstack-vibe/contact-app-frontend/

Backend:
/home/padhu/projects/fullstack-vibe/contact-app-backend/

Database Init:
/home/padhu/projects/fullstack-vibe/init-db/
```

---

## 💡 Pro Tips

1. **Hot Reload**: Frontend dev server auto-reloads on file changes
2. **Docker Logs**: Use `docker-compose logs -f` for real-time logs
3. **Database Access**: Use `./docker-manage.sh db` for quick DB access
4. **Health Checks**: Services have health checks, wait for "healthy" status
5. **Volumes**: Database data persists even after docker-compose down
6. **Rebuild**: Use `./docker-manage.sh rebuild` to force rebuild

---

## 📞 Getting Help

1. Check `README.md` for full documentation
2. Check `SETUP.md` for setup help
3. Check `FRONTEND_SUMMARY.md` for frontend details
4. View logs: `./docker-manage.sh logs`
5. Check service status: `./docker-manage.sh status`

---

## ✅ Checklist

- [ ] Started Docker services with `./docker-manage.sh start`
- [ ] All services show as "Up" or "Up (healthy)"
- [ ] Frontend loads at http://localhost:5173
- [ ] Can view sample contacts in table
- [ ] Can search contacts by name
- [ ] Can create new contact
- [ ] Can edit existing contact
- [ ] Can delete contact
- [ ] API responds at http://localhost:8080/api/contacts
- [ ] Database accessible via `./docker-manage.sh db`

---

**Quick Start**: `./docker-manage.sh start` → Open http://localhost:5173 → Done! 🎉
