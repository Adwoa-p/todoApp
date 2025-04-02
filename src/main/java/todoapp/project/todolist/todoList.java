package todoapp.project.todolist;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import todoapp.project.tasks.Task;

import java.time.LocalDate;
import java.util.List;

//@Entity
//@Table
//public class todoList {
//
//    @Id
//    @SequenceGenerator(
//            name = "todo_sequence",
//            sequenceName = "todo_sequence",
//            allocationSize =1
//    )
//
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "todo_sequence"
//    )
//    private Integer todolist_id;
//    private String name;
//
//    @CreationTimestamp
//    private LocalDate created_at;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at", nullable = true)
//    private LocalDate updated_at;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Task> tasks;
//
//    @Transient
//    private int number_of_tasks;
//
//    private int tasks_completed;
//    private String todolist_type;
//
//    public todoList() {
//    }
//
//    public todoList(Integer todolist_id, String name, LocalDate created_at, LocalDate updated_at, List<Task> tasks, int number_of_tasks, int tasks_completed, String todolist_type) {
//        this.todolist_id = todolist_id;
//        this.name = name;
//        this.created_at = created_at;
//        this.updated_at = updated_at;
//        this.tasks = tasks;
//        this.number_of_tasks = number_of_tasks;
//        this.tasks_completed = tasks_completed;
//        this.todolist_type = todolist_type;
//    }
//
//    public todoList(String name, LocalDate created_at, LocalDate updated_at, List<Task> tasks, int tasks_completed, String todolist_type) {
//        this.name = name;
//        this.created_at = created_at;
//        this.updated_at = updated_at;
//        this.tasks = tasks;
//        this.number_of_tasks = tasks.size();
//        this.tasks_completed = tasks_completed;
//        this.todolist_type = todolist_type;
//    }
//
//    public int getTodolist_id() {
//        return todolist_id;
//    }
//
//    public void setTodolist_id(int todolist_id) {
//        this.todolist_id = todolist_id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public LocalDate getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(LocalDate created_at) {
//        this.created_at = created_at;
//    }
//
//    public LocalDate getUpdated_at() {
//        return updated_at;
//    }
//
//    public void setUpdated_at(LocalDate updated_at) {
//        this.updated_at = updated_at;
//    }
//
//    public List<Task> getTasks() {
//        return tasks;
//    }
//;
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//        this.number_of_tasks = tasks.size();
//    }
//
//    public int getNumber_of_tasks() {
//        return number_of_tasks;
//    }
//
//    public void setNumber_of_tasks(int number_of_tasks) {
//        this.number_of_tasks = number_of_tasks;
//    }
//
//    public int getTasks_completed() {
//        return tasks_completed;
//    }
//
//    public void setTasks_completed(int tasks_completed) {
//        this.tasks_completed = tasks_completed;
//    }
//
//    public String getTodolist_type() {
//        return todolist_type;
//    }
//
//    public void setTodolist_type(String todolist_type) {
//        this.todolist_type = todolist_type;
//    }
//
//    @Override
//    public String toString() {
//        return "todoList{" +
//                "todolist_id=" + todolist_id +
//                ", name='" + name + '\'' +
//                ", todolist_type='" + todolist_type + '\'' +
//                '}';
//    }
//}


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
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

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private LocalDate updated_at;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Task> tasks;

    private int tasks_completed;
    private String todolist_type;

    private boolean isDeleted;

    public todoList() {
    }

    public todoList(String name, LocalDate created_at, LocalDate updated_at, List<Task> tasks, int tasks_completed, String todolist_type) {
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.tasks = tasks;
        this.tasks_completed = tasks_completed;
        this.todolist_type = todolist_type;
        this.isDeleted = false;
    }

    public Integer getTodolist_id() {
        return todolist_id;
    }

    public void setTodolist_id(Integer todolist_id) {
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @JsonProperty("number_of_tasks") // Ensures this method appears in JSON response
    public int getNumber_of_tasks() {
        return tasks != null ? tasks.size() : 0;
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
                ", todolist_type='" + todolist_type + '\'' +
                ", number_of_tasks=" + getNumber_of_tasks() +
                '}';
    }
}

