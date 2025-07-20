package com.company.employeemanagement.repository;

import com.company.employeemanagement.model.LeaveRequest;
import com.company.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee(Employee employee);
}