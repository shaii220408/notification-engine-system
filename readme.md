# 🔔 Notification Engine

A production-level, event-driven notification system built with Spring Boot, Apache Kafka, and PostgreSQL.

## 🏗️ Architecture
## ⚙️ Tech Stack

- **Java 17** + **Spring Boot 3.2**
- **Apache Kafka** - async event processing
- **PostgreSQL** - persistent storage
- **Docker** - containerization
- **JavaMailSender** - email delivery
- **Lombok** - reduced boilerplate

## 🚀 Features

- ✅ REST API for sending notifications
- ✅ Kafka-based async processing
- ✅ Email, SMS, Push channel support
- ✅ Retry mechanism (max 3 attempts)
- ✅ Delivery status tracking
- ✅ Notification logs
- ✅ Template-based messaging
- ✅ Docker Compose setup

## 📦 How to Run

### Prerequisites
- Java 17
- Docker Desktop

### Steps

1. Clone the repo
2. Start infrastructure
3. Run the app

```bash
git clone https://github.com/shaii220408/notification-engine-system.git
cd notification-engine-system
docker-compose up -d
./mvnw spring-boot:run
```

## 📬 API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/v1/notifications/send | Send notification |
| GET | /api/v1/notifications/all | Get all notifications |
| GET | /api/v1/notifications/user/{userId} | Get by user |

## 📨 Sample Request

```json
{
  "userId": "user123",
  "type": "ORDER_CONFIRMED",
  "channel": "EMAIL",
  "recipient": "user@gmail.com",
  "message": "Your order has been confirmed!"
}
```

## 🔁 Notification Flow