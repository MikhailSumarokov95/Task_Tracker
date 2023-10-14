package ru.sumarokov.task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sumarokov.task_tracker.entity.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

    List<TaskGroup> findByUserIdOrderByIdAsc(Long user_id);
    Optional<TaskGroup> findByIdAndUserId(Long id, Long user_id);
    @Transactional
    Integer deleteByIdAndUserIdAndIsDefaultFalse(Long id, Long user_id);
}
