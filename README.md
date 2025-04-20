# Movie Ticket Booking Backend System 

Milo is a **scalable, distributed, and secure movie ticket booking backend** designed using **Spring Boot**, with a microservices-inspired structure for key infrastructure components like caching, messaging, and load balancing.

It handles everything from user authentication to reservation management, payment linking, and seat allocation with strong guarantees on **consistency, performance, and maintainability**.

![Untitled design (1)](https://github.com/user-attachments/assets/94e0d5c7-e50b-44ea-b18c-d0d328456f41)

---

## ⚙️ Tech Stack

- **Spring Boot**: Core web framework with REST API architecture  
- **PostgreSQL**: Relational database for consistent storage of all entities  
- **JWT (JSON Web Tokens)**: Authentication and role-based access control  
- **Redis**: High-performance in-memory caching for read-heavy operations  
- **RabbitMQ**: Asynchronous messaging layer for offloading non-critical operations  
- **Docker**: Containerization of all services for isolated, portable deployment  
- **Go (Golang)**: Custom reverse proxy/load balancer handling traffic across instances  
- **Docker Compose**: Multi-container orchestration for local and deployment setups

---

## 🔐 Authentication & Authorization

Authentication is implemented using **JWT tokens**, issued upon successful login. Role-based access is enforced throughout the system:

- **Admin Role**:  
  - Manage movies, theaters, screens, showtimes  
  - Access administrative endpoints  

- **User Role**:  
  - View movies, book tickets, check reservation status  
  - Submit reviews and make payments  

Tokens are stateless, and security is enforced through `@PreAuthorize` annotations and custom filters that validate JWTs on every secured route.

---

## 🧱 Core Modules

### 1. **User & Authentication Module**
- Handles user registration, login, and JWT issuance
- Distinguishes between admins and normal users using a boolean `is_admin` flag
- Stores hashed passwords securely

---

### 2. **Movie & Theater Management**
- **Admins** can:
  - Create and manage movie entries
  - Add theaters, screens, and configure seating arrangements
  - Set showtimes, assign movies to screens and time slots

- **Entities involved**:
  - `Movie`, `Theater`, `Screen`, `Seat`, `Showtime`

---

### 3. **Reservation & Booking Logic**
- Users can:
  - View available showtimes
  - Select seats
  - Make a reservation, which links user, showtime, and selected seats

- Seat booking is atomic and safe from race conditions using synchronized logic and database constraints

- **Entities involved**:
  - `Reservation`, `ReservedSeat`, `Payment`

---

### 4.  **Atomic Seat Locking with Lua & Redis**

To ensure **consistency and prevent race conditions** during seat booking in high-concurrency scenarios, the system uses **Lua scripts in Redis** to atomically manage:

- **Seat availability checks**
- **Seat locking for reservations**
- **Seat unlocking on timeout or failure**

Redis executes Lua scripts **atomically**, meaning no other operations can interleave during execution. This guarantees:
- No **double-booking** of the same seat
- No inconsistent intermediate states
- Ultra-fast performance under concurrent access

#### Workflow:
1. When a user selects seats, a Lua script checks if the seats are available and locks them temporarily.
2. If the payment is not completed within a specific window, another Lua script unlocks the seats for others to book.
3. Once the payment is confirmed via the RabbitMQ consumer, the reservation is finalized and the lock is cleared.

This mechanism acts like a **lightweight distributed lock**, enabling **safe parallel booking** without database contention or the need for heavyweight locking strategies.

---


### 5. **Caching with Redis**
- Frequently queried data like:
  - Movie lists
  - Showtimes per movie
  - Seat layout per screen

...are cached using Redis for **low-latency access**.

- Cache is refreshed on updates using Spring's `@CacheEvict` and `@CachePut`

- This greatly reduces PostgreSQL query load and improves responsiveness under high traffic

---

### 6. **RabbitMQ for Asynchronous Payment Processing**
- **RabbitMQ** is integrated to **handle payment processing asynchronously**, ensuring that the main reservation flow is fast and non-blocking.
  
- When a user confirms a booking:
  - The reservation is saved instantly.
  - A **payment task is pushed to a RabbitMQ queue** for background processing.
  - A listener consumes the task and handles payment-related logic like:
    - Simulating payment validation
    - Storing payment confirmation
    - Linking payment records to reservations
  
- This decouples the **payment system** from the main app logic, allowing:
  - **Faster response times** for users
  - **Scalability** of payment workers
  - Future integration with **real payment gateways** without touching core booking logic

- We can easily extend this queue-based system to support:
  - Retries on payment failure
  - Dead-letter queues for failed transactions
  - Email/SMS confirmation hooks

---

### 7. **Payments & Reviews**
- Each reservation is linked to a `Payment` record
- Users can later leave a `Review` for the movie they watched
- Payments are simulated/logged and can be later integrated with real gateways

---

## ⏲️ Scheduled Cleanup of Expired Seat Locks

To maintain **data consistency** and avoid stale seat locks blocking availability, the system includes a **scheduled cleanup task** that runs periodically as a **CRON job** within the Spring Boot application.

#### Why It’s Needed:
- Seats temporarily locked during the booking process (via Redis + Lua) may remain held if:
  - The user abandons the payment flow
  - There’s a failure in communication
- These stale locks can prevent other users from booking valid seats

#### How It Works:
- A Spring `@Scheduled` job runs at fixed intervals (e.g., every 5 minutes)
- It scans Redis for **expired or timed-out seat locks**
- Removes any that exceed a safe timeout threshold
- Frees up the seats for other users to book

This cleanup mechanism ensures:
- **Fresh seat availability**
- No false positives on “unavailable” seats
- A seamless and consistent booking experience for users

---

## 🗃 Data Model Overview

![Untitled](https://github.com/user-attachments/assets/d0a64696-d0af-4f32-839a-ae4ed9d27b53)

The schema is normalized and enforces **unique constraints**, **foreign key relationships**, and **cascading deletes** wherever appropriate to maintain data integrity.

---

##  🛠 Deployment Architecture

To ensure performance, scalability, and fault tolerance, the backend is **deployed as three independent Spring Boot instances**, each stateless and running inside a Docker container.

A custom **Go-based Load Balancer** is used to distribute incoming HTTP traffic across these instances:

- Built using Go’s `net/http` and `httputil` reverse proxy
- Implements **Round Robin** or **Least Connections** strategy
- Handles failure gracefully and retries failed nodes

---

##  🐳 Containerized Microservices

Each service runs in a dedicated container:
- `springboot-app`: 3 containers, each an instance of the backend app
- `postgres`: central relational database
- `redis`: caching layer
- `rabbitmq`: message queue
- `golang-proxy`: load balancer container

Communication is done over a virtual Docker network, allowing services to reach each other by name.

This setup enables:
- **Horizontal scaling** of backend instances
- Easy deployment on any Docker-compatible host
- Consistent environments between dev, staging, and production

---

## 📌 Design Decisions

- **Stateless Application Instances**: Easy to scale horizontally
- **Asynchronous Messaging with RabbitMQ**: Improves UX and throughput
- **Caching via Redis**: Reduces DB strain and enhances performance
- **Database-level Constraints**: Avoids duplicate or overlapping seat reservations
- **Dockerization**: Promotes portability, reproducibility, and environment parity
- **Go-based Load Balancer**: Lightweight, fast, and highly customizable

---

## ✅ Current System Capabilities

- Register & authenticate users with JWT
- Manage movies, theaters, screens, showtimes (admin-only)
- Fetch movies, book showtimes, reserve seats
- Process and log payments
- Add and fetch user reviews
- Automatic cache refresh on data updates
- Background task handling with RabbitMQ
- Docker-based deployment with Go-powered load distribution

---

## 🧠 Future Enhancements

- Integration with real payment gateways (e.g., Stripe, Razorpay)
- Enhanced admin dashboard with analytics
- Support for seat map UI rendering (for frontend use)
- Retry logic for RabbitMQ consumer failures
- Rate limiting & abuse protection via API gateway
- Observability with Prometheus + Grafana for metrics

