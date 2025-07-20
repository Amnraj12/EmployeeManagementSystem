package com.company.employeemanagement.service;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.model.RankingRecord;
import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.repository.TaskRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

    @Autowired
    private TaskRepository taskRepository;
    
    public List<RankingRecord> getEmployeeRankings(String sortBy) {
        List<Task> allTasks = taskRepository.findAll();
        System.out.println("Total tasks in system: " + allTasks.size());
        
        Set<String> statuses = allTasks.stream()
            .map(Task::getStatus)
            .filter(status -> status != null)
            .collect(Collectors.toSet());
        System.out.println("Task statuses found: " + statuses);
        
        List<Task> completedTasks = new ArrayList<>();
        completedTasks.addAll(taskRepository.findByStatus("Reviewed"));
        completedTasks.addAll(taskRepository.findByStatus("Completed"));
        completedTasks.addAll(taskRepository.findByStatus("Done"));
        completedTasks.addAll(taskRepository.findByStatus("Finished"));
        
        System.out.println("Completed/Reviewed tasks found: " + completedTasks.size());
        
        Map<Employee, RankingRecord> rankingMap = new HashMap<>();

        for (Task task : completedTasks) {
            Employee emp = task.getAssignedEmployee();
            if (emp == null) {
                System.out.println("Task " + task.getId() + " has no assigned employee");
                continue;
            }
            
            RankingRecord record = rankingMap.getOrDefault(emp, new RankingRecord(emp, 0, 0, 0));
            
            int points;
            try {
                points = task.getPoints();
                if (points <= 0) {
                    points = 1;
                }
            } catch (Exception e) {
                System.out.println("Error getting points from task " + task.getId() + ": " + e.getMessage());
                points = 1;
            }
            record.setTotalPoints(record.getTotalPoints() + points);
            
            try {
                String priority = task.getPriority();
                if (priority != null && (priority.equalsIgnoreCase("High") || 
                                         priority.equalsIgnoreCase("Critical") || 
                                         priority.equals("1"))) {
                    record.setPriorityCount(record.getPriorityCount() + 1);
                }
            } catch (Exception e) {
                System.out.println("Error getting priority from task " + task.getId() + ": " + e.getMessage());
            }
            
            try {
                int hours = task.getHoursAllocated();
                record.setHoursSpent(record.getHoursSpent() + hours);
            } catch (Exception e) {
                System.out.println("Error getting hours from task " + task.getId() + ": " + e.getMessage());
            }
            
            rankingMap.put(emp, record);
        }
        
        List<RankingRecord> rankings = new ArrayList<>(rankingMap.values());
        System.out.println("Number of employees with rankings: " + rankings.size());
        
        if ("hours".equals(sortBy)) {
            rankings.sort(Comparator.comparing(RankingRecord::getHoursSpent).reversed());
        } else if ("priority".equals(sortBy)) {
            rankings.sort(Comparator.comparing(RankingRecord::getPriorityCount).reversed());
        } else {
            // Default to points
            rankings.sort(Comparator.comparing(RankingRecord::getTotalPoints).reversed());
        }
        
        return rankings;
    }
    
    public List<RankingRecord> getEmployeeRankings() {
        return getEmployeeRankings("points");
    }
}