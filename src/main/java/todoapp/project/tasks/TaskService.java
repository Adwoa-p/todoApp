package todoapp.project.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapp.project.tasks.enums.Priority;
import todoapp.project.tasks.enums.Status;
import todoapp.project.todolist.todoList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() { return taskRepository.findAll();}

    public Task getTask(Integer id) {
       Task retrievedTask = taskRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Task not found"));
       return retrievedTask;
    }

//    public List<Task> getAllTasksInList(Integer listId) {
//        List<Task> retrievedTasks = taskRepository.findByTodoListId(listId);
//        return retrievedTasks;
//    }

    public void addTasks(Task task) {
        Optional<Task> taskOptional = taskRepository.findByTitle(task.getTitle());
        if (taskOptional.isPresent()){
            throw new IllegalStateException("Task already exists");
        }
        taskRepository.save(task);
    }

    @Transactional
    public void updateTask(Integer id, String title, String description) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such task exists"));

        if (title!= null && !title.isEmpty() && !Objects.equals(task.getTitle(), title)){
            Optional<Task> taskOptional = taskRepository.findByTitle(title);
            if (taskOptional.isPresent()){
                throw new IllegalStateException("Task already exists");
            }
            task.setTitle(title);
            taskRepository.save(task);
        }

        if (title!= null && !title.isEmpty() && !Objects.equals(task.getDescription(), description)){
            Optional<Task> taskOptional = taskRepository.findByDescription(description);
            if (taskOptional.isPresent()){
                throw new IllegalStateException("Task Description already exists");
            }
            task.setDescription(description);
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
