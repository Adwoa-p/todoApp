package todoapp.project.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todoapp.project.dtos.TodoListDto;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path="api/todolist")
public class TodoListController {

    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public Page<todoList> getList(@RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                  @RequestParam (value="pageSize", defaultValue = "10", required = false) int pageSize){
        return todoListService.todoLists(pageNo, pageSize);
    }

    @PostMapping
    public void addNewList(@RequestBody todoList todoList){
        todoListService.addList(todoList);
    }

    @DeleteMapping(path = "{id}")
    public void deleteList(@PathVariable("id") Integer id){
        todoListService.deleteList(id);
    }

    @PutMapping(path = "{id}")
    public void updateList(@PathVariable("id") Integer id,@RequestParam String name, @RequestParam String todolist_type){
        todoListService.updateList(id,name,todolist_type);
    }

    @GetMapping(path = "{id}")
    public todoList getListById(@PathVariable("id") Integer id) {
        return todoListService.getListById(id);
    }

    @PutMapping(path = "type/{id}")
    public void updateListType(@PathVariable("id") Integer id, @RequestParam String todolist_type) {
        todoListService.updateListType(id, todolist_type);
        System.out.println("Type updated");
    }
}
