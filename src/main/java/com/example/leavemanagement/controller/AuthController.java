package com.example.leavemanagement.controller;

import com.example.leavemanagement.dto.EmployeeRegistrationDto;
import com.example.leavemanagement.dto.LoginDto;
import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.entity.Role;
import com.example.leavemanagement.exception.EmployeeAlreadyExistsException;
import com.example.leavemanagement.exception.ResourceNotFoundException;
import com.example.leavemanagement.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final EmployeeService employeeService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("employeeRegistrationDto", new EmployeeRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerEmployee(@Valid @ModelAttribute EmployeeRegistrationDto employeeRegistrationDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            employeeService.registerEmployee(employeeRegistrationDto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please log in.");
            return "redirect:/login";
        } catch (EmployeeAlreadyExistsException exception) {
            bindingResult.rejectValue("email", "duplicate", exception.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            Employee employee = employeeService.authenticate(loginDto);
            HttpSession currentSession = request.getSession(false);
            if (currentSession != null) {
                currentSession.invalidate();
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("employeeName", employee.getFullName());
            session.setAttribute("employeeRole", employee.getRole().name());

            if (employee.getRole() == Role.ADMIN) {
                session.setAttribute("adminId", employee.getId());
                return "redirect:/admin/dashboard";
            }

            session.setAttribute("employeeId", employee.getId());
            return "redirect:/employee/dashboard";
        } catch (ResourceNotFoundException exception) {
            model.addAttribute("loginError", exception.getMessage());
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "You have been logged out.");
        return "redirect:/login";
    }
}
