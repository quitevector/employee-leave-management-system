package com.example.leavemanagement.controller;

import com.example.leavemanagement.dto.LeaveRequestDto;
import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.entity.LeaveStatus;
import com.example.leavemanagement.exception.DuplicateLeaveRequestException;
import com.example.leavemanagement.service.EmployeeService;
import com.example.leavemanagement.service.LeaveRequestService;
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
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveRequestService leaveRequestService;

    @GetMapping("/employee/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Employee employee = getLoggedInEmployee(session);
        if (employee == null) {
            return "redirect:/login";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("totalLeaves", leaveRequestService.countEmployeeLeaves(employee));
        model.addAttribute("pendingLeaves", leaveRequestService.countEmployeeLeavesByStatus(employee, LeaveStatus.PENDING));
        model.addAttribute("approvedLeaves", leaveRequestService.countEmployeeLeavesByStatus(employee, LeaveStatus.APPROVED));
        model.addAttribute("rejectedLeaves", leaveRequestService.countEmployeeLeavesByStatus(employee, LeaveStatus.REJECTED));
        model.addAttribute("recentLeaves", leaveRequestService.getLeaveHistory(employee).stream().limit(5).toList());
        return "employee-dashboard";
    }

    @GetMapping("/employee/apply-leave")
    public String showApplyLeaveForm(HttpSession session, Model model) {
        if (getLoggedInEmployee(session) == null) {
            return "redirect:/login";
        }
        model.addAttribute("leaveRequestDto", new LeaveRequestDto());
        return "apply-leave";
    }

    @PostMapping("/employee/apply-leave")
    public String applyLeave(@Valid @ModelAttribute LeaveRequestDto leaveRequestDto,
                             BindingResult bindingResult,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        Employee employee = getLoggedInEmployee(session);
        if (employee == null) {
            return "redirect:/login";
        }

        if (leaveRequestDto.getStartDate() != null
                && leaveRequestDto.getEndDate() != null
                && leaveRequestDto.getEndDate().isBefore(leaveRequestDto.getStartDate())) {
            bindingResult.rejectValue("endDate", "dateRange", "End date must be the same as or after start date.");
        }

        if (bindingResult.hasErrors()) {
            return "apply-leave";
        }

        try {
            leaveRequestService.applyLeave(employee, leaveRequestDto);
            redirectAttributes.addFlashAttribute("successMessage", "Leave request submitted successfully.");
            return "redirect:/employee/leave-history";
        } catch (DuplicateLeaveRequestException | IllegalArgumentException exception) {
            bindingResult.reject("leaveError", exception.getMessage());
            return "apply-leave";
        }
    }

    @GetMapping("/employee/leave-history")
    public String leaveHistory(HttpSession session, Model model) {
        Employee employee = getLoggedInEmployee(session);
        if (employee == null) {
            return "redirect:/login";
        }
        model.addAttribute("leaveRequests", leaveRequestService.getLeaveHistory(employee));
        return "leave-history";
    }

    private Employee getLoggedInEmployee(HttpSession session) {
        Object employeeId = session.getAttribute("employeeId");
        if (employeeId == null) {
            return null;
        }
        return employeeService.getEmployeeById((Long) employeeId);
    }
}
