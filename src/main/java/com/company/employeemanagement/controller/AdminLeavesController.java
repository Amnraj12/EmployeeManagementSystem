//// filepath: src/main/java/com/company/employeemanagement/controller/AdminLeavesController.java
package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.LeaveRequest;
import com.company.employeemanagement.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/leaves")
public class AdminLeavesController {

    @Autowired
    private LeaveRequestService leaveRequestService;
    
    // View all leave requests.
    @GetMapping
    public String viewAllLeaveRequests(Model model) {
        model.addAttribute("requests", leaveRequestService.getAllLeaveRequests());
        return "admin/leaveRequests";
    }
    
    // Approve a leave request.
    @PostMapping("/approve/{id}")
    public String approveLeave(@PathVariable Long id) {
        LeaveRequest req = leaveRequestService.getLeaveRequestById(id);
        if (req != null) {
            req.setStatus("Approved");
            leaveRequestService.updateLeaveRequest(req);
        }
        return "redirect:/admin/leaves";
    }
    
    // Deny a leave request.
    @PostMapping("/deny/{id}")
    public String denyLeave(@PathVariable Long id) {
        LeaveRequest req = leaveRequestService.getLeaveRequestById(id);
        if (req != null) {
            req.setStatus("Denied");
            leaveRequestService.updateLeaveRequest(req);
        }
        return "redirect:/admin/leaves";
    }
}