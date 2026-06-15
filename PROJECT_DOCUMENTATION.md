# Employee Leave Management System - Complete Beginner-Friendly Documentation

This document explains the Employee Leave Management System from the ground up. It is written for someone who understands basic programming ideas such as variables, functions, classes, and databases, but is new to Java, Spring Boot, Spring MVC, Thymeleaf, JPA, Maven, and enterprise-style application development.

The documentation is based on the actual source code in this project:

```text
C:\Users\91639\Documents\employee-leave-management-system
```

## 1. Project Overview

### Purpose of the Project

The Employee Leave Management System is a web application that helps employees apply for leave and helps administrators approve or reject those leave requests.

In a manual office process, an employee may write an email or paper request to ask for leave. The administrator or HR person then reviews it, replies, and keeps records manually. This project automates that process.

### Problem Being Solved

The project solves these common problems:

- Employees need a simple way to apply for leave.
- Employees need to see whether their leave request is pending, approved, or rejected.
- Admins need one place to view all leave requests.
- Admins need to approve or reject requests quickly.
- The company needs employee and leave data stored permanently in a database.
- Duplicate leave applications for overlapping dates should be prevented.

### Main Features

The project currently supports:

- Home page
- Employee registration
- Employee login
- Session-based authentication
- Employee dashboard
- Apply leave form
- Leave history page
- Admin dashboard
- Leave approval
- Leave rejection
- Email validation
- Required field validation
- Date validation
- Duplicate leave prevention
- Duplicate employee email prevention
- MySQL database storage
- SQL scripts for schema and sample data
- Global exception handling

### End Users

There are two main user types:

1. Employee

   An employee can register, log in, apply for leave, and view leave history.

2. Admin

   An admin can log in, view all leave requests, approve requests, and reject requests.

The roles are represented by the `Role` enum in:

```text
src/main/java/com/example/leavemanagement/entity/Role.java
```

```java
public enum Role {
    EMPLOYEE,
    ADMIN
}
```

## 2. High-Level Architecture

### What Architecture Means

Architecture means how the project is organized and how different parts communicate with each other.

This project is a layered Spring Boot web application. Each layer has a specific responsibility.

```text
Browser
   |
   v
Thymeleaf HTML Pages
   |
   v
Controller Layer
   |
   v
Service Layer
   |
   v
Repository Layer
   |
   v
MySQL Database
```

### MVC Architecture

MVC means Model-View-Controller.

In this project:

- Model means the data objects, such as `Employee`, `LeaveRequest`, and DTO classes.
- View means the Thymeleaf HTML pages, such as `login.html`, `register.html`, and `admin-dashboard.html`.
- Controller means Java classes that receive web requests and decide which page to show or which service method to call.

Example:

```text
User opens /login
   |
   v
AuthController handles request
   |
   v
Controller returns login.html
   |
   v
Browser displays login page
```

### Controller to Service to Repository to Database Flow

The project follows this common enterprise flow:

```text
Controller -> Service -> Repository -> Database
```

Each part has a job:

- Controller: Handles web requests and page navigation.
- Service: Contains business logic.
- Repository: Talks to the database using Spring Data JPA.
- Database: Stores permanent data.

Example: Applying for leave

```text
Employee fills apply-leave.html
   |
   v
POST /employee/apply-leave
   |
   v
EmployeeController.applyLeave()
   |
   v
LeaveRequestService.applyLeave()
   |
   v
LeaveRequestRepository.existsOverlappingLeave()
   |
   v
MySQL checks duplicate leave
   |
   v
LeaveRequestRepository.save()
   |
   v
New row inserted into leave_request table
```

### Why This Layering Is Useful

Layering keeps the project clean.

For example, the HTML page does not directly talk to the database. The controller does not write SQL. The service does not know how HTML is displayed. Each layer does one type of work.

This makes the project easier to:

- Understand
- Debug
- Test
- Modify
- Extend later

## 3. Technology Stack

### Java

Java is the programming language used to write the backend code.

In this project, Java is used for:

- Entity classes
- DTO classes
- Controllers
- Services
- Repositories
- Exception classes
- Main application startup class

The project uses Java 21, configured in:

```text
pom.xml
```

```xml
<java.version>21</java.version>
```

### Spring Boot

Spring Boot is a framework that makes it easier to create Java web applications.

Without Spring Boot, a developer must manually configure many things. Spring Boot provides automatic configuration for web server, MVC, database, Thymeleaf, validation, and more.

In this project, Spring Boot is used to:

- Start the application
- Run an embedded web server
- Configure Spring MVC
- Configure JPA
- Connect to MySQL
- Load Thymeleaf templates

The main class is:

```text
src/main/java/com/example/leavemanagement/EmployeeLeaveManagementSystemApplication.java
```

### Spring MVC

Spring MVC is the web framework inside Spring that handles HTTP requests and responses.

In this project, Spring MVC is used in controller classes:

```text
controller/HomeController.java
controller/AuthController.java
controller/EmployeeController.java
controller/AdminController.java
```

Example:

```java
@GetMapping("/login")
public String showLoginForm(Model model) {
    model.addAttribute("loginDto", new LoginDto());
    return "login";
}
```

This means:

- When the user opens `/login`
- Spring calls `showLoginForm`
- A new `LoginDto` is sent to the page
- Spring displays `login.html`

### Thymeleaf

Thymeleaf is a server-side template engine. It allows HTML pages to display data from Java.

In this project, Thymeleaf pages are stored in:

```text
src/main/resources/templates
```

