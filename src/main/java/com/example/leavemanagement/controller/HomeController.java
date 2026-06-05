package com.example.leavemanagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("employeeId") != null) {
            return "redirect:/employee/dashboard";
        }
        if (session.getAttribute("adminId") != null) {
            return "redirect:/admin/dashboard";
        }
        return "home";
    }
}
