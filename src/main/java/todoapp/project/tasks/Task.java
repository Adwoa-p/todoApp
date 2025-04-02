package todoapp.project.tasks;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import todoapp.project.todolist.todoList;
import todoapp.project.tasks.enums.Priority;
import todoapp.project.tasks.enums.Status;
import java.time.LocalDateTime;

@Entity
@Table
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
    @Column(name = "updatedAt", nullable = true) // trying prepersist on update and set to localdatetime.now() and see if it works
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getTodoListId() {
        return this.todoList != null ? this.todoList.getTodolist_id() : null;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Task{" +
                ", title='" + title + "->" +
                ", status=" + status +
                '}';
    }
}