Example from a template:

```html
<span th:text="${employee.fullName}">Employee</span>
```

This means Thymeleaf replaces the sample text `Employee` with the actual employee name from the backend.

### Spring Data JPA

Spring Data JPA helps Java classes communicate with a relational database.

JPA means Java Persistence API. It maps Java objects to database tables.

In this project:

- `Employee` class maps to `employee` table.
- `LeaveRequest` class maps to `leave_request` table.
- Repository interfaces provide database operations.

Example:

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

Spring Data JPA automatically creates the database query for methods like `findByEmail`.

### MySQL

MySQL is the database used to store project data permanently.

In this project, MySQL stores:

- Employee records
- Leave request records

The database name is:

```text
employee_leave_db
```

The configuration is in:

```text
src/main/resources/application.properties
```

### Maven

Maven is a build and dependency management tool.

Dependency means an external library used by the project.

Maven is used here to:

- Download Spring Boot libraries
- Download MySQL driver
- Download Lombok
- Compile the project
- Run tests
- Start the application

The Maven configuration file is:

```text
pom.xml
```

### Bootstrap

Bootstrap is a CSS framework used to create clean and responsive web pages.

Responsive means the pages can adjust to different screen sizes, such as laptop and mobile screens.

In this project, Bootstrap is loaded in each HTML page using CDN links.

Example:

```html
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
```

Bootstrap is used for:

- Navbar
- Buttons
- Forms
- Cards
- Tables
- Alerts
- Responsive grids

### Lombok

Lombok is a Java library that reduces repeated code.

Normally, Java classes need getter methods, setter methods, constructors, and builder code. Lombok generates those during compilation.

Example from `Employee.java`:

```java
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
```

These annotations generate:

- Getters
- Setters
- Builder methods
- No-argument constructor
- All-argument constructor

## 4. Project Folder Structure

### Root Folder

```text
employee-leave-management-system
```

Important root files:

```text
pom.xml
README.md
PROJECT_DOCUMENTATION.md
sql/
src/
```

### pom.xml

This file defines:

- Project name
- Java version
- Spring Boot version
- Dependencies
- Maven plugins

### README.md

This file gives a short setup guide.

### PROJECT_DOCUMENTATION.md

This file is the detailed beginner-friendly documentation.

### sql Folder

```text
sql/
    schema.sql
    sample-data.sql
    seed-10-employees-20-leaves.sql
```

This folder contains database scripts.

### src/main/java

This folder contains Java backend source code.

Base package:

```text
com.example.leavemanagement
```

### controller Package

Path:

```text
src/main/java/com/example/leavemanagement/controller
```

Files:

```text
HomeController.java
AuthController.java
EmployeeController.java
AdminController.java
```

Purpose:

The controller package handles browser requests. It decides what page to show and what service method to call.

It interacts with:

- DTO package for form data
- Service package for business logic
- Thymeleaf templates for views
- HTTP session for login state

### dto Package

Path:

```text
src/main/java/com/example/leavemanagement/dto
```

Files:

```text
EmployeeRegistrationDto.java
LoginDto.java
LeaveRequestDto.java
```

Purpose:

DTO means Data Transfer Object. DTOs carry form data between HTML pages and controllers.

This project uses DTOs instead of binding forms directly to entity classes.

This is safer because:

- Forms only expose fields that the user should enter.
- Validation can be placed on form-specific fields.
- Database entities remain focused on persistence.

### entity Package

Path:

```text
src/main/java/com/example/leavemanagement/entity
```

Files:

```text
Employee.java
LeaveRequest.java
Role.java
LeaveStatus.java
```

Purpose:

The entity package contains classes that map to database tables.

It interacts with:

- Repository layer for database operations
- Service layer for business logic
- Database tables through JPA

### repository Package

Path:

```text
src/main/java/com/example/leavemanagement/repository
```

Files:

```text
EmployeeRepository.java
LeaveRequestRepository.java
```

Purpose:

The repository package handles database access.

Repositories extend `JpaRepository`, which gives built-in methods like:

- `save`
- `findById`
- `findAll`
- `deleteById`
- `count`

### service Package

Path:

```text
src/main/java/com/example/leavemanagement/service
src/main/java/com/example/leavemanagement/service/impl
```

Files:

```text
EmployeeService.java
LeaveRequestService.java
EmployeeServiceImpl.java
LeaveRequestServiceImpl.java
```

Purpose:

The service package contains business logic.

Business logic means rules of the application, such as:

- Do not allow duplicate employee email.
- Do not allow duplicate overlapping leave.
- Set leave status to `PENDING` when applied.
- Set review time when admin approves or rejects.

### exception Package

Path:

```text
src/main/java/com/example/leavemanagement/exception
```

Files:

```text
DuplicateLeaveRequestException.java
EmployeeAlreadyExistsException.java
ResourceNotFoundException.java
GlobalExceptionHandler.java
```

Purpose:

This package handles error situations in a cleaner way.

Examples:

- Employee email already exists.
- Leave request overlaps with an existing request.
- Record not found.

### resources Folder

Path:

```text
src/main/resources
```

Important files and folders:

```text
application.properties
static/css/style.css
templates/
```

Purpose:

The resources folder contains configuration, CSS, and Thymeleaf HTML pages.

## 5. Database Design

### Database Name

The database name is:

```text
employee_leave_db
```

### Tables

There are two main tables:

```text
employee
leave_request
```

### Employee Table

Defined in:

```text
sql/schema.sql
```

