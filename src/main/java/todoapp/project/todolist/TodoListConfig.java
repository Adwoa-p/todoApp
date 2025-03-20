package todoapp.project.todolist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
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
                    5,
                    2,
                    "Work"
            );

            todoList list2 =  new todoList(
                    "Basic Tasks",
                    LocalDate.of(2025,3,19),
                    LocalDate.of(2025,3,19),
                    10,
                    5,
                    "Home"
            );

            repository.saveAll(List.of(list1,list2));

        };
    }
}
