package todoapp.project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapp.project.enums.Priority;
import todoapp.project.enums.Status;
import todoapp.project.models.dtos.TaskDto;
import todoapp.project.models.entities.Task;
import todoapp.project.models.entities.TodoList;
import todoapp.project.repositories.TaskRepository;
import todoapp.project.repositories.TodoListRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TodoListRepository todoListRepository;

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findByIsDeletedFalse();
        for (Task task : tasks) {
            if (task.getStatus() != Status.COMPLETED && task.getDeadline().isBefore(LocalDateTime.now())) {
                task.setStatus(Status.OVERDUE);
                taskRepository.save(task);
            }
        }
        return tasks;
    }

    public Task getTask(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.getStatus() != Status.COMPLETED && task.getDeadline().isBefore(LocalDateTime.now())) {
            task.setStatus(Status.OVERDUE);
            taskRepository.save(task);
        }
        return task;
    }


//    public List<Task> getAllTasksInList(Integer listId) {
//        List<Task> retrievedTasks = taskRepository.findByTodoListId(listId);
//        return retrievedTasks;
//    }

    public void addTasks(TaskDto taskDto) {
        Optional<Task> taskOptional = taskRepository.findByTitle(taskDto.getTitle());
        if (taskOptional.isPresent()){
            throw new IllegalStateException("Task already exists");
        }
        Optional<TodoList> todoList = todoListRepository.findById(taskDto.getTodoListId());

        Task task = Task.builder()
                .deadline(taskDto.getDeadline())
                .priority(taskDto.getPriority())
                .description(taskDto.getDescription())
                .title(taskDto.getTitle())
                .todoList(todoList.get())
                .build();
        todoList.get().getTasks().add(task);
        Task savedTask = taskRepository.save(task);
        System.out.println(savedTask);
    }

    @Transactional
    public void updateTask(Integer id, String title) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such task exists"));

        if (title!= null && !title.isEmpty() && !Objects.equals(task.getTitle(), title)){
            Optional<Task> taskOptional = taskRepository.findByTitle(title);
            if (taskOptional.isPresent()){
                throw new IllegalStateException("Task already exists");
            }
            task.setTitle(title);
            taskRepository.save(task);
        }
    }


    public void setStatus(Status status, Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No such task exists"));
        task.setStatus(status);
        taskRepository.save(task);
    }

    public void setPriority(Priority priority, Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No such task exists"));
        task.setPriority(priority);
        taskRepository.save(task);
    }

    public void deleteTask(Integer id, boolean delete) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw new IllegalStateException("Task not found");
        }
        Task task = taskOptional.get();
        task.setDeleted(delete);
        taskRepository.save(task);
        System.out.println("Deleted task: " + task.getTitle());
    }

}
