package com.example.leavemanagement.service.impl;

import com.example.leavemanagement.dto.EmployeeRegistrationDto;
import com.example.leavemanagement.dto.LoginDto;
import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.entity.Role;
import com.example.leavemanagement.exception.EmployeeAlreadyExistsException;
import com.example.leavemanagement.exception.ResourceNotFoundException;
import com.example.leavemanagement.repository.EmployeeRepository;
import com.example.leavemanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Employee registerEmployee(EmployeeRegistrationDto registrationDto) {
        if (employeeRepository.existsByEmail(registrationDto.getEmail())) {
            throw new EmployeeAlreadyExistsException("An employee with this email already exists.");
        }

        Employee employee = Employee.builder()
                .fullName(registrationDto.getFullName())
                .email(registrationDto.getEmail())
                .password(registrationDto.getPassword())
                .department(registrationDto.getDepartment())
                .designation(registrationDto.getDesignation())
                .role(Role.EMPLOYEE)
                .build();

        return employeeRepository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee authenticate(LoginDto loginDto) {
        return employeeRepository.findByEmail(loginDto.getEmail())
                .filter(employee -> employee.getPassword().equals(loginDto.getPassword()))
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password."));
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee record was not found."));
    }
}
