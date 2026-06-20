# LAN Vault Messenger
An containerized, real-time messaging and media-sharing system built using **Java 21**, **Spring Boot 4**, and **PostgreSQL**. Designed to stream text payloads, documents, images, and videos seamlessly across multiple heterogeneous mobile and desktop devices operating over a shared local area network (LAN), Wi-Fi, or Mobile Hotspot.


## Architectural Topology
The platform leverages full-duplex WebSocket tunnels wrapped inside the STOMP protocol layout for real-time bidirectional message distribution, backed by transaction-safe relational database caching.
```bash
        [ Mobile/Desktop Browser ] (10.39.210.XX)
                      │
                      │ (STOMP over WebSocket Pipe)
                      ▼
        [ Docker Bridge Network Core Ring ]
                      │
        ┌─────────────┴─────────────┐
        ▼                           ▼
  [ Spring Boot Engine ] ◄───► [ PostgreSQL Storage Hub ]
  (Port 8080 Listener)         (Port 5432 - chat_db)
    │
    └──► 1. Intercepts Binary/String Frames
    └──► 2. Commits Record Layer Transactions
    └──► 3. Liquibase Managed Change Validation
    └──► 4. Internal Memory Broker Replication Fan-out
```

### Key Technical Pillars
* **High-Capacity WebSocket Buffers:** Configured to ingest full data packets up to **50MB**, allowing rich media (images and videos) to transfer natively over the socket plane.
* **Base64 End-to-End Serialization:** Binary attachment files are transformed on the fly via the browser's `FileReader` pipeline into text-safe strings, eliminating complex multi-part HTTP upload overhead.
* **Database Version Control:** Fully decoupled schema lifecycle tracking handled natively by **Liquibase** declarative change sets during bootstrap execution.


## Tech Stack & Prerequisites

* **Runtime Environment:** Java 21 (Eclipse Temurin JRE Base)
* **Application Framework:** Spring Boot 4.1.0 + Thymeleaf
* **Dependency Automation:** Gradle (Wrapper Configured)
* **Storage Engine:** PostgreSQL 15 (Alpine Minimal Core)
* **Migration Manager:** Liquibase Core Starter
* **Styling Paradigm:** Tailwind CSS Web Engine + FontAwesome v6
* **Container Layer:** Docker Engine + Docker Compose


## 🚀 Quick Start (Dockerized Deployment)

Navigate to your project root containing the docker-compose.yml file and execute a clean compiled build:
```bash
docker compose up --build
```


## Dev guide : How to generate liquibase auto table gen
```bash
./gradlew diffChangelog -PreferenceUrl=hibernate:spring:com.example.chat.model
```