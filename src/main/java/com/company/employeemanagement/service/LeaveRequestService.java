 
package com.company.employeemanagement.service;

import com.company.employeemanagement.model.LeaveRequest;
import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaveRequestService {
    
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    
    public LeaveRequest saveLeaveRequest(LeaveRequest request) {
        return leaveRequestRepository.save(request);
    }
    
    public List<LeaveRequest> getLeaveRequestsByEmployee(Employee employee) {
        return leaveRequestRepository.findByEmployee(employee);
    }
    
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
    
    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id).orElse(null);
    }
    
    public LeaveRequest updateLeaveRequest(LeaveRequest request) {
        return leaveRequestRepository.save(request);
    }
}