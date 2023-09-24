package ru.sumarokov.task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.task_tracker.entity.Task;
import ru.sumarokov.task_tracker.entity.TaskGroup;

import java.util.List;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {
    public List<TaskGroup> findAllByOrderByIdAsc();
}
