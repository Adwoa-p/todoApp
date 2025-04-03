package todoapp.project.tasks;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import todoapp.project.todolist.todoList;
import todoapp.project.tasks.enums.Priority;
import todoapp.project.tasks.enums.Status;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Task {

    @Id
    @SequenceGenerator(
            name = "taskSequence",
            sequenceName = "taskSequence",
            allocationSize =1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "taskSequence"
    )
    private Integer taskId;

    private String title;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "deadline", nullable = true)
    private LocalDateTime deadline;
    @Column(name = "dateCompleted", nullable = true)
    private LocalDateTime completedDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(nullable = true)
    @JsonBackReference
    private todoList todoList;

    private boolean isDeleted;

    public Task() {
    }

    public Task(Integer taskId,String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deadline, LocalDateTime completedDate, Priority priority, Status status) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deadline = deadline;
        this.completedDate = completedDate;
        this.priority = priority;
        this.status = status;
        this.isDeleted = false;
    }

    public Task(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deadline, LocalDateTime completedDate, Priority priority, Status status) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deadline = deadline;
        this.completedDate = completedDate;
        this.priority = priority;
        this.status = status;
        this.isDeleted = false;
    }

    @Override
    public String toString() {
        return "Task{" +
                ", title='" + title + "->" +
                ", status=" + status +
                '}';
    }

}
