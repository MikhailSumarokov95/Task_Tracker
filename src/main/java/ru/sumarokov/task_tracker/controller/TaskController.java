package ru.sumarokov.task_tracker.controller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.User;
import ru.sumarokov.task_tracker.exception.AccessDeniedException;
import ru.sumarokov.task_tracker.service.AuthService;
import ru.sumarokov.task_tracker.service.TaskGroupService;
import ru.sumarokov.task_tracker.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;
    private final TaskGroupService taskGroupService;
    private final AuthService authService;

    @Autowired
    public TaskController(TaskService taskService, TaskGroupService taskGroupService, AuthService authService) {
        this.taskService = taskService;
        this.taskGroupService = taskGroupService;
        this.authService = authService;
    }

    @GetMapping()
    public String getTaskList(Model model) {
        User user = authService.getUser();
        List<Task> tasks = taskService.getUserTasks(user.getId());
        model.addAttribute("tasks", tasks);
        return "task/list";
    }

    @GetMapping("/{id}")
    public String getTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTask(id));
        User user = authService.getUser();
        model.addAttribute("taskGroups", taskGroupService.getUserTaskGroups(user.getId()));
        return "task/form";
    }

    @GetMapping("/create")
    public String getTaskCreate(Model model) {
        model.addAttribute("task", new Task());
        User user = authService.getUser();
        model.addAttribute("taskGroups", taskGroupService.getUserTaskGroups(user.getId()));
        return "task/form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute @Valid Task task, BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            User user = authService.getUser();
            model.addAttribute("taskGroups", taskGroupService.getUserTaskGroups(user.getId()));
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

    @GetMapping("/{id}/complete")
    public String completeTask(@PathVariable Long id) {
        taskService.completeTask(id);
        return "redirect:/task";
    }

    @ExceptionHandler({AccessDeniedException.class})
    public String handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return "exception/accessDenied";
    }
}