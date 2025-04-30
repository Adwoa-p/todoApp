package todoapp.project.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import todoapp.project.enums.TodoListType;
import todoapp.project.models.dtos.ResponseDto;
import todoapp.project.models.dtos.TodoListDto;
import todoapp.project.models.entities.TodoList;
import todoapp.project.repositories.TodoListRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public Page<TodoList> todoLists(int pageNo, int pageSize, String sortBy, boolean ascending, TodoListType todoListType) {
        Sort sort = sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        if(Objects.nonNull(todoListType)) {
            return todoListRepository.findByTodoListTypeIsDeletedFalse(todoListType, pageable);
        }
        return todoListRepository.findByIsDeletedFalse(pageable);
    }

    public ResponseDto<TodoList> addList(TodoList todoList){
        Optional<TodoList> toDoOptional = todoListRepository.findByName(todoList.getName());
        if (toDoOptional.isPresent() && !toDoOptional.get().isDeleted()){
            throw new IllegalStateException("Name already exists");
        }
        todoListRepository.save(todoList);
        return ResponseDto.<TodoList>builder()
                .message("Todolist with id " + todoList.getTodoListId() + " added successfully")
                .response(todoList)
                .build();

    }

    public ResponseDto<String> deleteList(Integer id) {
        Optional<TodoList> listOptional = todoListRepository.findById(id);
        if (listOptional.isEmpty()) {
            throw new IllegalStateException("Task not found");
        }
        TodoList list = listOptional.get();
        if (list.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List with ID " + id + " not found");
        }
        list.setDeleted(true);
        todoListRepository.save(list);
        return ResponseDto.<String>builder()
                .message("TodoList deleted successfully")
                .response(String.format("TodoList with id %d deleted successfully", id))
                .build();
    }

    @Transactional
    public ResponseDto<String>  updateList(Integer id, TodoListDto todoListDto){
        TodoList todoList = todoListRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such id exists"));

        if (todoList.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List with ID " + id + " not found");
        }

        if (todoListDto.getName()!= null && !todoListDto.getName().isEmpty() && !Objects.equals(todoList.getName(), todoListDto.getName())){
            Optional<TodoList> listOptional = todoListRepository.findByName(todoListDto.getName());
            if (listOptional.isPresent()){
                throw new IllegalStateException("Name already exists");
            }
            todoList.setName(todoListDto.getName());
            todoListRepository.save(todoList);
        }
        return ResponseDto.<String>builder()
                .message("TodoList update")
                .response("Todolist with id " + todoList.getTodoListId() + " updated successfully")
                .build();
    }

    public TodoList getListById(Integer id) {
        Optional<TodoList> list = todoListRepository.findById(id);
        if (list.isEmpty()) {
            throw new IllegalStateException("No such list id " + id + " exists");
        }
        TodoList retrievedList = list.get();
        if (retrievedList.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List with ID " + id + " not found");
        }

        return retrievedList;
    }


        public ResponseDto<String>  updateListType(Integer id, TodoListType todolist_type) {
            TodoList todoList = todoListRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("No such list id exists"));
            if (todoList.isDeleted()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List with ID " + id + " not found");
            }
            todoList.setTodolist_type(todolist_type);
            todoListRepository.save(todoList);
            return ResponseDto.<String>builder()
                    .message("TodoListType updated successfully")
                    .response("Todolist with id " + todoList.getTodoListId() + " todolist type updated successfully")
                    .build();
        }
}
