package com.company.employeemanagement.facade;

import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TaskManagementFacade {
    @Autowired
    private TaskService taskService;

    public Task createAndAssignTask(Task task) {
        return taskService.createTask(task);
    }

    public Task searchTaskByNumber(String taskNumber) {
        return taskService.getTaskByNumber(taskNumber);
    }

    public List<Task> searchTasksByName(String taskName) {
        return taskService.searchTasksByName(taskName);
    }
}