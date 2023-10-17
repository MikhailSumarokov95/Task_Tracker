package ru.sumarokov.task_tracker.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.sumarokov.task_tracker.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTaskGroupUserIdOrderByIdAsc(Long user_id);
    List<Task> findByTaskGroupUserIdOrderByIsCompletedAscDateDeadLineAsc(Long user_id);
    Optional<Task> findByIdAndTaskGroupUserId(Long id,Long user_id);
    @Transactional
    Integer deleteByIdAndTaskGroupUserId(Long id, Long user_id);
}
