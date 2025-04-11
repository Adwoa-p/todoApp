package todoapp.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity <ResponseEntity<String>> addTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.addTasks(taskDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity <ResponseEntity<String>> updateTask(@PathVariable Integer id,@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(id, taskDto), HttpStatus.OK);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity <ResponseEntity<String>> setStatus(@RequestParam Status status, @PathVariable Integer id) {
        return new ResponseEntity<>(taskService.setStatus(status, id), HttpStatus.OK);
    }

    @PatchMapping("{id}/priority")
    public ResponseEntity <ResponseEntity<String>> setPriority(@RequestParam Priority priority, @PathVariable Integer id) {
        return new ResponseEntity<>(taskService.setPriority(priority, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity <ResponseEntity<String>> deleteTask(@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.deleteTask(id, true), HttpStatus.OK);
    }
}
