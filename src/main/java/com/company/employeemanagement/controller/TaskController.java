package com.company.employeemanagement.controller;

import com.company.employeemanagement.facade.TaskManagementFacade;
import com.company.employeemanagement.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskManagementFacade taskManagementFacade;

    
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskManagementFacade.createAndAssignTask(task);
    }

    
    @GetMapping("/{taskNumber}")
    public Task getTask(@PathVariable String taskNumber) {
        return taskManagementFacade.searchTaskByNumber(taskNumber);
    }

    
    @GetMapping("/search")
    public List<Task> searchTasks(@RequestParam("name") String taskName) {
        return taskManagementFacade.searchTasksByName(taskName);
    }
}