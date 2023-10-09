package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.exception.EntityNotFoundException;
import ru.sumarokov.task_tracker.exception.NotAccessException;
import ru.sumarokov.task_tracker.repository.TaskGroupRepository;

import java.util.List;

@Service
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskService taskService;
    private final AuthService authService;

    @Autowired
    public TaskGroupService(TaskGroupRepository taskGroupRepository, TaskService taskService, AuthService authService) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskService = taskService;
        this.authService = authService;
    }

    public List<TaskGroup> getUserTaskGroups() {
        return taskGroupRepository.findByUserOrderByIdAsc(authService.getUser());
    }

    public TaskGroup getTaskGroup(Long id) {
        TaskGroup taskGroup = taskGroupRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (taskGroup.getUser() != authService.getUser()) throw new NotAccessException();
        return taskGroup;
    }

    public void saveTaskGroup(TaskGroup taskGroup) {
        if (taskGroup.getId() == -1L) {
            taskGroup.setUser(authService.getUser());
            taskGroupRepository.save(taskGroup);
        } else {
            TaskGroup oldTask = taskGroupRepository.findById(taskGroup.getId())
                    .orElseThrow(EntityNotFoundException::new);
            if (oldTask.getUser() != authService.getUser()) {
                throw new NotAccessException();
            } else {
                taskGroupRepository.save(taskGroup);
            }
        }
    }

    public void deleteTaskGroup(Long id)  {
        TaskGroup taskGroup = taskGroupRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (taskGroup.isDefault() || taskGroup.getUser() != authService.getUser())
            throw new NotAccessException();
        else taskGroupRepository.deleteById(id);
    }

    public void addTaskToGroup(Long taskId, Long taskGroupId) {
        Task task = taskService.getTask(taskId);
        TaskGroup oldTaskGroup = taskGroupRepository.findById(task.getTaskGroup().getId())
                .orElseThrow(EntityNotFoundException::new);
        TaskGroup newTaskGroup = taskGroupRepository.findById(taskGroupId)
                .orElseThrow(EntityNotFoundException::new);

        if (newTaskGroup.getUser() != authService.getUser() || oldTaskGroup.getUser() != authService.getUser())
            throw new NotAccessException();

        task.setTaskGroup(newTaskGroup);
        taskService.saveTask(task);
    }
}
