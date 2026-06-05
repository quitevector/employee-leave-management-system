package com.example.leavemanagement;

import com.example.leavemanagement.dto.EmployeeRegistrationDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DtoValidationTest {

    @Test
    void employeeRegistrationDtoRejectsInvalidEmail() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            EmployeeRegistrationDto dto = new EmployeeRegistrationDto();
            dto.setFullName("Test User");
            dto.setEmail("invalid-email");
            dto.setPassword("secret123");
            dto.setDepartment("Engineering");
            dto.setDesignation("Developer");

            assertThat(validator.validate(dto))
                    .anyMatch(violation -> violation.getPropertyPath().toString().equals("email"));
        }
    }
}