```sql
CREATE TABLE employee (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    designation VARCHAR(255) NOT NULL,
    role ENUM('EMPLOYEE', 'ADMIN') NOT NULL,
    PRIMARY KEY (id)
);
```

This table stores users of the system.

Fields:

- `id`: Unique employee ID.
- `full_name`: Employee full name.
- `email`: Employee email, must be unique.
- `password`: Employee password.
- `department`: Employee department.
- `designation`: Job title.
- `role`: Either `EMPLOYEE` or `ADMIN`.

### Leave Request Table

```sql
CREATE TABLE leave_request (
    id BIGINT NOT NULL AUTO_INCREMENT,
    leave_type VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(500) NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL,
    applied_at DATETIME(6) NOT NULL,
    reviewed_at DATETIME(6),
    admin_remarks VARCHAR(500),
    employee_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_leave_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee (id)
        ON DELETE CASCADE
);
```

This table stores leave applications.

Fields:

- `id`: Unique leave request ID.
- `leave_type`: Type of leave, such as Sick Leave or Casual Leave.
- `start_date`: First day of leave.
- `end_date`: Last day of leave.
- `reason`: Reason for leave.
- `status`: `PENDING`, `APPROVED`, or `REJECTED`.
- `applied_at`: Date and time when leave was applied.
- `reviewed_at`: Date and time when admin reviewed it.
- `admin_remarks`: Admin comments.
- `employee_id`: Foreign key pointing to employee.

### Relationship

One employee can have many leave requests.

One leave request belongs to one employee.

```text
employee
   1
   |
   | has many
   |
   *
leave_request
```

### Primary Keys

A primary key uniquely identifies a row.

In this project:

- `employee.id` is the primary key of employee table.
- `leave_request.id` is the primary key of leave request table.

### Foreign Key

A foreign key connects one table to another.

In this project:

```text
leave_request.employee_id -> employee.id
```

This means every leave request must belong to an existing employee.

## 6. Entity Classes

### What Is an Entity?

An entity is a Java class that represents a database table.

In this project:

- `Employee.java` represents the `employee` table.
- `LeaveRequest.java` represents the `leave_request` table.

### Employee Entity

File:

```text
src/main/java/com/example/leavemanagement/entity/Employee.java
```

Important annotations:

```java
@Entity
@Table(name = "employee")
```

Meaning:

- `@Entity`: This class is managed by JPA and maps to a database table.
- `@Table(name = "employee")`: The database table name is `employee`.

Fields:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

`id` is the primary key. The database automatically generates it.

```java
@Column(nullable = false)
private String fullName;
```

`fullName` stores the employee name. It cannot be null.

```java
@Column(nullable = false, unique = true)
private String email;
```

`email` stores the employee email. It cannot be null and must be unique.

```java
@Column(nullable = false)
private String password;
```

`password` stores the login password.

```java
@Column(nullable = false)
private String department;
```

`department` stores the department name.

```java
@Column(nullable = false)
private String designation;
```

`designation` stores the employee job title.

```java
@Enumerated(EnumType.STRING)
@Column(nullable = false)
private Role role;
```

`role` stores whether the user is `EMPLOYEE` or `ADMIN`.

`EnumType.STRING` means the database stores text values like `EMPLOYEE`, not numeric values like `0`.

Relationship:

```java
@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
@Builder.Default
private List<LeaveRequest> leaveRequests = new ArrayList<>();
```

This means one employee can have many leave requests.

`mappedBy = "employee"` means the `LeaveRequest` class owns the relationship through its `employee` field.

`cascade = CascadeType.ALL` means changes to employee can cascade to related leave requests.

`orphanRemoval = true` means if a leave request is removed from the employee list, JPA can delete that orphaned record.

### LeaveRequest Entity

File:

```text
src/main/java/com/example/leavemanagement/entity/LeaveRequest.java
```

Important annotations:

```java
@Entity
@Table(name = "leave_request")
```

This maps the class to the `leave_request` table.

Fields:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

Primary key generated by database.

```java
@Column(nullable = false)
private String leaveType;
```

Stores leave type, such as Sick Leave or Casual Leave.

```java
@Column(nullable = false)
private LocalDate startDate;
```

Stores leave start date.

```java
@Column(nullable = false)
private LocalDate endDate;
```

Stores leave end date.

```java
@Column(nullable = false, length = 500)
private String reason;
```

Stores reason for leave. Maximum length is 500 characters.

```java
@Enumerated(EnumType.STRING)
@Column(nullable = false)
private LeaveStatus status;
```

Stores status as text:

- `PENDING`
- `APPROVED`
- `REJECTED`

```java
@Column(nullable = false)
private LocalDateTime appliedAt;
```

Stores when the leave was applied.

```java
private LocalDateTime reviewedAt;
```

Stores when the admin reviewed it. It can be null for pending requests.

```java
@Column(length = 500)
private String adminRemarks;
```

Stores admin comments. It can be null for pending requests.

