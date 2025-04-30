package todoapp.project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import todoapp.project.enums.Priority;
import todoapp.project.enums.Status;
import todoapp.project.models.dtos.ResponseDto;
import todoapp.project.models.dtos.TaskDto;
import todoapp.project.models.entities.Task;
import todoapp.project.models.entities.TodoList;
import todoapp.project.models.mappers.TaskDtoMapper;
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

    public Page<Task> getAllTasks(int pageNo, int pageSize, String sortBy, boolean ascending, Priority priority, Status status) {
        Sort sort = sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        if(Objects.nonNull(priority)) {
            return taskRepository.findByPriorityAndIsDeletedFalse(priority, pageable);
        }
        if(Objects.nonNull(status)){
            return taskRepository.findByStatusAndIsDeletedFalse(status, pageable);
        }
        Page<Task> tasks = taskRepository.findByIsDeletedFalse(pageable);
        for (Task task : tasks) {
            if (task.getStatus() != Status.COMPLETED && task.getDeadline().isBefore(LocalDateTime.now())) {
                task.setStatus(Status.OVERDUE);
                taskRepository.save(task);
            }
        }
        return tasks;
    }

    public ResponseDto<Task> getTask(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        if (task.getStatus() != Status.COMPLETED && task.getDeadline().isBefore(LocalDateTime.now())) {
            task.setStatus(Status.OVERDUE);
            taskRepository.save(task);
        }
        return ResponseDto.<Task>builder()
                .message(String.format("Task with id %d retrieved successfully", id))
                .response(task)
                .build();
    }

    public Page<Task> getTasksInList(Integer todoListId,int pageNo, int pageSize, String sortBy, boolean ascending){
        Sort sort  = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return taskRepository.findActiveTasksByTodoListId(todoListId, pageable);
    }

    public ResponseDto<Task> addTasks(TaskDto taskDto) {
        Optional<Task> taskOptional = taskRepository.findByTitle(taskDto.getTitle());
        if (taskOptional.isPresent() && !taskOptional.get().isDeleted()){
            throw new IllegalStateException("Task already exists");
        }
        Optional<TodoList> todoList = todoListRepository.findById(taskDto.getTodoListId());
        if(todoList.get().isDeleted()){
            throw new IllegalStateException("Todolist doesn't exist");
        }
        Task task = TaskDtoMapper.toTask(taskDto, todoList);
        taskRepository.save(task);
        return ResponseDto.<Task>builder()
                .message("Task with id " + task.getTaskId() + " added successfully to list " + todoList.get().getName())
                .response(task)
                .build();

    }

    @Transactional
    public ResponseDto<String> updateTask(Integer id, TaskDto taskDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such task exists"));
        if (task.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        if (taskDto.getTitle()!= null && !taskDto.getTitle().isEmpty() && !Objects.equals(task.getTitle(), taskDto.getTitle())){
            Optional<Task> taskOptional = taskRepository.findByTitle(taskDto.getTitle());
            if (taskOptional.isPresent()){
                throw new IllegalStateException("Task already exists");
            }
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription()!= null && !taskDto.getDescription().isEmpty() && !Objects.equals(task.getDescription(), taskDto.getDescription())){
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getDeadline()!= null && !Objects.equals(task.getDeadline(), taskDto.getDeadline())){
            task.setDeadline(taskDto.getDeadline());
        }
        taskRepository.save(task);
        return ResponseDto.<String>builder()
                .message("Task updated")
                .response("Task with id " + task.getTaskId() + " updated successfully")
                .build();
    }

    public ResponseDto<String> setStatus(Status status, Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No such task exists"));
        if (task.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        task.setStatus(status);
        if (status == Status.COMPLETED) {
            task.setCompletedDate(LocalDateTime.now());
        }
        taskRepository.save(task);
        return ResponseDto.<String>builder()
                .message("Task updated")
                .response("Task with id " + task.getTaskId() + " status set to " + status + " successfully")
                .build();
    }

    public ResponseDto<String> setPriority(Priority priority, Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No such task exists"));
        if (task.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        task.setPriority(priority);
        taskRepository.save(task);
        return ResponseDto.<String>builder()
                .message("Task updated")
                .response("Task with id " + task.getTaskId() + " priority set to " + priority + " successfully")
                .build();
    }

    public ResponseDto<String> deleteTask(Integer id, boolean delete) {
        Optional<Task> taskOptional = taskRepository.findByTaskIdAndIsDeletedFalse(id);
        if (taskOptional.isEmpty()) {
            throw new IllegalStateException("Task not found");
        }
        Task task = taskOptional.get();
        task.setDeleted(delete);
        taskRepository.save(task);
        return ResponseDto.<String>builder()
                .message("Task deleted")
                .message("Task with id " + task.getTaskId() + " deleted successfully")
                .build();
    }

}
