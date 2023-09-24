package ru.sumarokov.task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.task_tracker.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByOrderByIdAsc();
}