Relationship:

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "employee_id", nullable = false)
private Employee employee;
```

This means many leave requests can belong to one employee.

`employee_id` is the foreign key column in the database.

`FetchType.LAZY` means JPA does not load the employee object until needed. This improves performance, but the project must be careful when using employee details outside a transaction. The admin list handles this using `@EntityGraph` in `LeaveRequestRepository`.

### Role Enum

File:

```text
src/main/java/com/example/leavemanagement/entity/Role.java
```

Values:

```java
EMPLOYEE,
ADMIN
```

Used to decide whether a logged-in user should go to the employee dashboard or admin dashboard.

### LeaveStatus Enum

File:

```text
src/main/java/com/example/leavemanagement/entity/LeaveStatus.java
```

Values:

```java
PENDING,
APPROVED,
REJECTED
```

Used to track the state of a leave request.

## 7. DTO Classes

### Why DTOs Are Used

DTO means Data Transfer Object.

In a web application, form data comes from HTML pages. Instead of directly binding this data to entity classes, this project uses DTOs.

Example:

The registration page does not directly create an `Employee` entity. It fills an `EmployeeRegistrationDto`.

Then the service converts the DTO into an `Employee`.

```text
register.html
   |
   v
EmployeeRegistrationDto
   |
   v
EmployeeServiceImpl
   |
   v
Employee entity
   |
   v
employee table
```

### What Problem DTOs Solve

DTOs help because:

- They protect database entities from direct form binding.
- They keep validation rules close to form fields.
- They avoid exposing fields users should not control, such as `role` or `id`.
- They make forms easier to modify later.

### EmployeeRegistrationDto

File:

```text
src/main/java/com/example/leavemanagement/dto/EmployeeRegistrationDto.java
```

Fields:

- `fullName`
- `email`
- `password`
- `department`
- `designation`

Validation annotations:

```java
@NotBlank(message = "Full name is required")
@Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
```

This means full name is required and must have a valid length.

```java
@Email(message = "Enter a valid email address")
```

This checks email format.

```java
@Size(min = 6, max = 60)
```

This checks password length.

Important:

The user cannot choose `role` during registration. The service automatically assigns `Role.EMPLOYEE`.

### LoginDto

File:

```text
src/main/java/com/example/leavemanagement/dto/LoginDto.java
```

Fields:

- `email`
- `password`

Validation:

- Email is required.
- Email must be valid.
- Password is required.

This DTO is used by `login.html` and `AuthController`.

### LeaveRequestDto

File:

```text
src/main/java/com/example/leavemanagement/dto/LeaveRequestDto.java
```

Fields:

- `leaveType`
- `startDate`
- `endDate`
- `reason`

Validation:

```java
@NotBlank
```

Checks text fields are not empty.

```java
@NotNull
```

Checks date fields are selected.

```java
@FutureOrPresent
```

Checks dates are today or in the future.

```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
```

Tells Spring how to convert HTML date input into `LocalDate`.

```java
@Size(min = 10, max = 500)
```

Reason must be between 10 and 500 characters.

## 8. Repository Layer

### What Is a Repository?

A repository is an interface that talks to the database.

In Spring Data JPA, you usually do not need to write SQL for simple operations. You extend `JpaRepository`, and Spring creates the implementation automatically.

### EmployeeRepository

File:

```text
src/main/java/com/example/leavemanagement/repository/EmployeeRepository.java
```

Code:

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

`JpaRepository<Employee, Long>` means:

- This repository works with `Employee` entity.
- The primary key type is `Long`.

Built-in methods available:

- `save(employee)`
- `findById(id)`
- `findAll()`
- `deleteById(id)`
- `count()`

Custom methods:

```java
Optional<Employee> findByEmail(String email);
```

Spring Data JPA reads the method name and creates a query like:

```sql
SELECT * FROM employee WHERE email = ?
```

```java
boolean existsByEmail(String email);
```

Checks whether an employee exists with the given email.

Used for duplicate registration prevention.

### LeaveRequestRepository

File:

```text
src/main/java/com/example/leavemanagement/repository/LeaveRequestRepository.java
```

Important methods:

```java
List<LeaveRequest> findByEmployeeOrderByAppliedAtDesc(Employee employee);
```

Finds all leave requests for one employee, sorted by newest first.

```java
@EntityGraph(attributePaths = "employee")
List<LeaveRequest> findAllByOrderByAppliedAtDesc();
```

Finds all leave requests for admin dashboard.

`@EntityGraph(attributePaths = "employee")` tells JPA to also load the related employee. This is important because the admin page displays:

```html
${leave.employee.fullName}
${leave.employee.email}
```

```java
long countByEmployee(Employee employee);
```

Counts all leave requests for one employee.

```java
long countByEmployeeAndStatus(Employee employee, LeaveStatus status);
```

Counts employee leave requests by status.

```java
long countByStatus(LeaveStatus status);
```

Counts all leave requests by status for admin dashboard.

Custom duplicate leave query:

```java
@Query("""
        select count(lr) > 0
        from LeaveRequest lr
        where lr.employee = :employee
          and lr.status <> com.example.leavemanagement.entity.LeaveStatus.REJECTED
          and lr.startDate <= :endDate
          and lr.endDate >= :startDate
        """)
boolean existsOverlappingLeave(...)
```

This checks if the employee already has a pending or approved leave that overlaps the new date range.

The overlap condition is:

```text
existing start <= new end
AND
existing end >= new start
```

Example:

Existing leave:

```text
10 June to 12 June
```

New leave:

```text
11 June to 13 June
```

These overlap, so the new request is blocked.

Rejected leaves are ignored, because an employee should be allowed to apply again if a previous request was rejected.

## 9. Service Layer

### What Is the Service Layer?

The service layer contains business rules.

Business rules are the rules of the real-world problem.

For example:

- Registration should fail if email already exists.
- Leave should be pending when applied.
- Leave end date cannot be before start date.
- Admin approval should set status to approved.

### EmployeeService

File:

```text
src/main/java/com/example/leavemanagement/service/EmployeeService.java
```

Methods:

```java
Employee registerEmployee(EmployeeRegistrationDto registrationDto);
Employee authenticate(LoginDto loginDto);
Employee getEmployeeById(Long id);
```

This is an interface. An interface says what methods exist, but not how they work.

### EmployeeServiceImpl

File:

```text
src/main/java/com/example/leavemanagement/service/impl/EmployeeServiceImpl.java
```

This class implements the actual logic.

#### registerEmployee

Workflow:

```text
Receive EmployeeRegistrationDto
   |
   v
