package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.model.LeaveRequest;
import com.company.employeemanagement.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee/leaves")
public class EmployeeLeavesController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    
    @GetMapping
    public String viewLeaveRequests(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            List<LeaveRequest> requests = leaveRequestService.getLeaveRequestsByEmployee(employee);
            model.addAttribute("requests", requests);
        }
        return "employee/leaveRequests";
    }

    
    @GetMapping("/apply")
    public String applyLeaveForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "employee/applyLeave";
    }

    
    @PostMapping("/apply")
    public String submitLeaveRequest(@Valid @ModelAttribute LeaveRequest leaveRequest,
                                    BindingResult result,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(System.out::println);
            return "employee/applyLeave";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            leaveRequest.setEmployee(employee);
            leaveRequest.setStatus("Applied");
            leaveRequestService.saveLeaveRequest(leaveRequest);
            redirectAttributes.addFlashAttribute("message", "Leave application submitted successfully.");
        } else {
            System.out.println("Employee in session is null.");
        }
        return "redirect:/employee/leaves";

        }
}