package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.exception.EntityNotFoundException;
import ru.sumarokov.task_tracker.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAllByOrderByIdAsc();
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
