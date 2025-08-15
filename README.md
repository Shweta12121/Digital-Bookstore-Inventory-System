# Digital Bookstore Inventory System

A production-ready Spring Boot application to manage books, record sales, and generate sales reports with CSV export.

## Tech Stack
- Java 17, Spring Boot (Web, Data JPA, Validation, Thymeleaf)
- MySQL
- Maven
- Lombok
- Frontend: HTML/CSS/Bootstrap + Thymeleaf

## Features
- Book CRUD (title, author, price, stockQuantity, category, ISBN) with search by keyword
- Sales recording with stock deduction and insufficient stock protection
- Low-stock alerts (stockQuantity < 5)
- Sales reports (daily, monthly, all-time total) and CSV export

## Prerequisites
- Java 17
- Maven 3.9+
- MySQL 8.x running locally

## Database Setup
1. Create database:
```sql
CREATE DATABASE digital_bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
2. Update `src/main/resources/application.properties` if needed:
```
spring.datasource.url=jdbc:mysql://localhost:3306/digital_bookstore?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=changeme
```
3. On application start, sample data from `data.sql` will be inserted.

## Run Locally
1. Build:
```bash
mvn -q -DskipTests clean package
```
2. Run:
```bash
mvn spring-boot:run
```
3. Visit:
- Home: `http://localhost:8080/`
- Books: `http://localhost:8080/books`
- Sales: `http://localhost:8080/sales`
- Reports: `http://localhost:8080/reports`

## Project Structure
- `DigitalBookstoreInventoryApplication` – Spring Boot entry point
- `entity/Book`, `entity/Sale` – JPA entities
- `repository/BookRepository`, `repository/SaleRepository` – Spring Data JPA
- `service/BookService`, `service/SaleService` – Business logic
- `controller/*` – MVC controllers
- `templates/*` – Thymeleaf views with Bootstrap layout
- `data.sql` – initial sample data

## Notes
- JPA ddl-auto is `update` for local development; for production, manage migrations (e.g., Flyway) and set `ddl-auto=validate`.
- Lombok is used for boilerplate reduction; ensure Lombok plugin is enabled in your IDE.

## Default Credentials
- This application has no login. If you add security later, document credentials here.

## License
MIT
