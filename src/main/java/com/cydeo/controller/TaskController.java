package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {

    TaskService taskService;
    UserService userService;
    ProjectService projectService;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createTask(Model model){
        model.addAttribute("task",new TaskDTO());
        model.addAttribute("assignedEmployees",userService.findEmployee());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("tasks",taskService.findAll());
        return "/task/create";
    }

    @PostMapping("/create")
    public String saveTask(TaskDTO task){
        taskService.save(task);
        return "redirect:/task/create";
    }

    @GetMapping("/delete/{taskid}")
    public String deleteTask(@PathVariable("taskid") Long taskid){
        taskService.deleteById(taskid);
        return "redirect:/task/create";
    }

    @GetMapping("/update/{taskid}")
    public String editTask(@PathVariable("taskid") Long taskid,Model model){
        model.addAttribute("task",taskService.findById(taskid));
        model.addAttribute("assignedEmployees",userService.findEmployee());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("tasks",taskService.findAll());
        return "/task/update";
    }

    //    @PostMapping("/update/{taskId}")
//    public String updateTask(@PathVariable("taskId") Long taskId, TaskDTO task) {
//        task.setId(taskId);
//        taskService.update(task);
//        return "redirect:/task/create";
//    }

    @PostMapping("/update/{id}")
    public String updateTask(@ModelAttribute("task") TaskDTO task){
        taskService.update(task);
        return "redirect:/task/create";
    }

    @GetMapping("/employee/pending-tasks")
    public String getPendingTask(Model model){
        model.addAttribute("pendingTaskList",taskService.findAllPendingTasks());
        return "task/pending-tasks";
    }

    @GetMapping("/employee/status-update/{id}")
    public String getPendingTask(@PathVariable("id") Long id, Model model){
        model.addAttribute("task",taskService.findById(id));
        model.addAttribute("tasks",taskService.findAllPendingTasks());
        model.addAttribute("statuses", Status.values());

        return "task/status-update";
    }

    @PostMapping("/employee/status/update/{id}")
    public String updatePendingTask(@ModelAttribute("task") TaskDTO task){
        taskService.updateStatus(task);

        return "redirect:/task/employee/pending-tasks";
    }





}
