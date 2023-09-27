package ru.sumarokov.task_tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.repository.TaskGroupRepository;

@SpringBootApplication
public class TaskTrackerApplication implements CommandLineRunner {

	private final TaskGroupRepository taskGroupRepository;

	@Autowired
	TaskTrackerApplication(TaskGroupRepository taskGroupRepository) {
		this.taskGroupRepository = taskGroupRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (taskGroupRepository.findById(1L).orElse(null) == null)
		{
			TaskGroup taskGroup = new TaskGroup(1L, "Default");
			taskGroupRepository.save(taskGroup);
		}
	}
}