Check if email already exists
   |
   v
If yes, throw EmployeeAlreadyExistsException
   |
   v
If no, build Employee entity
   |
   v
Set role to EMPLOYEE
   |
   v
Save employee using EmployeeRepository
```

Important code:

```java
if (employeeRepository.existsByEmail(registrationDto.getEmail())) {
    throw new EmployeeAlreadyExistsException("An employee with this email already exists.");
}
```

This prevents duplicate employee accounts.

#### authenticate

Workflow:

```text
Receive LoginDto
   |
   v
Find employee by email
   |
   v
Compare password
   |
   v
Return employee if valid
   |
   v
Throw ResourceNotFoundException if invalid
```

Current project uses plain-text password comparison:

```java
.filter(employee -> employee.getPassword().equals(loginDto.getPassword()))
```

For a real production project, passwords should be hashed using Spring Security.

#### getEmployeeById

Finds employee by ID. If not found, throws `ResourceNotFoundException`.

### LeaveRequestService

File:

```text
src/main/java/com/example/leavemanagement/service/LeaveRequestService.java
```

Methods:

- `applyLeave`
- `getLeaveHistory`
- `getAllLeaveRequests`
- `approveLeave`
- `rejectLeave`
- `countEmployeeLeaves`
- `countEmployeeLeavesByStatus`
- `countLeavesByStatus`

### LeaveRequestServiceImpl

File:

```text
src/main/java/com/example/leavemanagement/service/impl/LeaveRequestServiceImpl.java
```

#### applyLeave

Workflow:

```text
Receive Employee and LeaveRequestDto
   |
   v
Check if end date is before start date
   |
   v
Check duplicate overlapping leave
   |
   v
Create LeaveRequest entity
   |
   v
Set status to PENDING
   |
   v
Set appliedAt to current date/time
   |
   v
Save leave request
```

Important logic:

```java
if (leaveRequestDto.getEndDate().isBefore(leaveRequestDto.getStartDate())) {
    throw new IllegalArgumentException("End date must be the same as or after start date.");
}
```

Duplicate prevention:

```java
boolean duplicateLeave = leaveRequestRepository.existsOverlappingLeave(...);
if (duplicateLeave) {
    throw new DuplicateLeaveRequestException(...);
}
```

#### approveLeave

Workflow:

```text
Find leave request
Set status to APPROVED
Set reviewedAt to now
Set adminRemarks
Save
```

#### rejectLeave

Workflow:

```text
Find leave request
Set status to REJECTED
Set reviewedAt to now
Set adminRemarks
Save
```

If admin does not type remarks, default remarks are used:

```text
Rejected by admin.
```

## 10. Controller Layer

### What Is a Controller?

A controller receives HTTP requests from the browser.

Example:

When the browser asks for:

```text
/login
```

Spring finds a controller method with:

```java
@GetMapping("/login")
```

### HomeController

File:

```text
src/main/java/com/example/leavemanagement/controller/HomeController.java
```

Endpoint:

```java
@GetMapping("/")
```

Purpose:

Shows home page if nobody is logged in.

If an employee is logged in, redirects to:

```text
/employee/dashboard
```

If an admin is logged in, redirects to:

```text
/admin/dashboard
```

### AuthController

File:

```text
src/main/java/com/example/leavemanagement/controller/AuthController.java
```

Handles registration, login, and logout.

#### GET /register

Shows registration form.

Adds an empty DTO to the model:

```java
model.addAttribute("employeeRegistrationDto", new EmployeeRegistrationDto());
```

This DTO is used by `register.html`.

#### POST /register

Receives registration form data.

Flow:

```text
Validate EmployeeRegistrationDto
   |
   v
If validation errors, return register page
   |
   v
Call employeeService.registerEmployee()
   |
   v
If success, redirect to login
```

If duplicate email is found:

```java
bindingResult.rejectValue("email", "duplicate", exception.getMessage());
```

This shows the error near the email field.

#### GET /login

Shows login form.

#### POST /login

Receives login form data.

Flow:

```text
Validate LoginDto
   |
   v
Authenticate employee
   |
   v
Create HTTP session
   |
   v
If ADMIN, redirect to admin dashboard
   |
   v
