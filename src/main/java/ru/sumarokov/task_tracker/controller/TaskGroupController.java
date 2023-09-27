package ru.sumarokov.task_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.repository.TaskGroupRepository;
import ru.sumarokov.task_tracker.repository.TaskRepository;

import java.util.List;

@Controller
@RequestMapping("task-group")
public class TaskGroupController {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskRepository taskRepository;

    public TaskGroupController(TaskGroupRepository taskGroupRepository, TaskRepository taskRepository) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping()
    public String getTaskGroup(Model model) {
        List<TaskGroup> taskGroups = taskGroupRepository.findAllByOrderByIdAsc();
        model.addAttribute("taskGroups", taskGroups);
        return "task_group/list";
    }

    @GetMapping("/{id}")
    public String getTaskGroupForm(@PathVariable Long id, Model model) {
        TaskGroup taskGroup = taskGroupRepository.findById(id).orElse(new TaskGroup(-1L, ""));
        model.addAttribute("taskGroup", taskGroup);

        List<Task> tasks = taskRepository.findAllByOrderByIdAsc();
        model.addAttribute("tasks", tasks);
        return "task_group/form";
    }

    @GetMapping("/create")
    public String getTaskGroupCreate(Model model) {
        return "redirect:/task-group/-1";
    }

    @PostMapping("/save")
    public String saveTaskGroup(@ModelAttribute TaskGroup taskGroup) {
        TaskGroup savedTaskGroup = taskGroupRepository.save(taskGroup);
        return "redirect:/task-group/" + savedTaskGroup.getId();
    }

    @GetMapping("/{id}/delete")
    public String deleteTaskGroup(@PathVariable Long id) {
        if (id != 1L) taskGroupRepository.deleteById(id);
        return "redirect:/task-group";
    }

    @GetMapping("/{taskGroupId}/add-task/{taskId}")
    public String addTaskToGroup(@PathVariable Long taskId, @PathVariable Long taskGroupId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        TaskGroup newTaskGroup = taskGroupRepository.findById(taskGroupId).orElseThrow();
        task.setTaskGroup(newTaskGroup);
        taskRepository.save(task);
        return "redirect:/task-group/" + taskGroupId;
    }
}
