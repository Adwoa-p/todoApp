package todoapp.project.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import todoapp.project.tasks.Task;

import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
@Builder
public class TodoListDto {

    private String name;
    private LocalDate created_at;
    private LocalDate updated_at;
    private List<Task> tasks;
    private int tasks_completed;
    private String todolist_type;

}