If EMPLOYEE, redirect to employee dashboard
```

Session attributes:

For both roles:

```text
employeeName
employeeRole
```

For admin:

```text
adminId
```

For employee:

```text
employeeId
```

#### POST /logout

Invalidates the session and redirects to login page.

### EmployeeController

File:

```text
src/main/java/com/example/leavemanagement/controller/EmployeeController.java
```

Handles employee-only pages.

#### GET /employee/dashboard

Shows employee dashboard.

Data sent to page:

- Employee details
- Total leaves
- Pending leaves
- Approved leaves
- Rejected leaves
- Recent leave requests

#### GET /employee/apply-leave

Shows apply leave form.

If employee is not logged in, redirects to login.

#### POST /employee/apply-leave

Receives leave application form.

Flow:

```text
Check session
Validate LeaveRequestDto
Check date range
Call leaveRequestService.applyLeave()
Redirect to leave history
```

#### GET /employee/leave-history

Shows all leave requests of the logged-in employee.

### AdminController

File:

```text
src/main/java/com/example/leavemanagement/controller/AdminController.java
```

Handles admin-only pages.

#### GET /admin/dashboard

Shows all leave requests and status counts.

Data sent to page:

- All leave requests
- Pending count
- Approved count
- Rejected count

#### POST /admin/leaves/{id}/approve

Approves one leave request.

`{id}` means the leave request ID is part of the URL.

Example:

```text
/admin/leaves/5/approve
```

This approves leave request with ID 5.

#### POST /admin/leaves/{id}/reject

Rejects one leave request.

Can receive optional remarks from the admin.

## 11. Thymeleaf Pages

Thymeleaf pages are stored in:

```text
src/main/resources/templates
```

### fragments/header.html

Contains the navigation bar.

It changes links based on session:

- If nobody is logged in: Home, Register, Login
- If employee is logged in: Dashboard, Apply Leave, Leave History
- If admin is logged in: Admin Dashboard

It also shows the logged-in user's name.

### fragments/footer.html

Contains the common footer.

### home.html

Purpose:

Landing page for the application.

User actions:

- Register Employee
- Login

Backend interaction:

Displayed by `HomeController`.

### register.html

Purpose:

Allows a new employee to register.

Uses:

```html
th:object="${employeeRegistrationDto}"
```

This binds the form to `EmployeeRegistrationDto`.

User actions:

- Enter full name
- Enter email
- Enter department
- Enter designation
- Enter password
- Submit form

Backend interaction:

Submits to:

```text
POST /register
```

Handled by `AuthController.registerEmployee`.

### login.html

Purpose:

Allows admin or employee to log in.

Uses:

```html
th:object="${loginDto}"
```

Backend interaction:

Submits to:

```text
POST /login
```

Handled by `AuthController.login`.

### employee-dashboard.html

Purpose:

Shows employee summary.

Displays:

- Employee name
- Department and designation
- Total leaves
- Pending leaves
- Approved leaves
- Rejected leaves
- Recent leave requests

Backend interaction:

Displayed by:

```text
GET /employee/dashboard
```

### apply-leave.html

Purpose:

Allows employee to apply for leave.

Uses:

```html
th:object="${leaveRequestDto}"
```

Fields:

- Leave type
- Start date
- End date
- Reason

Backend interaction:

Submits to:

```text
POST /employee/apply-leave
```

Handled by `EmployeeController.applyLeave`.

### leave-history.html

Purpose:

Shows all leave requests of the logged-in employee.

Displays:

- Leave type
- Start date
- End date
- Reason
- Status
- Admin remarks
- Applied date

Backend interaction:

Displayed by:

```text
GET /employee/leave-history
```

### admin-dashboard.html

Purpose:

Allows admin to view and review leave requests.

Displays:

- Employee name
- Employee email
- Leave type
- Dates
- Reason
- Status
- Action buttons

Actions:

- Approve pending request
- Reject pending request with optional remarks

Backend interaction:

Approve submits to:

```text
POST /admin/leaves/{id}/approve
```

Reject submits to:

```text
POST /admin/leaves/{id}/reject
```

### error.html

Purpose:

Displays friendly error messages from `GlobalExceptionHandler`.

## 12. Authentication Flow

### What Authentication Means

Authentication means checking who the user is.

In this project, authentication is simple and session-based.

This project does not use Spring Security. Instead, it manually stores login details in the HTTP session.

### Login Process

```text
User opens login.html
   |
   v
User enters email and password
   |
   v
POST /login
   |
   v
AuthController.login()
   |
   v
EmployeeService.authenticate()
   |
   v
EmployeeRepository.findByEmail()
   |
   v
Password is checked
   |
   v
Session is created
   |
   v
Redirect based on role
```

### Session Handling

An HTTP session stores data for a logged-in user.

For employee login:

```java
session.setAttribute("employeeId", employee.getId());
session.setAttribute("employeeName", employee.getFullName());
session.setAttribute("employeeRole", employee.getRole().name());
```

For admin login:

```java
session.setAttribute("adminId", employee.getId());
session.setAttribute("employeeName", employee.getFullName());
session.setAttribute("employeeRole", employee.getRole().name());
```

### Role Handling

Role is checked after successful login:

```java
if (employee.getRole() == Role.ADMIN) {
    return "redirect:/admin/dashboard";
}
return "redirect:/employee/dashboard";
```

### Admin vs Employee Access

Employee pages check:

```text
session.employeeId
```

Admin pages check:

```text
session.adminId
```

If the required session attribute is missing, the user is redirected to login.

## 13. Leave Request Workflow

This section explains the full flow from registration to approval.

### Step 1: Registration

```text
User opens /register
   |
   v
register.html is displayed
   |
   v
User fills form
   |
   v
POST /register
   |
   v
EmployeeRegistrationDto is validated
   |
   v
EmployeeServiceImpl checks duplicate email
   |
   v
Employee is saved with role EMPLOYEE
```

### Step 2: Login

```text
User opens /login
   |
   v
User enters email and password
   |
   v
AuthController receives LoginDto
   |
   v
EmployeeServiceImpl authenticates
   |
   v
