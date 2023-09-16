package ru.sumarokov.taskTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.taskTracker.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByNameGroup(String nameGroup);
}
