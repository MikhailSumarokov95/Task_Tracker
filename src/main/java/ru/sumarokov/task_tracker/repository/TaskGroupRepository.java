package ru.sumarokov.task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.task_tracker.entity.TaskGroup;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

}
