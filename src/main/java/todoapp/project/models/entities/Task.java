package todoapp.project.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import todoapp.project.enums.Priority;
import todoapp.project.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
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

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "deadline", nullable = true)
    private LocalDateTime deadline;
    
    @Column(name = "date_completed", nullable = true)
    private LocalDateTime completedDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(nullable = true)
    @JsonBackReference
    private TodoList todoList;

    @JsonIgnore
    private boolean isDeleted;

    public Task() {
    }

    @PrePersist
    protected void onCreate(){
        System.out.println("onCreate triggered");
        this.setStatus(Status.PENDING);
        this.setUpdatedAt(null);
    }

    @PreUpdate
    protected void onUpdate(){
        System.out.println("onUpdate triggered");
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Integer getTodoListId() {
        return this.todoList != null ? this.todoList.getTodoListId() : null;
    }

    public void setDeleted(boolean isDeleted) {
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
