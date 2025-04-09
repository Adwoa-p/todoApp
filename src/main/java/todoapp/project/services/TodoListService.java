package todoapp.project.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todoapp.project.enums.TodoListType;
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

    public Page<TodoList> todoLists(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return todoListRepository.findByIsDeletedFalse(pageable);
    }

    public TodoList addList(TodoList todoList){
        Optional<TodoList> toDoOptional = todoListRepository.findByName(todoList.getName());
        if (toDoOptional.isPresent()){
            throw new IllegalStateException("Name already exists");
        }
        todoListRepository.save(todoList);
//        System.out.println(todoList);
        return todoList;
    }

    public void deleteList(Integer id) {
        Optional<TodoList> listOptional = todoListRepository.findById(id);
        if (listOptional.isEmpty()) {
            throw new IllegalStateException("Task not found");
        }
        TodoList list = listOptional.get();
        list.setDeleted(true);
        todoListRepository.save(list);
        System.out.println("Deleted task: " + list.getName());
    }

    @Transactional
    public void updateList(Integer id, String name){
        TodoList todoList = todoListRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such id exists"));

        if (name!= null && name.length() > 0 && !Objects.equals(todoList.getName(), name)){
            Optional<TodoList> listOptional = todoListRepository.findByName(name);
            if (listOptional.isPresent()){
                throw new IllegalStateException("Name already exists");
            }
            todoList.setName(name);
            todoListRepository.save(todoList);
        }
    }

    public TodoList getListById(Integer id) {
        Optional<TodoList> list = todoListRepository.findById(id);
        if (list.isEmpty()) {
            throw new IllegalStateException("No such list id " + id + " exists");
        }
        TodoList retrievedList = todoListRepository.findByTodoListIdAndIsDeletedFalse(id);
        return retrievedList;
    }


        public void updateListType(Integer id, TodoListType todolist_type) {
            TodoList todoList = todoListRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("No such id exists"));

            todoList.setTodolist_type(todolist_type);
            todoListRepository.save(todoList);
    }
}
