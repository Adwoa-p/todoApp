package todoapp.project.todolist;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import todoapp.project.dtos.ResponseDto;
import todoapp.project.dtos.TodoListDto;
import todoapp.project.tasks.Task;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public Page<todoList> todoLists(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return todoListRepository.findAll(pageable);
    }

    public todoList addList(todoList todoList){
        Optional<todoList> toDoOptional = todoListRepository.findByName(todoList.getName());
        if (toDoOptional.isPresent()){
            throw new IllegalStateException("Name already exists");
        }
        todoListRepository.save(todoList);
//        System.out.println(todoList);
        return todoList;
    }

    public void deleteList(Integer id) {
        Optional<todoList> listOptional = todoListRepository.findById(id);
        if (listOptional.isEmpty()) {
            throw new IllegalStateException("Task not found");
        }
        todoList list = listOptional.get();
        list.setDeleted(true);
        todoListRepository.save(list);
        System.out.println("Deleted task: " + list.getName());
    }

    @Transactional
    public void updateList(Integer id, String name, String todolist_type){
        todoList todoList = todoListRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such id exists"));

        if (name!= null && name.length() > 0 && !Objects.equals(todoList.getName(), name)){
            Optional<todoList> listOptional = todoListRepository.findByName(name);
            if (listOptional.isPresent()){
                throw new IllegalStateException("Name already exists");
            }
            todoList.setName(name);
            todoListRepository.save(todoList);
        }

        if (todolist_type!=null && todolist_type.length() > 0 && !Objects.equals(todoList.getTodolist_type(), todolist_type)){
            todoList.setTodolist_type(todolist_type);
        }
    }

    public todoList getListById(Integer id) {
        Optional<todoList> list = todoListRepository.findById(id);
        if (list.isEmpty()) {
            throw new IllegalStateException("No such list id " + id + " exists");
        }
        return list.get();
    }


        public void updateListType(Integer id, String todolist_type) {
            todoList todoList = todoListRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such id exists"));

            if (todolist_type!=null && !todolist_type.isEmpty() && !Objects.equals(todoList.getTodolist_type(), todolist_type)){
                todoList.setTodolist_type(todolist_type);
                todoListRepository.save(todoList);
            }
    }
}
