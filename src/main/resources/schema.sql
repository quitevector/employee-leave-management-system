CREATE TABLE employee (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fullName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    designation VARCHAR(255) NOT NULL,
    role ENUM('EMPLOYEE', 'ADMIN') NOT NULL,
    PRIMARY KEY (id)
);

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
