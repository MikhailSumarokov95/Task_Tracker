package ru.sumarokov.task_tracker.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.repository.TaskGroupRepository;
import ru.sumarokov.task_tracker.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("task")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskGroupRepository taskGroupRepository) {
        this.taskRepository = taskRepository;
        this.taskGroupRepository = taskGroupRepository;
    }

    @GetMapping()
    public String getTaskList(Model model) {
        List<Task> tasks = taskRepository.findAllByOrderByIdAsc();
        model.addAttribute("tasks", tasks);
        return "task/list";
    }

    @GetMapping("/{id}")
    public String getTaskForm(@PathVariable Long id, Model model) {
        Task newTask = new Task(LocalDate.now());
        Task task = taskRepository.findById(id).orElse(newTask);
        model.addAttribute("task", task);

        List<TaskGroup> taskGroups = taskGroupRepository.findAllByOrderByIdAsc();
        model.addAttribute("taskGroups", taskGroups);
        return "task/form";
    }

    @GetMapping("/create")
    public String getTaskCreate() {
        return "redirect:/task/-1";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/task";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/task";
    }
}
