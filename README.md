# Employee Leave Management System

A complete Spring Boot 3 mini project for employee registration, login, leave application, leave history, admin approval, and rejection workflows.

## Tech Stack

- Java 21
- Spring Boot 3
- Spring MVC
- Thymeleaf
- Spring Data JPA
- MySQL
- Maven
- HTML, CSS, Bootstrap 5
- Lombok

## Features

- Employee registration with email validation and duplicate email prevention
- Employee login with session-based dashboard access
- Employee dashboard with leave statistics
- Apply leave using DTO-based form validation
- Date validation and duplicate overlapping leave prevention
- Leave history tracking
- Admin dashboard for all leave requests
- Approve and reject leave requests
- Global exception handling
- SQL schema and sample data

## Project Structure

```text
src/main/java/com/example/leavemanagement
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

DTO classes are used for form handling:

- `EmployeeRegistrationDto`
- `LoginDto`
- `LeaveRequestDto`

Entities are used for persistence only and are not bound directly to HTML forms.

## Database Setup

1. Start MySQL.
2. Open MySQL Workbench or the MySQL command line.
3. Run `sql/schema.sql`.
4. Run `sql/sample-data.sql`.

Default database:

```text
employee_leave_db
```

Update database credentials in `src/main/resources/application.properties` if your MySQL username or password is different.

```properties
spring.datasource.username=root
spring.datasource.password=root
```

## Sample Login Accounts

Admin:

```text
Email: admin@leavems.com
Password: admin123
```

Employee:

```text
Email: aarav.sharma@example.com
Password: employee123
```

## Run in Visual Studio Code

1. Open the project folder in Visual Studio Code.
2. Ensure Java 21, Maven, and MySQL are installed.
3. Configure MySQL credentials in `src/main/resources/application.properties`.
4. Create the database by running the SQL scripts:

   * `sql/schema.sql`
   * `sql/sample-data.sql` (optional)
   * `sql/seed-10-employees-20-leaves.sql` (for demo data)
5. Open a terminal in the project root directory.
6. Run the application using:

```bash
mvn spring-boot:run
```

7. Alternatively, run `EmployeeLeaveManagementSystemApplication.java` directly from VS Code.
8. Visit:

```text
http://localhost:8080
```

9. Log in using the configured employee or admin credentials.


## Run with Maven

```bash
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080
```

## Notes

This project uses plain-text passwords because it is a mini project focused on Spring MVC, Thymeleaf, DTO validation, JPA relationships, and CRUD workflow. For production, add Spring Security and password hashing.
