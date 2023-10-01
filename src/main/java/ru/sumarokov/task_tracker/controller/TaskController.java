package ru.sumarokov.task_tracker.controller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.service.TaskGroupService;
import ru.sumarokov.task_tracker.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;
    private final TaskGroupService taskGroupService;

    @Autowired
    public TaskController(TaskService taskService, TaskGroupService taskGroupService) {
        this.taskService = taskService;
        this.taskGroupService = taskGroupService;
    }

    @GetMapping()
    public String getTaskList(Model model) {
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "task/list";
    }

    @GetMapping("/{id}")
    public String getTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTask(id));
        model.addAttribute("taskGroups", taskGroupService.getTaskGroups());
        return "task/form";
    }

    @GetMapping("/create")
    public String getTaskCreate(Model model) {
        model.addAttribute("task", taskService.getEmptyTask());
        model.addAttribute("taskGroups", taskGroupService.getTaskGroups());
        return "task/form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute @Valid Task task, BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("taskGroups", taskGroupService.getTaskGroups());
            return "task/form";
        }
        taskService.saveTask(task);
        return "redirect:/task";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/task";
    }
}