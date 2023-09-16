package ru.sumarokov.taskTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sumarokov.taskTracker.entity.Task;
import ru.sumarokov.taskTracker.repository.TaskRepository;

import java.time.LocalDate;

@SpringBootApplication
public class TaskTrackerApplication implements CommandLineRunner {

	@Autowired
	private TaskRepository taskRepository;

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Task task = new Task();
		task.setText("Hello World");
		task.setCompleted(true);
		task.setDateCreated(LocalDate.now());
		task.setDateDeadLine(LocalDate.now().plusDays(10));
		Task savedTask = taskRepository.save(task);
		Long id = savedTask.getId();
		System.out.println(id);

		Task taskFromDB = taskRepository.findById(id).orElse(null);
		System.out.println(taskFromDB);

		taskFromDB.setText("Two");
		taskRepository.save(taskFromDB);
	}
}
