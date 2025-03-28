package todoapp.project.todolist;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;

;    @Autowired
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public List<todoList> todoLists() {
        return todoListRepository.findAll();
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

    public void deleteList(Integer id){
//        boolean list = todoListRepository.existsById(id);
//        if (!list){
//            throw new IllegalStateException("No such id " + id + " exists");
//        }

        todoListRepository.deleteById(id);
        System.out.println("List successfully deleted");
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

            if (todolist_type!=null && todolist_type.length() > 0 && !Objects.equals(todoList.getTodolist_type(), todolist_type)){
                todoList.setTodolist_type(todolist_type);
                todoListRepository.save(todoList);
            }
    }
}
