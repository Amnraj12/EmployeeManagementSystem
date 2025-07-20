package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.service.EmployeeService;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {
        
        if("admin@company.com".equalsIgnoreCase(email) && "admin".equals(password)) {
            session.setAttribute("role", "admin");
            return "redirect:/admin/dashboard";
        }
        
        Optional<Employee> optional = employeeService.findByEmail(email);
        if(optional.isPresent()){
            Employee employee = optional.get();
            if(!employee.getPassword().equals(password)) {
                model.addAttribute("error", "Invalid credentials, please try again.");
                return "login";
            } else if(!employee.isApproved()){
                model.addAttribute("error", "Your account is pending approval. Please wait a few hours for admin approval.");
                return "login";
            } else {
                session.setAttribute("role", "employee");
                session.setAttribute("employee", employee);
                return "redirect:/employee/dashboard";
            }
        } else {
            model.addAttribute("error", "Invalid credentials, please try again.");
            return "login";
        }
    }
    
    
    @PostMapping("/register")
    public String processRegistration(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String password,
                                    Model model) {
        
        Optional<Employee> existing = employeeService.findByEmail(email);
        if(existing.isPresent()){
            model.addAttribute("message", "This email is already registered. Please login or use a different email.");
            return "register";
        }
        
        
        Employee newEmployee = new Employee();
        newEmployee.setName(name);
        newEmployee.setEmail(email);
        newEmployee.setPassword(password);
        newEmployee.setApproved(false);
        
        employeeService.saveEmployee(newEmployee);
        
        
        model.addAttribute("message", "Registration successful! Your account is pending admin approval. This process may take up to 2 hours.");
        return "login";  
    }
    
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}