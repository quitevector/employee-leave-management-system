package com.example.leavemanagement.repository;

import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.entity.LeaveRequest;
import com.example.leavemanagement.entity.LeaveStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeOrderByAppliedAtDesc(Employee employee);

    @EntityGraph(attributePaths = "employee")
    List<LeaveRequest> findAllByOrderByAppliedAtDesc();

    long countByEmployee(Employee employee);

    long countByEmployeeAndStatus(Employee employee, LeaveStatus status);

    long countByStatus(LeaveStatus status);

    @Query("""
            select count(lr) > 0
            from LeaveRequest lr
            where lr.employee = :employee
              and lr.status <> com.example.leavemanagement.entity.LeaveStatus.REJECTED
              and lr.startDate <= :endDate
              and lr.endDate >= :startDate
            """)
    boolean existsOverlappingLeave(@Param("employee") Employee employee,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);
}
