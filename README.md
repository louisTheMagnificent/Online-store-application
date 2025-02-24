# 🛒 Online Store Application

## Preface
This project was developed as part of a course assignment, where I primarily focused on the frontend implementation. 
The backend APIs were predefined, meaning that some design limitations exist, and certain functionalities may not be fully optimized. 
Thank you for your understanding.


## 📌 Project Introduction
This is an online store application based on **Vue.js** and **Spring Boot**, including:
- **Login**
- **ProductList and details & Placing order**
- **OrderList and details & canceling order**

## 🚀 Tech stack
### 🔹 **Frontend**
- **Vue.js 2.6.14** - Main frontend framework
- **Vue Router** - Handles routing (if used)
- **Vue CLI 5** - Project setup and build system
- **Core.js** - JavaScript polyfills
- **Babel** - Transpiles modern JavaScript
- **ESLint** - Enforces coding standards

### 🔹 **Backend**
- **Spring Boot** - Main backend framework
- **Spring JPA (Hibernate)** - ORM for database operations
- **PostgreSQL** - Database system
- **RabbitMQ** - Message queue for asynchronous communication
- **gRPC** - Communication between microservices
- **Spring Profiles** - Multi-environment configuration

---

## 📂 Directory structure  
📦 online-store  
┣ 📂 frontend (Vue frontend)  
┃ ┗ 📂 my-vue-app  
┃     ┣ 📂 src  
┃     ┗ 📜 package.json  
┣ 📂 backend (Spring Boot backend)  
┃ ┣ 📂 bank  
┃ ┃     ┗ 📂 src  
┃ ┣ 📂 store  
┃ ┃     ┗ 📂 src  
┃ ┣ 📂 warehouse  
┃ ┃     ┗ 📂 src  
┃ ┣ 📂 deliveryco  
┃ ┃     ┗ 📂 src  
┃ ┗ 📂 email  
┃     ┗ 📂 src  
┣ 📜 README.md  
┗ 📜 .gitignore  

## 🛠️ Environment Setup

### 📌 1. Set Up PostgreSQL Database

- Install **PostgreSQL** if not already installed: [Download PostgreSQL](https://www.postgresql.org/download/)
- Create the required databases:
  ```sql
  CREATE DATABASE project_store;
  CREATE DATABASE project_bank;
  CREATE DATABASE deliveryco;
  CREATE DATABASE warehouses;
  ```
- Update your `application.properties` file with your PostgreSQL credentials:
  ```properties
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  ```

### 📌 2. Set Up RabbitMQ (Message Queue)

- Install RabbitMQ: [Download RabbitMQ](https://www.rabbitmq.com/download.html)
- Start RabbitMQ:
  ```sh
  sudo service rabbitmq-server start  # Linux/macOS
  rabbitmq-server  # Windows
  ```
- Verify that RabbitMQ is running:
  ```sh
  rabbitmqctl status
  ```
- Default RabbitMQ credentials:
  ```properties
  spring.rabbitmq.host=localhost
  spring.rabbitmq.port=5672
  spring.rabbitmq.username=guest
  spring.rabbitmq.password=guest
  ```

### 📌 3. Set Up gRPC (Microservices Communication)

- gRPC Ports:
  - **Bank Service** → `9091`
  - **Warehouse Service** → `9092`
  - **DeliveryCo Service** → `9093`
- gRPC Clients:
  - `store` connects to `bankService`, `warehouseService`, `deliveryCo`
  - `deliveryco` connects to `warehouseService`
- Ensure your `grpc-spring-boot-starter` dependency is in `pom.xml`:
  ```xml
  <dependency>
      <groupId>net.devh</groupId>
      <artifactId>grpc-spring-boot-starter</artifactId>
      <version>2.12.0.RELEASE</version>
  </dependency>
  ```

## 🚀 How to Run the Project

### 1️⃣ Start the Backend (Spring Boot)
1. Make sure your **PostgreSQL database is running** and **Database "deliveryco", "project_bank", "project_store" and "warehouses" exist**.
2. Update the **database configurations** in `backend/src/main/resources/application.properties`.
3. Navigate to each backend directory:
   cd backend/store  # Example for Store service
4. Build and run the Spring Boot application:
   mvn clean install
   mvn spring-boot:run
5. Backend ports: 8080, 8081, 8082, 8083 and 8084

### 2️⃣ Start the Frontend (Vue.js)
1. Navigate to the frontend directory:
   cd frontend/my-vue-app
2. Install dependencies:
   npm install
3. Start the Vue development server:
   npm run serve
4. The frontend will be available at http://localhost:8085.

### 3️⃣ Function Description
1. Each time you run the application, the data in the database are reset.
2. Default username: customer, password: COMP5348
3. Customer has 100 dollar.
4. Order state is changed each 10 seconds and you can only cancel the order in the state "READY_FOR_PICKUP"

## 📌 Contact

If you have any questions, feel free to contact me:

- **GitHub**: [louisTheMagnificent](https://github.com/louisTheMagnificent)