Session is created
```

### Step 3: Employee Dashboard

After login, employee goes to:

```text
/employee/dashboard
```

The dashboard shows counts and recent leave requests.

### Step 4: Apply Leave

Employee opens:

```text
/employee/apply-leave
```

The employee fills leave type, dates, and reason.

When submitted:

```text
LeaveRequestDto is validated
Date range is checked
Duplicate leave is checked
LeaveRequest entity is created
Status becomes PENDING
Data is saved
```

### Step 5: Leave History

Employee is redirected to:

```text
/employee/leave-history
```

The page displays all leave requests and statuses.

### Step 6: Admin Review

Admin logs in and opens:

```text
/admin/dashboard
```

Admin sees all leave requests.

For pending requests, admin can:

- Approve
- Reject with remarks

### Step 7: Approve or Reject

Approve:

```text
POST /admin/leaves/{id}/approve
   |
   v
LeaveRequestServiceImpl.approveLeave()
   |
   v
Status becomes APPROVED
reviewedAt is set
adminRemarks is set
```

Reject:

```text
POST /admin/leaves/{id}/reject
   |
   v
LeaveRequestServiceImpl.rejectLeave()
   |
   v
Status becomes REJECTED
reviewedAt is set
adminRemarks is set
```

## 14. Validation Logic

### Email Validation

Email validation is done using:

```java
@Email
```

Used in:

- `EmployeeRegistrationDto`
- `LoginDto`

This checks whether the email has a valid format.

### Required Field Validation

Required fields use:

```java
@NotBlank
@NotNull
```

`@NotBlank` is for text fields.

`@NotNull` is for date fields.

Examples:

- Full name is required.
- Email is required.
- Password is required.
- Leave type is required.
- Start date is required.
- End date is required.
- Reason is required.

### Date Validation

Date validation happens in two places.

First, DTO checks that dates are today or future:

```java
@FutureOrPresent
```

Second, controller and service check that end date is not before start date:

```java
leaveRequestDto.getEndDate().isBefore(leaveRequestDto.getStartDate())
```

### Duplicate Leave Prevention

Duplicate leave prevention is done in `LeaveRequestRepository`.

The query checks whether an employee already has a pending or approved leave that overlaps the requested date range.

Rejected leaves are ignored.

### Duplicate Employee Prevention

Duplicate employee prevention is done in `EmployeeServiceImpl`.

It uses:

```java
employeeRepository.existsByEmail(registrationDto.getEmail())
```

If email already exists, the service throws:

```text
EmployeeAlreadyExistsException
```

## 15. Exception Handling

### What Is an Exception?

An exception is an error that happens while the program is running.

Examples:

- Login details are wrong.
- Employee record is not found.
- Duplicate leave is applied.

### Custom Exceptions

#### EmployeeAlreadyExistsException

Used when registration email already exists.

#### DuplicateLeaveRequestException

Used when employee tries to apply for overlapping leave.

#### ResourceNotFoundException

Used when a required record is not found.

Examples:

- Invalid login
- Employee ID not found
- Leave request ID not found

### GlobalExceptionHandler

File:

```text
src/main/java/com/example/leavemanagement/exception/GlobalExceptionHandler.java
```

This class uses:

```java
@ControllerAdvice
```

`@ControllerAdvice` means this class can handle exceptions from all controllers.

It handles:

```java
@ExceptionHandler(ResourceNotFoundException.class)
```

This displays a specific error page for missing records.

It also handles:

```java
@ExceptionHandler(Exception.class)
```

This handles general unexpected errors.

## 16. application.properties

File:

```text
src/main/resources/application.properties
```

Line-by-line explanation:

```properties
spring.application.name=employee-leave-management-system
```

Sets the application name.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_leave_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
```

Configures the MySQL database connection.

Parts:

- `jdbc:mysql`: Use MySQL through JDBC.
- `localhost`: MySQL is running on the same computer.
- `3306`: Default MySQL port.
- `employee_leave_db`: Database name.
- `createDatabaseIfNotExist=true`: Create database if missing.
- `useSSL=false`: Do not use SSL for local development.
- `serverTimezone=UTC`: Sets timezone handling for database connection.

```properties
spring.datasource.username=root
```

MySQL username.

```properties
spring.datasource.password=abhi@SHEK123
```

MySQL password currently configured in the project. This must match your local MySQL password.

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Tells Spring which MySQL JDBC driver class to use.

```properties
spring.jpa.hibernate.ddl-auto=update
```

Tells Hibernate to update database tables based on entity classes.

For learning, `update` is convenient.

For production, migrations such as Flyway or Liquibase are preferred.

```properties
spring.jpa.show-sql=true
```

Prints SQL queries in the console.

Useful for learning and debugging.

```properties
spring.jpa.properties.hibernate.format_sql=true
```

Formats printed SQL so it is easier to read.

```properties
spring.jpa.open-in-view=false
```

Disables Open Session in View.

This is cleaner because database access should happen in service methods, not while rendering templates.

```properties
spring.thymeleaf.cache=false
```

Disables Thymeleaf template caching during development.

This helps see template changes without restarting sometimes.

```properties
server.port=8080
```

Runs the application on port 8080.

Application URL:

```text
http://localhost:8080
```

## 17. SQL Scripts

### schema.sql

File:

```text
sql/schema.sql
```

Purpose:

Creates the database and tables.

It does:

```text
Create employee_leave_db
Use employee_leave_db
Drop old leave_request table
Drop old employee table
Create employee table
Create leave_request table
Create foreign key relationship
```

Use this when setting up the database from scratch.

### sample-data.sql

File:

```text
sql/sample-data.sql
```

Purpose:

Adds a small amount of sample data.

