package ru.sumarokov.task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.entity.User;

import java.util.List;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

    List<TaskGroup> findByUserIdOrderByIdAsc(Long user_id);
}
