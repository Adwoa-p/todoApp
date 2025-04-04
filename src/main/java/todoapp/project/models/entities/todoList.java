package todoapp.project.todolist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import todoapp.project.tasks.Task;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table
@Getter
@Setter
public class todoList {

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
    private Integer todolist_id;

    private String name;

    @CreationTimestamp
    private LocalDate created_at;

    @Column(name = "updated_at", nullable = true)
    private LocalDate updated_at;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks;

    private int tasks_completed;
    private String todolist_type;

    @JsonIgnore
    private boolean isDeleted;

    @PrePersist
    protected void onUpdate() {
        this.updated_at = LocalDate.now();
    }

    public todoList() {
    }

    public todoList(String name, LocalDate created_at, LocalDate updated_at, List<Task> tasks, int tasks_completed, String todolist_type) {
        this.name = name;
        this.created_at = created_at;
        this.updated_at = null;
        this.tasks = tasks;
        this.tasks_completed = tasks_completed;
        this.todolist_type = todolist_type;
        this.isDeleted = false;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @JsonProperty("number_of_tasks")
    public int getNumber_of_tasks() {
        return tasks != null ? tasks.size() : 0;
    }

    @Override
    public String toString() {
        return "todoList{" +
                "todolist_id=" + todolist_id +
                ", name='" + name + '\'' +
                ", todolist_type='" + todolist_type + '\'' +
                ", number_of_tasks=" + getNumber_of_tasks() +
                '}';
    }
}

