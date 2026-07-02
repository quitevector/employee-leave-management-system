INSERT INTO employee (id, fullName, email, password, department, designation, role)
VALUES
    (1, 'System Administrator', 'admin@leavems.com', 'admin123', 'Human Resources', 'HR Administrator', 'ADMIN'),
    (2, 'Aarav Sharma', 'aarav.sharma@example.com', 'employee123', 'Engineering', 'Software Engineer', 'EMPLOYEE'),
    (3, 'Meera Patel', 'meera.patel@example.com', 'employee123', 'Finance', 'Accounts Executive', 'EMPLOYEE'),
    (4, 'Rohan Verma', 'rohan.verma@example.com', 'employee123', 'Sales', 'Sales Manager', 'EMPLOYEE'),
    (5, 'Ananya Iyer', 'ananya.iyer@example.com', 'employee123', 'Marketing', 'Digital Marketing Specialist', 'EMPLOYEE'),
    (6, 'Vikram Singh', 'vikram.singh@example.com', 'employee123', 'Operations', 'Operations Coordinator', 'EMPLOYEE'),
    (7, 'Priya Nair', 'priya.nair@example.com', 'employee123', 'Human Resources', 'HR Executive', 'EMPLOYEE'),
    (8, 'Kabir Khan', 'kabir.khan@example.com', 'employee123', 'Engineering', 'QA Analyst', 'EMPLOYEE'),
    (9, 'Sneha Reddy', 'sneha.reddy@example.com', 'employee123', 'Customer Support', 'Support Lead', 'EMPLOYEE'),
    (10, 'Neha Gupta', 'neha.gupta@example.com', 'employee123', 'Product', 'Product Analyst', 'EMPLOYEE');

INSERT INTO leave_request
    (id, leave_type, start_date, end_date, reason, status, applied_at, reviewed_at, admin_remarks, employee_id)
VALUES
    (1, 'Sick Leave', '2026-06-08', '2026-06-09',
     'Fever and doctor advised two days of rest.',
     'APPROVED', '2026-06-05 09:15:00.000000', '2026-06-05 11:30:00.000000', 'Approved. Please update your team lead.', 2),

    (2, 'Casual Leave', '2026-06-12', '2026-06-12',
     'Need to attend a family function in the city.',
     'APPROVED', '2026-06-05 10:05:00.000000', '2026-06-05 12:20:00.000000', 'Approved for one day.', 3),

    (3, 'Earned Leave', '2026-06-16', '2026-06-18',
     'Planned personal travel with family.',
     'PENDING', '2026-06-06 08:45:00.000000', NULL, NULL, 4),

    (4, 'Work From Home', '2026-06-10', '2026-06-10',
     'Home internet technician visit scheduled during office hours.',
     'REJECTED', '2026-06-04 14:10:00.000000', '2026-06-04 16:00:00.000000', 'Rejected because work from home is not categorized as leave.', 5),

    (5, 'Sick Leave', '2026-06-20', '2026-06-21',
     'Medical appointment and recovery time required.',
     'PENDING', '2026-06-07 09:30:00.000000', NULL, NULL, 6),

    (6, 'Casual Leave', '2026-06-14', '2026-06-14',
     'Personal banking and documentation work.',
     'APPROVED', '2026-06-05 15:20:00.000000', '2026-06-05 17:05:00.000000', 'Approved. Ensure pending tasks are handed over.', 7),

    (7, 'Earned Leave', '2026-07-01', '2026-07-05',
     'Annual vacation planned in advance.',
     'PENDING', '2026-06-08 10:00:00.000000', NULL, NULL, 8),

    (8, 'Sick Leave', '2026-06-11', '2026-06-11',
     'Dental procedure scheduled in the morning.',
     'APPROVED', '2026-06-03 11:10:00.000000', '2026-06-03 13:25:00.000000', 'Approved for medical appointment.', 9),

    (9, 'Casual Leave', '2026-06-24', '2026-06-25',
     'Travel for cousin wedding ceremony.',
     'REJECTED', '2026-06-06 16:40:00.000000', '2026-06-07 10:15:00.000000', 'Rejected due to product release activities on these dates.', 10),

    (10, 'Maternity Leave', '2026-07-10', '2026-08-20',
     'Maternity leave request submitted with medical documents.',
     'APPROVED', '2026-06-01 09:00:00.000000', '2026-06-02 10:30:00.000000', 'Approved as per company policy.', 5),

    (11, 'Paternity Leave', '2026-06-22', '2026-06-28',
     'Paternity leave request for newborn care and family support.',
     'APPROVED', '2026-06-04 09:25:00.000000', '2026-06-04 12:45:00.000000', 'Approved. Congratulations.', 4),

    (12, 'Earned Leave', '2026-07-15', '2026-07-19',
     'Planned hometown visit during school holidays.',
     'PENDING', '2026-06-09 10:50:00.000000', NULL, NULL, 2),

    (13, 'Casual Leave', '2026-06-30', '2026-06-30',
     'Need leave for property registration formalities.',
     'APPROVED', '2026-06-08 13:15:00.000000', '2026-06-08 15:40:00.000000', 'Approved for one day.', 6),

    (14, 'Sick Leave', '2026-06-17', '2026-06-17',
     'Migraine and medical consultation scheduled.',
     'REJECTED', '2026-06-06 08:30:00.000000', '2026-06-06 10:05:00.000000', 'Rejected because the same date conflicts with a mandatory client workshop.', 7),

    (15, 'Earned Leave', '2026-08-03', '2026-08-07',
     'Long-planned vacation and travel booking completed.',
     'PENDING', '2026-06-10 09:10:00.000000', NULL, NULL, 3),

    (16, 'Casual Leave', '2026-06-26', '2026-06-26',
     'School admission appointment for child.',
     'APPROVED', '2026-06-09 14:35:00.000000', '2026-06-09 16:10:00.000000', 'Approved. Please share handover notes.', 8),

    (17, 'Sick Leave', '2026-06-13', '2026-06-13',
     'Seasonal allergy treatment and rest required.',
     'APPROVED', '2026-06-05 08:20:00.000000', '2026-06-05 10:10:00.000000', 'Approved for medical reason.', 10),

    (18, 'Earned Leave', '2026-07-21', '2026-07-23',
     'Personal travel to attend a family event out of station.',
     'REJECTED', '2026-06-07 12:05:00.000000', '2026-06-07 17:25:00.000000', 'Rejected due to overlapping quarter-end support schedule.', 9),

    (19, 'Casual Leave', '2026-07-02', '2026-07-02',
     'Personal appointment with government office.',
     'PENDING', '2026-06-10 11:45:00.000000', NULL, NULL, 6),

    (20, 'Sick Leave', '2026-06-27', '2026-06-28',
     'Doctor advised rest after minor viral infection.',
     'PENDING', '2026-06-11 09:55:00.000000', NULL, NULL, 2);
