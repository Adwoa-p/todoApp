package todoapp.project.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todoapp.project.tasks.enums.Status;

import java.util.List;

@RestController
@RequestMapping(path="api/task")
public class TaskController {

    private final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Integer id) {
        return taskService.getTask(id);
    }

    @PostMapping
    public void addTask(@RequestBody Task task) {
        taskService.addTasks(task);
    }

    @PutMapping(path = "{id}")
    public void updateTask(@PathVariable Integer id,@RequestBody String title, @RequestBody String description) {
        taskService.updateTask(id, title, description);
    }

    @PutMapping("{id}/status")
    public void setStatus(@RequestBody Status status, @PathVariable Integer id) {
        taskService.setStatus(status, id);
    }

    @PutMapping("{id}/priority")
    public void setPriority(@RequestBody Priority priority, @PathVariable Integer id) {
        taskService.setPriority(priority, id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id, true);
    }
}
