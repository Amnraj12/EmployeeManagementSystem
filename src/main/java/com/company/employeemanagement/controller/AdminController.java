package com.company.employeemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/home")
    public String dashboard() {
        
        return "redirect:/admin/dashboard";
    }
}