package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.model.LeaveRequest;
import com.company.employeemanagement.model.RankingRecord;
import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.service.EmployeeService;
import com.company.employeemanagement.service.LeaveRequestService;
import com.company.employeemanagement.service.RankingService;
import com.company.employeemanagement.service.TaskService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminDashboardController {

    @Autowired
    private RankingService rankingService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private LeaveRequestService leaveRequestService;
    
    @GetMapping("/admin/dashboard")
    public String viewDashboard(
            @RequestParam(value = "sortBy", required = false, defaultValue = "points") String sortBy,
            Model model) {
        
        List<RankingRecord> rankings = rankingService.getEmployeeRankings(sortBy);
        
        
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("rankings", rankings);
        model.addAttribute("hasRankings", !rankings.isEmpty());
        
        
        int employeeCount = employeeService.getAllEmployees().size();
        int taskCount = taskService.getAllTasks().size();
        int pendingCount = employeeService.getPendingEmployees().size();
        int leaveCount = leaveRequestService.getAllLeaveRequests().size();
        
        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("taskCount", taskCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("leaveCount", leaveCount);
        
        
        List<ActivityRecord> recentActivities = getRecentActivities();
        model.addAttribute("recentActivities", recentActivities);
        
        return "admin/dashboard";
    }
    
    
    private static class ActivityRecord {
        private String description;
        private LocalDateTime timestamp;
        
        public ActivityRecord(String description, LocalDateTime timestamp) {
            this.description = description;
            this.timestamp = timestamp;
        }
        
        public String getDescription() {
            return description;
        }
        
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
    
    
    private List<ActivityRecord> getRecentActivities() {
        List<ActivityRecord> activities = new ArrayList<>();
        
        
        List<Task> recentTasks = taskService.getAllTasks();
        for (Task task : recentTasks) {
            String status = task.getStatus();
            LocalDateTime timestamp = task.getUpdatedAt() != null ? 
                                     task.getUpdatedAt() : LocalDateTime.now();
            
            if ("Completed".equals(status)) {
                String empName = task.getAssignedEmployee() != null ? 
                               task.getAssignedEmployee().getName() : "Unknown";
                activities.add(new ActivityRecord(
                    "Task \"" + task.getTaskName() + "\" completed by " + empName,
                    timestamp
                ));
            } else if ("Accepted".equals(status)) {
                String empName = task.getAssignedEmployee() != null ? 
                               task.getAssignedEmployee().getName() : "Unknown";
                activities.add(new ActivityRecord(
                    "Task \"" + task.getTaskName() + "\" accepted by " + empName,
                    timestamp
                ));
            } else if (task.getAssignedEmployee() == null && 
                     ("Available".equals(status) || status == null)) {
                activities.add(new ActivityRecord(
                    "New task \"" + task.getTaskName() + "\" created",
                    timestamp
                ));
            }
        }
        
        
        List<LeaveRequest> leaves = leaveRequestService.getAllLeaveRequests();
        for (LeaveRequest leave : leaves) {
            LocalDateTime timestamp = leave.getUpdatedAt() != null ? 
                                    leave.getUpdatedAt() : LocalDateTime.now();
            
            String status = leave.getStatus() != null ? leave.getStatus().toLowerCase() : "pending";
            if (leave.getEmployee() != null) {
                activities.add(new ActivityRecord(
                    "Leave request " + status + " for " + leave.getEmployee().getName(),
                    timestamp
                ));
            }
        }
        
        
        List<Employee> employees = employeeService.getAllEmployees();
        for (Employee emp : employees) {
            if (emp.isApproved()) {
                activities.add(new ActivityRecord(
                    "Employee " + emp.getName() + " account approved",
                    LocalDateTime.now().minusDays((long)(Math.random() * 7))
                ));
            }
        }
        
        
        activities.sort(Comparator.comparing(ActivityRecord::getTimestamp).reversed());
        
        
        return activities.size() > 10 ? activities.subList(0, 10) : activities;
    }
}