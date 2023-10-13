package ru.sumarokov.task_tracker.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.entity.User;
import ru.sumarokov.task_tracker.service.AuthService;
import ru.sumarokov.task_tracker.service.TaskGroupService;
import ru.sumarokov.task_tracker.service.TaskService;

@Controller
@RequestMapping("task-group")
public class TaskGroupController {

    private final TaskService taskService;
    private final TaskGroupService taskGroupService;
    private final AuthService authService;

    @Autowired
    public TaskGroupController(TaskService taskService, TaskGroupService taskGroupService, AuthService authService) {
        this.taskService = taskService;
        this.taskGroupService = taskGroupService;
        this.authService = authService;
    }

    @GetMapping()
    public String getTaskGroup(Model model) {
        User user = authService.getUser();
        model.addAttribute("taskGroups",taskGroupService.getUserTaskGroups(user.getId()));
        return "task_group/list";
    }

    @GetMapping("/{id}")
    public String getTaskGroupForm(@PathVariable Long id, Model model) {
        model.addAttribute("taskGroup", taskGroupService.getTaskGroup(id));
        User user = authService.getUser();
        model.addAttribute("tasks", taskService.getUserTasks(user.getId()));
        return "task_group/form";
    }

    @GetMapping("/create")
    public String getTaskGroupCreate(Model model) {
        User user = authService.getUser();
        model.addAttribute("taskGroup", new TaskGroup(user));
        model.addAttribute("tasks", taskService.getUserTasks(user.getId()));
        return "task_group/form";
    }

    @PostMapping("/save")
    public String saveTaskGroup(@ModelAttribute @Valid TaskGroup taskGroup, BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            User user = authService.getUser();
            model.addAttribute("tasks", taskService.getUserTasks(user.getId()));
            return "task_group/form";
        }
        taskGroupService.saveTaskGroup(taskGroup);
        return "redirect:/task-group";
    }

    @GetMapping("/{id}/delete")
    public String deleteTaskGroup(@PathVariable Long id) {
        taskGroupService.deleteTaskGroup(id);
        return "redirect:/task-group";
    }

    @GetMapping("/{taskGroupId}/add-task/{taskId}")
    public String addTaskToGroup(@PathVariable Long taskId, @PathVariable Long taskGroupId) {
        taskGroupService.addTaskToGroup(taskId, taskGroupId);
        return "redirect:/task-group/" + taskGroupId;
    }
}
