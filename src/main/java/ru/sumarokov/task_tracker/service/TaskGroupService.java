package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.exception.AccessDeniedException;
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

    public List<TaskGroup> getUserTaskGroups(Long id) {
        return taskGroupRepository.findByUserIdOrderByIdAsc(id);
    }

    public TaskGroup getTaskGroup(Long id) {
        return taskGroupRepository.findByIdAndUserId(id, authService.getUser().getId())
                .orElseThrow(() -> new AccessDeniedException("Недостаточно прав для данной операции"));
    }

    public void saveTaskGroup(TaskGroup taskGroup) {
        if (taskGroup.getId() != null)
            taskGroupRepository.findByIdAndUserId(taskGroup.getId(), authService.getUser().getId())
                    .orElseThrow(() -> new AccessDeniedException("Недостаточно прав для данной операции"));
        taskGroupRepository.save(taskGroup);
    }

    public void deleteTaskGroup(Long id) {
        if (taskGroupRepository.deleteByIdAndUserIdAndIsDefaultFalse(id, authService.getUser().getId()) == 0)
            throw new AccessDeniedException("Недостаточно прав для данной операции");
    }

    public void addTaskToGroup(Long taskId, Long newTaskGroupId) {
        Task task = taskService.getTask(taskId);
        Long userId = authService.getUser().getId();
        taskGroupRepository.findByIdAndUserId(task.getTaskGroup().getId(), userId)
                .orElseThrow(() -> new AccessDeniedException("Недостаточно прав для данной операции"));
        TaskGroup newTaskGroup = taskGroupRepository.findByIdAndUserId(newTaskGroupId, userId)
                .orElseThrow(() -> new AccessDeniedException("Недостаточно прав для данной операции"));

        task.setTaskGroup(newTaskGroup);
        taskService.saveTask(task);
    }
}
