package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.exception.AccessDeniedException;
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
         return taskRepository.findByIdAndTaskGroupUserId(id, authService.getUser().getId())
                .orElseThrow(() -> new AccessDeniedException("Недостаточно прав для данной операции"));
    }

    public void saveTask(Task task) {
        if (task.getId() != null)
            taskRepository.findByIdAndTaskGroupUserId(task.getId(), authService.getUser().getId())
                    .orElseThrow(() -> new AccessDeniedException("Недостаточно прав для данной операции"));
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (taskRepository.deleteByIdAndTaskGroupUserId(id, authService.getUser().getId()) == 0)
            throw new AccessDeniedException("Недостаточно прав для данной операции");
    }
}
