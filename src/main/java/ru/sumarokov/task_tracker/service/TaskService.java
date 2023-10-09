package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.exception.EntityNotFoundException;
import ru.sumarokov.task_tracker.exception.NotAccessException;
import ru.sumarokov.task_tracker.repository.TaskGroupRepository;
import ru.sumarokov.task_tracker.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final AuthService authService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskGroupRepository taskGroupRepository, AuthService authService) {
        this.taskRepository = taskRepository;
        this.authService = authService;
        this.taskGroupRepository = taskGroupRepository;
    }

    public List<Task> getUserTasks() {
        return taskRepository.findByTaskGroupUserOrderByIdAsc(authService.getUser());
    }

    public Task getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (task.getTaskGroup().getUser() != authService.getUser())
            throw new NotAccessException();
        return task;
    }

    public void saveTask(Task task) {
        if (task.getId() == -1L) {
            taskRepository.save(task);
        } else {
            Task oldTask = taskRepository.findById(task.getId())
                    .orElseThrow(EntityNotFoundException::new);
            if (oldTask.getTaskGroup().getUser() != authService.getUser()) {
                throw new NotAccessException();
            } else {
                taskRepository.save(task);
            }
        }
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (task.getTaskGroup().getUser() != authService.getUser())
            throw new NotAccessException();
        taskRepository.deleteById(id);
    }
}
