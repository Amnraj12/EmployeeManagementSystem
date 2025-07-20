package com.company.employeemanagement.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskNumber;
    private String taskName;
    private String description;
    private String priority;   // High, Medium, Low
    private int points;
    private int hoursAllocated;
    
    private String status;
    private boolean isTeamTask;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; // Added updatedAt field

    @ManyToOne
    private Employee assignedEmployee;

    private String adminComment;
    private String employeeComment;
    
    private int progress;
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTaskNumber() { return taskNumber; }
    public void setTaskNumber(String taskNumber) { this.taskNumber = taskNumber; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public int getHoursAllocated() { return hoursAllocated; }
    public void setHoursAllocated(int hoursAllocated) { this.hoursAllocated = hoursAllocated; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isTeamTask() { return isTeamTask; }
    public void setTeamTask(boolean teamTask) { isTeamTask = teamTask; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Employee getAssignedEmployee() { return assignedEmployee; }
    public void setAssignedEmployee(Employee assignedEmployee) { this.assignedEmployee = assignedEmployee; }

    public String getAdminComment() { return adminComment; }
    public void setAdminComment(String adminComment) { this.adminComment = adminComment; }

    public String getEmployeeComment() { return employeeComment; }
    public void setEmployeeComment(String employeeComment) { this.employeeComment = employeeComment; }
    
     public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
}