USE employee_leave_db;

INSERT INTO employee (fullName, email, password, department, designation, role)
VALUES
    ('System Administrator', 'admin@leavems.com', 'admin123', 'Human Resources', 'Admin', 'ADMIN'),
    ('Aarav Sharma', 'aarav.sharma@example.com', 'employee123', 'Engineering', 'Software Engineer', 'EMPLOYEE'),
    ('Meera Patel', 'meera.patel@example.com', 'employee123', 'Finance', 'Accounts Executive', 'EMPLOYEE');

INSERT INTO leave_request (leave_type, start_date, end_date, reason, status, applied_at, reviewed_at, admin_remarks, employee_id)
VALUES
    ('Sick Leave', '2026-06-08', '2026-06-09', 'Medical rest advised by doctor.', 'PENDING', '2026-06-05 09:30:00', NULL, NULL, 2),
    ('Casual Leave', '2026-06-12', '2026-06-12', 'Family function at home.', 'APPROVED', '2026-06-04 10:15:00', '2026-06-04 14:00:00', 'Approved by admin.', 3),
    ('Earned Leave', '2026-06-18', '2026-06-20', 'Personal travel plans.', 'REJECTED', '2026-06-03 11:20:00', '2026-06-03 15:45:00', 'Team coverage unavailable.', 2);
