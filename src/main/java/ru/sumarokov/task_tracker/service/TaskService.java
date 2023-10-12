package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.exception.EntityNotFoundException;
import ru.sumarokov.task_tracker.exception.AccesDeniedException;
import ru.sumarokov.task_tracker.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AuthService authService;

    @Autowired
    public TaskService(TaskRepository taskRepository, AuthService authService) {
        this.taskRepository = taskRepository;
        this.authService = authService;
    }

    public List<Task> getUserTasks(Long id) {
        return taskRepository.findByTaskGroupUserIdOrderByIdAsc(id);
    }

    public Task getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (task.getTaskGroup().getUser() != authService.getUser())
            throw new AccesDeniedException();
        return task;
    }

    public void saveTask(Task task) {
        if (task.getId() == -1L) {
            taskRepository.save(task);
        } else {
            Task oldTask = taskRepository.findById(task.getId())
                    .orElseThrow(EntityNotFoundException::new);
            if (oldTask.getTaskGroup().getUser() != authService.getUser()) {
                throw new AccesDeniedException();
            } else {
                taskRepository.save(task);
            }
        }
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (task.getTaskGroup().getUser() != authService.getUser())
            throw new AccesDeniedException();
        taskRepository.deleteById(id);
    }
}
