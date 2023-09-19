package ru.sumarokov.taskTracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate dateCreated;
    private LocalDate dateDeadLine;
    private String text;
    private boolean isCompleted;
    private String nameGroup;

    public Task() {}

    public Task(Long id, LocalDate dateCreated, LocalDate dateDeadLine, String text, boolean isCompleted, String nameGroup) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateDeadLine = dateDeadLine;
        this.text = text;
        this.isCompleted = isCompleted;
        this.nameGroup = nameGroup;
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

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", dateDeadLine=" + dateDeadLine +
                ", text='" + text + '\'' +
                ", isCompleted=" + isCompleted +
                ", nameGroup='" + nameGroup + '\'' +
                '}';
    }
}
