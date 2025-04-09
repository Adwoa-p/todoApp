package todoapp.project.models.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import todoapp.project.enums.Priority;
import todoapp.project.enums.Status;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Builder
public class TaskDto {

    private String title;
    private String description;
    private Integer todoListId;
    private LocalDateTime deadline;
    private Priority priority;
    private Status status;

}
