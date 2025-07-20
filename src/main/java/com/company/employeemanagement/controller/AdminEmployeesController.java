//// filepath: src/main/java/com/company/employeemanagement/controller/AdminEmployeesController.java
package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Added import

@Controller
@RequestMapping("/admin/employees")
public class AdminEmployeesController {

    private final EmployeeService employeeService;

    @Autowired
    public AdminEmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "admin/employees"; // renders employees.html
    }
    
    // New endpoint to list pending employees for approval
    @GetMapping("/pending")
    public String pendingEmployees(Model model) {
        List<Employee> pending = employeeService.getPendingEmployees();
        System.out.println("Pending employees count: " + pending.size());
        model.addAttribute("pendingEmployees", pending);
        return "admin/pendingEmployees";
    }
    
    // Endpoint to approve a pending employee
    @PostMapping("/approve/{id}")
    public String approveEmployee(@PathVariable Long id) {
        employeeService.approveEmployee(id);
        return "redirect:/admin/employees/pending";
    }
    
    // Endpoint to reject (delete) a pending employee
    @PostMapping("/reject/{id}")
    public String rejectEmployee(@PathVariable Long id) {
        employeeService.rejectEmployee(id);
        return "redirect:/admin/employees/pending";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/admin/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.rejectEmployee(id);
        return "redirect:/admin/employees";
    }
}