package ru.sumarokov.task_tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate dateCreated;
    private LocalDate dateDeadLine;
    @NotEmpty(message = "{size.task.text.notNull}")
    private String text;
    private boolean isCompleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_group_id", nullable = false)
    private TaskGroup taskGroup;

    public Task() {
        dateCreated = LocalDate.now();
        this.id = -1L;
    }

    public Task(Long id, LocalDate dateCreated, LocalDate dateDeadLine, String text, boolean isCompleted, String nameGroup) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateDeadLine = dateDeadLine;
        this.text = text;
        this.isCompleted = isCompleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateDeadLine() {
        return dateDeadLine;
    }

    public void setDateDeadLine(LocalDate dateDeadLine) {
        this.dateDeadLine = dateDeadLine;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }

    public TaskGroup getTaskGroup() { return taskGroup; }

    public void setTaskGroup(TaskGroup taskGroup) { this.taskGroup = taskGroup; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", dateDeadLine=" + dateDeadLine +
                ", text='" + text + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
