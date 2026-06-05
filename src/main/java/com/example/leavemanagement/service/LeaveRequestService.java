package com.example.leavemanagement.service;

import com.example.leavemanagement.dto.LeaveRequestDto;
import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.entity.LeaveRequest;
import com.example.leavemanagement.entity.LeaveStatus;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequest applyLeave(Employee employee, LeaveRequestDto leaveRequestDto);

    List<LeaveRequest> getLeaveHistory(Employee employee);

    List<LeaveRequest> getAllLeaveRequests();

    LeaveRequest approveLeave(Long leaveRequestId);

    LeaveRequest rejectLeave(Long leaveRequestId, String remarks);

    long countEmployeeLeaves(Employee employee);

    long countEmployeeLeavesByStatus(Employee employee, LeaveStatus status);

    long countLeavesByStatus(LeaveStatus status);
}
