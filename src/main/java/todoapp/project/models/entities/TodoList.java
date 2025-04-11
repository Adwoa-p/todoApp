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
import org.hibernate.annotations.Where;
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

    @OneToMany(mappedBy = "todoList",cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "is_deleted = false")
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
        return (int) tasks.stream().filter(task -> !task.isDeleted()).count();
    }

    @JsonProperty("NumberOfTasksCompleted")
    public int getNumberOfTasksCompleted() {
        if (tasks == null) return 0;
        int count = 0;
        for (Task task:tasks){
            if (task.getStatus()== Status.COMPLETED && !task.isDeleted()){
                count+=1;
            }
        }
        return count;
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

