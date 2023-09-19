package ru.sumarokov.taskTracker.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.taskTracker.entity.Task;
import ru.sumarokov.taskTracker.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("task")
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping()
    public String getTaskList(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "task/task_list";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).orElse(null);
        model.addAttribute("task", task);
        return "task/task_update";
    }

    @GetMapping("/new")
    public String getTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task/task_create";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {
        task.setDateCreated(LocalDate.now());
        taskRepository.save(task);
        return "redirect:/task";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/task";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/task";
    }
}
