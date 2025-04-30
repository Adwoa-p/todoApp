package todoapp.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todoapp.project.enums.Priority;
import todoapp.project.enums.Status;
import todoapp.project.enums.TodoListType;
import todoapp.project.models.dtos.ResponseDto;
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
    public ResponseEntity<Page<Task>> getTasks(@RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                               @RequestParam (value="pageSize", defaultValue = "10", required = false) int pageSize,
                                               @RequestParam (defaultValue = "name",  required = false) String sortBy,
                                               @RequestParam (defaultValue = "true") boolean ascending,
                                               @RequestParam (value = "priority", required = false) Priority priority,
                                               @RequestParam (value = "status", required = false) Status status)
     {
        return new ResponseEntity<>(taskService.getAllTasks(pageNo, pageSize, sortBy, ascending, priority, status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Task>> getTask(@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    @GetMapping("/{todoListId}")
    public ResponseEntity<Page<Task>> getTasksInList(@RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                     @RequestParam (value="pageSize", defaultValue = "10", required = false) int pageSize,
                                                     @RequestParam (defaultValue = "name",  required = false) String sortBy,
                                                     @RequestParam (defaultValue = "true") boolean ascending,
                                                     @PathVariable Integer todoListId){
        return new ResponseEntity<>(taskService.getTasksInList(todoListId, pageNo, pageSize, sortBy, ascending), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Task>> addTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.addTasks(taskDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ResponseDto<String>> updateTask(@PathVariable Integer id,@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(id, taskDto), HttpStatus.OK);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity <ResponseDto<String>> setStatus(@RequestParam Status status, @PathVariable Integer id) {
        return new ResponseEntity<>(taskService.setStatus(status, id), HttpStatus.OK);
    }

    @PatchMapping("{id}/priority")
    public ResponseEntity<ResponseDto<String>> setPriority(@RequestParam Priority priority, @PathVariable Integer id) {
        return new ResponseEntity<>(taskService.setPriority(priority, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity <ResponseDto<String>> deleteTask(@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.deleteTask(id, true), HttpStatus.OK);
    }
}
