

package com.company.employeemanagement.repository;

import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByTaskNumber(String taskNumber);
    List<Task> findByTaskNameContaining(String taskName);
    List<Task> findByAssignedEmployee(Employee employee);
    List<Task> findByAssignedEmployeeOrderByCreatedAtDesc(Employee employee);
    List<Task> findByStatus(String status);
}