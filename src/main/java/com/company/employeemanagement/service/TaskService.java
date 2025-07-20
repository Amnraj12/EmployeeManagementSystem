package com.company.employeemanagement.service;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus("Available");
        task.setTaskNumber("T" + (taskRepository.count() + 1));
        return taskRepository.save(task);
    }

    public Task getTaskByNumber(String taskNumber) {
        return taskRepository.findByTaskNumber(taskNumber);
    }

    public List<Task> searchTasksByName(String taskName) {
        return taskRepository.findByTaskNameContaining(taskName);
    }
    
    public List<Task> getTasksByEmployeeOrdered(Employee employee) {
        return taskRepository.findByAssignedEmployeeOrderByCreatedAtDesc(employee);
    }
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
    
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    
    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElse(null);
    }
    
    public Task assignTask(Long taskId, Employee employee) {
        List<Task> employeeTasks = taskRepository.findByAssignedEmployee(employee);
        boolean hasActiveTask = employeeTasks.stream()
                .anyMatch(t -> "Accepted".equalsIgnoreCase(t.getStatus()));
        if(hasActiveTask) {
            return null;
        }
        Task task = getTaskById(taskId);
        if(task != null && "Available".equalsIgnoreCase(task.getStatus())){
            task.setAssignedEmployee(employee);
            task.setStatus("Accepted");
            return taskRepository.save(task);
        }
        return task;
    }
    
    
    public Task markTaskFinished(Long taskId, Employee employee) {
        Task task = getTaskById(taskId);
        if(task != null && task.getAssignedEmployee() != null &&
        task.getAssignedEmployee().getId().equals(employee.getId())){
            task.setStatus("Completed");
            return taskRepository.save(task);
        }
        return task;
    }

    public Task addEmployeeComment(Long taskId, String comment, Employee employee) {
        Task task = getTaskById(taskId);
        if(task != null && task.getAssignedEmployee() != null &&
        task.getAssignedEmployee().getId().equals(employee.getId())){
            String existing = task.getEmployeeComment();
            if(existing == null || existing.trim().isEmpty()){
                task.setEmployeeComment(comment);
            } else {
                task.setEmployeeComment(existing + "\n" + comment);
            }
            return taskRepository.save(task);
        }
        return task;
    }
    
    public Task addAdminComment(Long taskId, String comment) {
        Task task = getTaskById(taskId);
        if(task != null){
            task.setAdminComment(comment);
            return taskRepository.save(task);
        }
        return task;
    }
    
    public List<Task> getTasksByEmployee(Employee employee) {
        return taskRepository.findByAssignedEmployee(employee);
    }
    
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    public Task updateTaskProgress(Long taskId, int progress) {
        Task task = getTaskById(taskId);
        if (task != null) {
            
            if (progress < 0) {
                progress = 0;
            } else if (progress > 100) {
                progress = 100;
            }
            task.setProgress(progress);
            return taskRepository.save(task);
        }
        return null;
    }
}