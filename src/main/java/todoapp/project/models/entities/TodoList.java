package todoapp.project.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import todoapp.project.enums.Status;
import todoapp.project.enums.TodoListType;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoList {

    @Id
    @SequenceGenerator(
            name = "todo_sequence",
            sequenceName = "todo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todo_sequence"
    )
    private Integer todoListId;

    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks;

    @Enumerated(EnumType.STRING)
    private TodoListType todolist_type;

    @JsonIgnore
    private boolean isDeleted;

    @PrePersist
    protected void onCreate(){
        System.out.println("onCreate triggered");
        this.setUpdatedAt(null);
    }

    @PreUpdate
    protected void onUpdate(){
        System.out.println("onUpdate triggered");
        this.setUpdatedAt(LocalDateTime.now());
    }

    @JsonProperty("number_of_tasks")
    public int getNumber_of_tasks() {
        return tasks != null ? tasks.size() : 0;
    }

    @JsonProperty("NumberOfTasksCompleted")
    public int getNumberOfTasksCompleted() {
        if (tasks == null) return 0;
        return (int) tasks.stream().filter(task -> task.getStatus() == Status.COMPLETED).count();
    }

    @Override
    public String toString() {
        return "todoList{" +
                "todolist_id=" + todoListId +
                ", name='" + name + '\'' +
                ", todolist_type='" + todolist_type + '\'' +
                ", number_of_tasks=" + getNumber_of_tasks() +
                '}';
    }
}

