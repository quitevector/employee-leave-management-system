package com.example.leavemanagement.controller;

import com.example.leavemanagement.entity.LeaveStatus;
import com.example.leavemanagement.service.LeaveRequestService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final LeaveRequestService leaveRequestService;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        model.addAttribute("leaveRequests", leaveRequestService.getAllLeaveRequests());
        model.addAttribute("pendingCount", leaveRequestService.countLeavesByStatus(LeaveStatus.PENDING));
        model.addAttribute("approvedCount", leaveRequestService.countLeavesByStatus(LeaveStatus.APPROVED));
        model.addAttribute("rejectedCount", leaveRequestService.countLeavesByStatus(LeaveStatus.REJECTED));
        return "admin-dashboard";
    }

    @PostMapping("/admin/leaves/{id}/approve")
    public String approveLeave(@PathVariable Long id,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }
        leaveRequestService.approveLeave(id);
        redirectAttributes.addFlashAttribute("successMessage", "Leave request approved.");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/leaves/{id}/reject")
    public String rejectLeave(@PathVariable Long id,
                              @RequestParam(required = false) String remarks,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }
        leaveRequestService.rejectLeave(id, remarks);
        redirectAttributes.addFlashAttribute("successMessage", "Leave request rejected.");
        return "redirect:/admin/dashboard";
    }

    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("adminId") != null;
    }
}
