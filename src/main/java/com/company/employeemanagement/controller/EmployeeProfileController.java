package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class EmployeeProfileController {

    @Autowired
    private EmployeeService employeeService;

    
    @InitBinder("employee")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("profilePicture");
    }

    
    @GetMapping("/employee/profile")
    public String profile(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        System.out.println("DEBUG: Loaded employee from session: " + employee);
        if (employee == null) {
            System.out.println("DEBUG: No employee in session, redirecting to /login");
            return "redirect:/login";
        }
        model.addAttribute("employee", employee);
        return "employee/profile";
    }

    
    @PostMapping("/employee/profile/update")
    public String updateProfile(@ModelAttribute("employee") Employee employeeForm,
                                @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
                                HttpSession session,
                                Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        System.out.println("DEBUG: In updateProfile, session employee: " + employee);
        if (employee == null) {
            System.out.println("DEBUG: No employee in session during update, redirecting to /login");
            return "redirect:/login";
        }
        System.out.println("DEBUG: Employee form details: " + employeeForm);
        
        employee.setAddress(employeeForm.getAddress());
        employee.setPhone(employeeForm.getPhone());
        employee.setAdditionalEmail(employeeForm.getAdditionalEmail());
        employee.setAdditionalPhone(employeeForm.getAdditionalPhone());
        employee.setGender(employeeForm.getGender());
        employee.setMaritalStatus(employeeForm.getMaritalStatus());
        employee.setBloodGroup(employeeForm.getBloodGroup());

        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                System.out.println("DEBUG: Received file: " + profilePicture.getOriginalFilename() + 
                                   ", size: " + profilePicture.getBytes().length + " bytes");
                
                employee.setProfilePicture(profilePicture.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("DEBUG: Exception while processing profile picture.");
            }
        } else {
            System.out.println("DEBUG: No profile picture uploaded.");
        }

        
        employeeService.saveEmployee(employee);
        session.setAttribute("employee", employee);
        System.out.println("DEBUG: Profile update complete. Updated employee: " + employee);
        model.addAttribute("message", "Profile updated successfully.");
        return "redirect:/employee/profile";
    }

    
    @GetMapping("/employee/profile/picture/{id}")
    @ResponseBody
    public byte[] getProfilePicture(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id).orElse(null);
        if (employee != null && employee.getProfilePicture() != null) {
            return employee.getProfilePicture();
        }
        return new byte[0];
    }
}