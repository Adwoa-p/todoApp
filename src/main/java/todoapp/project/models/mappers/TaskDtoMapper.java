package todoapp.project.models.mappers;

import todoapp.project.models.dtos.TaskDto;
import todoapp.project.models.entities.Task;
import todoapp.project.models.entities.TodoList;

import java.util.Optional;

public class TaskDtoMapper {

    public static Task toTask(TaskDto taskDto, Optional<TodoList> todoList){
        return Task.builder()
                .deadline(taskDto.getDeadline())
                .priority(taskDto.getPriority())
                .description(taskDto.getDescription())
                .title(taskDto.getTitle())
                .todoList(todoList.get())
                .build();
    }
}

