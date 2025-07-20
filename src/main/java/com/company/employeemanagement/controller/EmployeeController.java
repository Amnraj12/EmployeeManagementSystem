package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.model.LeaveRequest;
import com.company.employeemanagement.service.TaskService;
import com.company.employeemanagement.service.LeaveRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private LeaveRequestService leaveRequestService;
    
    @GetMapping("/employee/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        
        if (employee == null) {
            return "redirect:/login";
        }
        
        
        List<Task> myTasks = taskService.getTasksByEmployeeOrdered(employee);
        
        
        Map<String, Long> taskStatusCount = myTasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
        
        
        int totalPoints = myTasks.stream()
            .filter(task -> "Reviewed".equals(task.getStatus()) || "Completed".equals(task.getStatus()))
            .mapToInt(Task::getPoints)
            .sum();
        
        
        long priorityTasksCompleted = myTasks.stream()
            .filter(task -> ("Reviewed".equals(task.getStatus()) || "Completed".equals(task.getStatus())) 
                  && "High".equals(task.getPriority()))
            .count();
        
        
        List<Task> recentTasks = myTasks.stream()
            .limit(3)
            .collect(Collectors.toList());
        
        
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByEmployee(employee);
        List<LeaveRequest> recentLeaveRequests = leaveRequests.stream()
            .limit(3)
            .collect(Collectors.toList());
        
        
        model.addAttribute("employee", employee);
        model.addAttribute("taskStatusCount", taskStatusCount);
        model.addAttribute("totalPoints", totalPoints);
        model.addAttribute("priorityTasksCompleted", priorityTasksCompleted);
        model.addAttribute("recentTasks", recentTasks);
        model.addAttribute("recentLeaveRequests", recentLeaveRequests);
        model.addAttribute("totalTasksCount", myTasks.size());
        
        return "employee/dashboard";
    }
}