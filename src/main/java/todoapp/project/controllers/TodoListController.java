package todoapp.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todoapp.project.enums.TodoListType;
import todoapp.project.models.dtos.ResponseDto;
import todoapp.project.models.dtos.TodoListDto;
import todoapp.project.models.entities.TodoList;
import todoapp.project.services.TodoListService;

@RestController
@RequestMapping(path="api/todolist")
public class TodoListController {

    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public ResponseEntity<Page<TodoList>> getList(@RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                  @RequestParam (value="pageSize", defaultValue = "10", required = false) int pageSize,
                                                  @RequestParam (defaultValue = "name",  required = false) String sortBy,
                                                  @RequestParam (defaultValue = "true") boolean ascending,
                                                  @RequestParam (value = "todolistType", required = false) TodoListType todoListType)
    {
        return new ResponseEntity<>(todoListService.todoLists(pageNo, pageSize, sortBy, ascending, todoListType), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<TodoList>> addNewList(@RequestBody TodoList todoList){
        return new ResponseEntity<>(todoListService.addList(todoList), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ResponseDto<String>> deleteList(@PathVariable("id") Integer id){
        return new ResponseEntity<>(todoListService.deleteList(id), HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ResponseDto<String>> updateList(@PathVariable("id") Integer id, @RequestBody TodoListDto todoListDto){
        return new ResponseEntity<>(todoListService.updateList(id,todoListDto), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public TodoList getListById(@PathVariable("id") Integer id) {
        return todoListService.getListById(id);
    }

    @PatchMapping(path = "type/{id}")
    public ResponseEntity<ResponseDto<String>> updateListType(@PathVariable("id") Integer id, @RequestParam TodoListType todolist_type) {
        return new ResponseEntity<>(todoListService.updateListType(id, todolist_type), HttpStatus.OK);
    }
}
