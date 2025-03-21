package todoapp.project.tasks;

import todoapp.project.tasks.enums.Priority;
import todoapp.project.tasks.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private Integer taskId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deadline;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;

    public Task() {
    }

    public Task(Integer taskId,String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deadline, LocalDate dueDate, Priority priority, Status status) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deadline = deadline;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public Task(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deadline, LocalDate dueDate, Priority priority, Status status) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deadline = deadline;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    @Override
    public String toString() {
        return "Task{" +
                ", title='" + title + "->" +
                ", status=" + status +
                '}';
    }
}
