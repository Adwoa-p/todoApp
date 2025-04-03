package todoapp.project.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import todoapp.project.tasks.enums.Priority;
import todoapp.project.tasks.enums.Status;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Builder
public class TaskDto {

    private String title;
    private String description;
    private Integer todoListId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deadline;
    private LocalDateTime completedDate;
    private Priority priority;
    private Status status;

}
