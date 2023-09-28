package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.exception.EntityNotFoundException;
import ru.sumarokov.task_tracker.repository.TaskGroupRepository;

import java.util.List;

@Service
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;

    private final TaskService taskService;

    @Autowired
    public TaskGroupService(TaskGroupRepository taskGroupRepository, TaskService taskService) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskService = taskService;
    }

    public List<TaskGroup> getTaskGroups() {
        return taskGroupRepository.findAllByOrderByIdAsc();
    }

    public TaskGroup getTaskGroup(Long id) {
        return taskGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public TaskGroup saveTaskGroup(TaskGroup taskGroup) {
        return taskGroupRepository.save(taskGroup);
    }

    public void deleteTaskGroup(Long id) {
        if (id != 1L) taskGroupRepository.deleteById(id);
    }

    public void addTaskToGroup(Long taskId, Long taskGroupId) {
        Task task = taskService.getTask(taskId);
        TaskGroup newTaskGroup = taskGroupRepository.findById(taskGroupId).orElseThrow(EntityNotFoundException::new);
        task.setTaskGroup(newTaskGroup);
        taskService.saveTask(task);
    }
}
