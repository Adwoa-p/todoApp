package todoapp.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todoapp.project.enums.Priority;
import todoapp.project.enums.Status;
import todoapp.project.models.entities.Task;
import todoapp.project.services.TaskService;
import todoapp.project.models.dtos.TaskDto;

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

//    @GetMapping("/{listId}")
//    public List<Task> getTasksByListId(@PathVariable Integer listId) {
//        return taskService.getAllTasksInList(listId);
//    }

    @PostMapping
    public void addTask(@RequestBody TaskDto taskDto) {
        taskService.addTasks(taskDto);
    }

    @PutMapping(path = "{id}")
    public void updateTask(@PathVariable Integer id,@RequestBody String title) {
        taskService.updateTask(id, title);
    }

    @PutMapping("{id}/status")
    public void setStatus(@RequestParam Status status, @PathVariable Integer id) {
        taskService.setStatus(status, id);
    }

    @PutMapping("{id}/priority")
    public void setPriority(@RequestParam Priority priority, @PathVariable Integer id) {
        taskService.setPriority(priority, id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id, true);
    }
}
