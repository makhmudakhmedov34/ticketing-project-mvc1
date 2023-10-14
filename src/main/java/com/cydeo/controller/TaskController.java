package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task){

        taskService.update(task);

        return "redirect:/task/create";
    }



}
