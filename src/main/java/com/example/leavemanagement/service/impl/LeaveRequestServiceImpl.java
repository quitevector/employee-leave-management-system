package com.example.leavemanagement.service.impl;

import com.example.leavemanagement.dto.LeaveRequestDto;
import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.entity.LeaveRequest;
import com.example.leavemanagement.entity.LeaveStatus;
import com.example.leavemanagement.exception.DuplicateLeaveRequestException;
import com.example.leavemanagement.exception.ResourceNotFoundException;
import com.example.leavemanagement.repository.LeaveRequestRepository;
import com.example.leavemanagement.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    @Transactional
    public LeaveRequest applyLeave(Employee employee, LeaveRequestDto leaveRequestDto) {
        if (leaveRequestDto.getEndDate().isBefore(leaveRequestDto.getStartDate())) {
            throw new IllegalArgumentException("End date must be the same as or after start date.");
        }

        boolean duplicateLeave = leaveRequestRepository.existsOverlappingLeave(
                employee,
                leaveRequestDto.getStartDate(),
                leaveRequestDto.getEndDate()
        );
        if (duplicateLeave) {
            throw new DuplicateLeaveRequestException("A pending or approved leave already exists for these dates.");
        }

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .leaveType(leaveRequestDto.getLeaveType())
                .startDate(leaveRequestDto.getStartDate())
                .endDate(leaveRequestDto.getEndDate())
                .reason(leaveRequestDto.getReason())
                .status(LeaveStatus.PENDING)
                .appliedAt(LocalDateTime.now())
                .build();

        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequest> getLeaveHistory(Employee employee) {
        return leaveRequestRepository.findByEmployeeOrderByAppliedAtDesc(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAllByOrderByAppliedAtDesc();
    }

    @Override
    @Transactional
    public LeaveRequest approveLeave(Long leaveRequestId) {
        LeaveRequest leaveRequest = getLeaveRequest(leaveRequestId);
        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setReviewedAt(LocalDateTime.now());
        leaveRequest.setAdminRemarks("Approved by admin.");
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    @Transactional
    public LeaveRequest rejectLeave(Long leaveRequestId, String remarks) {
        LeaveRequest leaveRequest = getLeaveRequest(leaveRequestId);
        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setReviewedAt(LocalDateTime.now());
        leaveRequest.setAdminRemarks(remarks == null || remarks.isBlank() ? "Rejected by admin." : remarks.trim());
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public long countEmployeeLeaves(Employee employee) {
        return leaveRequestRepository.countByEmployee(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public long countEmployeeLeavesByStatus(Employee employee, LeaveStatus status) {
        return leaveRequestRepository.countByEmployeeAndStatus(employee, status);
    }

    @Override
    @Transactional(readOnly = true)
    public long countLeavesByStatus(LeaveStatus status) {
        return leaveRequestRepository.countByStatus(status);
    }

    private LeaveRequest getLeaveRequest(Long leaveRequestId) {
        return leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request was not found."));
    }
}
