package com.example.leavemanagement.service;

import com.example.leavemanagement.dto.EmployeeRegistrationDto;
import com.example.leavemanagement.dto.LoginDto;
import com.example.leavemanagement.entity.Employee;

public interface EmployeeService {

    Employee registerEmployee(EmployeeRegistrationDto registrationDto);

    Employee authenticate(LoginDto loginDto);

    Employee getEmployeeById(Long id);
}
