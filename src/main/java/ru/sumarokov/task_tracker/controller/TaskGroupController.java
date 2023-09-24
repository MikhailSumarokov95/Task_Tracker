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
@RequestMapping("task_group")
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
        return "task_group/task_group_list";
    }

    @GetMapping("/create")
    public String getTaskGroupCreate(Model model) {
        model.addAttribute("taskGroup", new TaskGroup());
        return "task_group/task_group_create";
    }

    @PostMapping("/save_created")
    public String addTaskGroup(@ModelAttribute TaskGroup taskGroup) {
        taskGroupRepository.save(taskGroup);
        return "redirect:/task_group";
    }

    @GetMapping("/{id}")
    public String getTaskGroupUpdate(@PathVariable Long id, Model model) {
        TaskGroup taskGroup = taskGroupRepository.findById(id).orElse(null);
        model.addAttribute("taskGroup", taskGroup);

        List<Task> tasks = taskRepository.findAllByOrderByIdAsc();
        model.addAttribute("tasks", tasks);
        return "task_group/task_group_update";
    }

    @PostMapping("/save_update")
    public String updateTaskGroup(@ModelAttribute TaskGroup taskGroup) {
        taskGroupRepository.save(taskGroup);
        return "redirect:/task_group";
    }

    @GetMapping("/{id}/delete")
    public String deleteTaskGroup(@PathVariable Long id) {
        if (id != 1L) taskGroupRepository.deleteById(id);
        return "redirect:/task_group";
    }

    @GetMapping("/addTaskToGroup/{idTask}to{idGroup}")
    public String addTaskToGroup(@PathVariable Long idTask, @PathVariable Long idGroup) {
        Task task = taskRepository.findById(idTask).orElse(null);
        TaskGroup newTaskGroup = taskGroupRepository.findById(idGroup).orElse(null);
        task.setTaskGroup(newTaskGroup);
        taskRepository.save(task);
        return "redirect:/task_group/" + idGroup;
    }
}
