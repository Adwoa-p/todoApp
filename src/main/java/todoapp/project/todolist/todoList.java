package todoapp.project.todolist;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table
public class todoList {

    @Id
    @SequenceGenerator(
            name = "todo_sequence",
            sequenceName = "todo_sequence",
            allocationSize =1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todo_sequence"
    )
    private Integer todolist_id;
    private String name;

    @CreationTimestamp
    private LocalDate created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private LocalDate updated_at;
    private int number_of_tasks;
    private int tasks_completed;
    private String todolist_type;

    public todoList() {
    }

    public todoList(Integer todolist_id, String name, LocalDate created_at, LocalDate updated_at, int number_of_tasks, int tasks_completed, String todolist_type) {
        this.todolist_id = todolist_id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.number_of_tasks = number_of_tasks;
        this.tasks_completed = tasks_completed;
        this.todolist_type = todolist_type;
    }

    public todoList(String name, LocalDate created_at, LocalDate updated_at, int number_of_tasks, int tasks_completed, String todolist_type) {
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.number_of_tasks = number_of_tasks;
        this.tasks_completed = tasks_completed;
        this.todolist_type = todolist_type;
    }

    public int getTodolist_id() {
        return todolist_id;
    }

    public void setTodolist_id(int todolist_id) {
        this.todolist_id = todolist_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public int getNumber_of_tasks() {
        return number_of_tasks;
    }

    public void setNumber_of_tasks(int number_of_tasks) {
        this.number_of_tasks = number_of_tasks;
    }

    public int getTasks_completed() {
        return tasks_completed;
    }

    public void setTasks_completed(int tasks_completed) {
        this.tasks_completed = tasks_completed;
    }

    public String getTodolist_type() {
        return todolist_type;
    }

    public void setTodolist_type(String todolist_type) {
        this.todolist_type = todolist_type;
    }

    @Override
    public String toString() {
        return "todoList{" +
                "todolist_id=" + todolist_id +
                ", name='" + name + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", number_of_tasks=" + number_of_tasks +
                ", tasks_completed=" + tasks_completed +
                ", todolist_type='" + todolist_type + '\'' +
                '}';
    }
}
