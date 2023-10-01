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
    @NotEmpty(message = "{size.taskGroup.name.notNull}")
    private String name;
    @OneToMany(mappedBy = "taskGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public TaskGroup() { }

    public TaskGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setTaskGroup(this);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.setTaskGroup(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "TaskGroup{" +
                "tasks=" + tasks +
                '}';
    }
}