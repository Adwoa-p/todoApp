package todoapp.project.todolist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import todoapp.project.tasks.Task;
import todoapp.project.tasks.enums.Status;
import todoapp.project.tasks.enums.Priority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class TodoListConfig {

    @Bean
    CommandLineRunner runner(TodoListRepository repository){
        return args -> {
            todoList list1 =  new todoList(
                    "Work Tasks",
                    LocalDate.of(2025,3,24),
                    LocalDate.of(2025,3,25),
                    List.of(
                            new Task("Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(1), null, Priority.High, Status.PENDING),
                            new Task("Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(2), null, Priority.Medium, Status.PENDING)
                    ),
                    2,
                    "Work"
            );

            todoList list2 =  new todoList(
                    "Basic Tasks",
                    LocalDate.of(2025,3,19),
                    LocalDate.of(2025,3,19),
                    List.of(
                            new Task("Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(2), null, Priority.Medium, Status.PENDING)
                    ),
                    5,
                    "Home"
            );

            repository.saveAll(List.of(list1,list2));

        };
    }
}
