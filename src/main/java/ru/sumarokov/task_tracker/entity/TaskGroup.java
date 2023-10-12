package ru.sumarokov.task_tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="taskGroup")
public class TaskGroup {

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message = "Поле \"Имя\" должно быть заполнено")
    private String name;
    @OneToMany(mappedBy = "taskGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="users_id", nullable = false)
    private User user;
    private boolean isDefault;

    public TaskGroup() {
        this.id = -1L;
    }

    public TaskGroup(String name, User user, Boolean isDefault) {
        this.name = name;
        this.user = user;
        this.isDefault = isDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getCountTasks() { return tasks.size(); }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public boolean isDefault() { return isDefault; }

    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }

    @Override
    public String toString() {
        return "TaskGroup{" +
                "tasks=" + tasks +
                '}';
    }
}