It contains:

- 1 admin
- 2 employees
- 3 leave requests

Use this for a quick demo.

### seed-10-employees-20-leaves.sql

File:

```text
sql/seed-10-employees-20-leaves.sql
```

Purpose:

Adds larger demo data.

It contains:

- 10 employees
- 20 leave requests
- Mixed statuses: `APPROVED`, `REJECTED`, and `PENDING`

Important:

This script truncates both tables first:

```sql
TRUNCATE TABLE leave_request;
TRUNCATE TABLE employee;
```

That means it deletes old data before inserting new demo data.

Use it when you want a clean demo database.

## 18. How to Run the Project

### Step 1: Install Required Software

Install:

- Java 21
- Maven
- MySQL Server
- MySQL Workbench
- IntelliJ IDEA or Visual Studio Code

### Step 2: Open the Project

Open this folder in your IDE:

```text
C:\Users\91639\Documents\employee-leave-management-system
```

### Step 3: Configure Database Password

Open:

```text
src/main/resources/application.properties
```

Make sure these match your MySQL:

```properties
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### Step 4: Create Database Tables

Open MySQL Workbench.

Run:

```text
sql/schema.sql
```

Optional quick demo data:

```text
sql/sample-data.sql
```

Optional larger demo data:

```text
sql/seed-10-employees-20-leaves.sql
```

### Step 5: Run the Application

Option 1: From IDE

Run:

```text
EmployeeLeaveManagementSystemApplication.java
```

Option 2: From terminal

```bash
mvn spring-boot:run
```

### Step 6: Open Browser

Open:

```text
http://localhost:8080
```

### Step 7: Login

If you used sample data:

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

## 20. Future Enhancements

### 1. Add Spring Security

Current login is manually handled with sessions.

Improvement:

Use Spring Security for:

- Secure login
- Password hashing
- Role-based access
- Logout handling
- CSRF protection

How to implement:

- Add Spring Security dependency.
- Create a security configuration class.
- Use `BCryptPasswordEncoder`.
- Store encrypted passwords.
- Protect `/admin/**` for admin only.
- Protect `/employee/**` for employees only.

### 2. Password Hashing

Current passwords are stored as plain text.

Improvement:

Store hashed passwords using BCrypt.

Example:

```text
employee123 -> encrypted hash
```

Then compare using password encoder instead of direct string comparison.

### 3. Leave Balance

Current project does not track how many leaves an employee has left.

Improvement:

Add fields such as:

- casualLeaveBalance
- sickLeaveBalance
- earnedLeaveBalance

Then reduce balance when leave is approved.

### 4. Admin Employee Management

Current admin only reviews leave requests.

Improvement:

Allow admin to:

- View all employees
- Edit employee details
- Disable employee accounts
- Change roles

### 5. Search and Filter

Current admin dashboard shows all leave requests.

Improvement:

Add filters:

- Status
- Department
- Employee name
- Date range

### 6. Pagination

If many leave requests exist, one page can become slow.

Improvement:

Use Spring Data `Pageable` to show data page by page.

### 7. Email Notifications

Improvement:

Send email when:

- Employee applies for leave
- Admin approves leave
- Admin rejects leave

Use Spring Boot mail support.

### 8. File Upload

Improvement:

Allow employees to upload medical certificates for sick leave.

Implementation idea:

- Add file input in `apply-leave.html`.
- Store file path in database.
- Save files in a server folder or cloud storage.

### 9. REST API

Improvement:

Create REST endpoints so a mobile app or frontend framework can use the backend.

Example endpoints:

```text
GET /api/leaves
POST /api/leaves
PUT /api/leaves/{id}/approve
```

### 10. Better Error Pages

Current project has a simple `error.html`.

Improvement:

Create separate pages for:

- 404 Not Found
- 403 Forbidden
- 500 Server Error

### 11. Audit Logs

Improvement:

Track who approved or rejected each leave request.

Add fields:

- reviewedBy
- createdAt
- updatedAt

### 12. Unit and Integration Tests

Current project has a small DTO validation test.

Improvement:

Add tests for:

- Employee registration
- Login
- Duplicate leave prevention
- Admin approve/reject logic
- Controller page loading

### 13. Use Flyway or Liquibase

Current database schema is managed with SQL scripts and Hibernate update.

Improvement:

Use Flyway migration files.

Example:

```text
V1__create_tables.sql
V2__insert_sample_data.sql
```

This gives safer database version control.

### 14. Improve UI with Local Bootstrap Files

Current templates load Bootstrap from CDN.

Improvement:

Download Bootstrap into:

```text
src/main/resources/static
```

This allows the UI to work even without internet.

### 15. Add Leave Cancellation

Improvement:

Allow employees to cancel pending leave requests.

Implementation:

- Add cancel button in leave history.
- Add controller endpoint.
- Add service method.
- Only allow cancellation if status is `PENDING`.

## Final Project Explanation in One Paragraph

The Employee Leave Management System is a Spring Boot MVC web application where employees register and log in, apply for leave through Thymeleaf forms backed by DTO validation, and view their leave history. The backend follows a clean layered architecture: controllers handle browser requests, services apply business rules, repositories use Spring Data JPA to access MySQL, and entities map Java classes to database tables. Admin users can log in separately, view all leave requests, and approve or reject pending requests. The project demonstrates Java enterprise concepts such as MVC, session handling, DTOs, validation, JPA relationships, repositories, service interfaces, exception handling, SQL schema design, and Bootstrap-based responsive UI.
