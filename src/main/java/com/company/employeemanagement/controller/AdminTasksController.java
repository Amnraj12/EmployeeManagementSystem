package com.company.employeemanagement.controller;

import com.company.employeemanagement.model.Task;
import com.company.employeemanagement.service.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tasks")
public class AdminTasksController {

   @Autowired
   private TaskService taskService;

   @GetMapping
   public String listTasks(Model model){
       model.addAttribute("tasks", taskService.getAllTasks());
       return "admin/tasks"; // view to list tasks
   }

   @GetMapping("/add")
   public String addTaskForm(Model model){
       model.addAttribute("task", new Task());
       return "admin/addTask"; // view for adding a new task
   }

   @PostMapping("/save")
   public String saveTask(@ModelAttribute Task task){
       taskService.createTask(task);
       return "redirect:/admin/tasks";
   }

   @GetMapping("/edit/{id}")
   public String editTaskForm(@PathVariable Long id, Model model){
       Task task = taskService.getTaskById(id);
       model.addAttribute("task", task);
       return "admin/editTask"; // view for editing a task
   }

   @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        Task oldTask = taskService.getTaskById(task.getId());
        if (task.getStatus() == null && oldTask != null) {
            task.setStatus(oldTask.getStatus());
        }
        taskService.updateTask(task);
        return "redirect:/admin/tasks";
    }
    @GetMapping("/assigned")
    public String listAssignedTasks(Model model) {
        // Filter tasks where assignedEmployee is not null.
        List<Task> allTasks = taskService.getAllTasks();
        model.addAttribute("tasks", allTasks);
        return "admin/assignedTasks";
    }
    @PostMapping("/comment")
    public String addComment(@RequestParam Long taskId, @RequestParam String comment){
        taskService.addAdminComment(taskId, comment);
        return "redirect:/admin/tasks/assigned";
    }
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/admin/tasks";
    }


}