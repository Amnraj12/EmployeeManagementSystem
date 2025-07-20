package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/employee/tasks")
public class EmployeeTasksController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/available")
    public String viewAvailableTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "employee/availableTasks";
    }

    @PostMapping("/accept/{id}")
    public String acceptTask(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Employee employee = (Employee) session.getAttribute("employee");
        System.out.println("DEBUG: In acceptTask, employee = " + employee);
        if (employee != null) {
            
            List<Task> myTasks = taskService.getTasksByEmployee(employee);
            boolean alreadyAccepted = myTasks.stream()
                .anyMatch(task -> "Accepted".equalsIgnoreCase(task.getStatus()));
            if (alreadyAccepted) {
                redirectAttributes.addFlashAttribute("error", "You can only work on one task at a time. Please complete your current task before joining a new one.");
                System.out.println("DEBUG: Already accepted a task. Redirecting to availableTasks.");
                return "redirect:/employee/tasks/available";
            }
            
            Task assigned = taskService.assignTask(id, employee);
            System.out.println("DEBUG: Assigned task: " + assigned);
        } else {
            System.out.println("DEBUG: No employee found in session!");
        }
        
        System.out.println("DEBUG: Redirecting to /employee/tasks/my");
        return "redirect:/employee/tasks/my";
    }
       

    @PostMapping("/finish/{id}")
    public String finishTask(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            Task finished = taskService.markTaskFinished(id, employee);
            if (finished == null) {
                redirectAttributes.addFlashAttribute("error", "Unable to mark task as complete.");
            }
        }
        return "redirect:/employee/tasks/my";
    }

    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable Long id,
                            @RequestParam String employeeComment,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            Task updated = taskService.addEmployeeComment(id, employeeComment, employee);
            if (updated == null) {
                redirectAttributes.addFlashAttribute("error", "Unable to add comment.");
            }
        }
        return "redirect:/employee/tasks/my";
    }

    @GetMapping("/my")
    public String viewMyTasks(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            
            model.addAttribute("tasks", taskService.getTasksByEmployeeOrdered(employee));
            return "employee/myTasks";
        }
        return "redirect:/employee/dashboard";
    }


    
    @PostMapping("/progress/{id}")
    public String updateTaskProgress(@PathVariable Long id, @RequestParam int progress,
                                     HttpSession session, RedirectAttributes redirectAttributes) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            Task task = taskService.getTaskById(id);
            if (task != null && task.getAssignedEmployee() != null &&
                task.getAssignedEmployee().getId().equals(employee.getId()) &&
                "Accepted".equalsIgnoreCase(task.getStatus())) {
                taskService.updateTaskProgress(id, progress);
                redirectAttributes.addFlashAttribute("success", "Progress updated successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error", "You are not assigned to this task or it is not accepted.");
            }
        }
        return "redirect:/employee/tasks/my";
    }
}