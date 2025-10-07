# 🕒 Spring Boot Cron Job Application

A lightweight Spring Boot service that automatically sends HTTP requests at specified intervals. Specially designed for **free hosting services** (Render, Heroku, Railway, etc.) - these platforms go to sleep when inactive, and this application keeps your services awake continuously.

## ✨ Features

- ⏰ **Automatic requests every 13 minutes**
- 🎯 Easily configure target URL via environment variable
- 🏥 Health check endpoint (`/health`)
- 📊 Detailed logging and error handling
- 🐳 Docker support
- 🚀 Lightweight and fast startup

## 🛠️ Technologies

- **Java 21**
- **Spring Boot 3.5.6**
- **Maven**
- **Lombok**
- **Docker**

## 📋 Requirements

- Java 21 or higher
- Maven 3.6+ (optional, mvnw wrapper included)
- Docker (if you want to run with Docker)

## 🚀 Installation and Running

### With Maven

```bash
# Clone the repository
git clone <repo-url>
cd cronjob

# Run the application
./mvnw spring-boot:run

# Or build JAR and run
./mvnw clean package
java -jar target/cronjob-0.0.1-SNAPSHOT.jar
```

### With Docker

```bash
# Build the Docker image
docker build -t cronjob-app .

# Run the container
docker run -p 8080:8080 -e CRONJOB_TARGET_URL=https://your-service.com cronjob-app
```

## ⚙️ Configuration

### Changing Target URL

You can change the target URL using an environment variable:

```bash
# Linux/Mac
export CRONJOB_TARGET_URL=https://your-service.com
./mvnw spring-boot:run

# Windows (PowerShell)
$env:CRONJOB_TARGET_URL="https://your-service.com"
./mvnw spring-boot:run

# With Docker
docker run -p 8080:8080 -e CRONJOB_TARGET_URL=https://your-service.com cronjob-app
```

### application.properties

You can also modify settings in `src/main/resources/application.properties`:

```properties
# Target URL
cronjob.target.url=https://your-service.com

# Port (default: 8080)
server.port=8080
```

## 📡 Endpoints

### Health Check
```http
GET /health
```

**Response:**
```json
{
  "status": "UP",
  "timestamp": "07-10-2025 15:30:45",
  "message": "Cron Job application is running"
}
```

### Home
```http
GET /
```

**Response:**
```json
{
  "status": "UP",
  "timestamp": "07-10-2025 15:30:45",
  "message": "Spring Boot Cron Job Application",
  "description": "Every 13 minutes the target URL is requested"
}
```

## ⏱️ Scheduling Details

- **Cron Expression:** `0 */13 * * * *`
- **Frequency:** Every 13 minutes
- **First Request:** 30 seconds after application starts
- **Subsequent Requests:** Automatically every 13 minutes

## 📝 Logging

The application logs detailed information for each request:

```
======================================================
Cron Job started: 07-10-2025 15:30:45
Target URL: https://your-service.com
✅ Request successful! Response: ...
======================================================
```

## 🎯 Use Cases

1. **Keeping Render/Heroku Services Awake**
   - Free plans sleep after 15 minutes of inactivity
   - This app sends requests every 13 minutes to keep services active

2. **Backend API Warming**
   - Regularly requests your API to keep cache warm
   - Prevents slowness on initial requests

3. **Simple Monitoring**
   - Checks if your service is running
   - Track status through logs

## 🔒 Security Note

This application does not use any database and has minimal dependencies. It only sends GET requests to your specified URL.

