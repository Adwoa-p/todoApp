package todoapp.project.todolist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import todoapp.project.models.entities.TodoList;
import todoapp.project.repositories.TodoListRepository;
import todoapp.project.services.TodoListService;

@ExtendWith(MockitoExtension.class)
class  TodoListServiceTest {
    @Mock
    TodoListRepository todoListRepository;

    @InjectMocks
    TodoListService todoListService;

//    @Test
//    void getListSuccessfully() {
//        System.out.println("First unit test");
//        todoListService.todoLists();
//    }

    @Test
    void addListSuccessfully() {

        TodoList list = new TodoList();
        list.setTodolist_id(3);
        list.setName("Works");
//        list.setTodolist_type("Work");
        list.setTasks_completed(3);
//        list.setNumber_of_tasks(4);

        Mockito.when(todoListRepository.save(list)).thenReturn(list);

        TodoList todolist = todoListService.addList(list);

//       makes sure that testProduct == matchProduct
        Assertions.assertNotNull(todolist);
        Assertions.assertEquals(list.getName(), todolist.getName());
        Assertions.assertEquals(3, (int) list.getTodolist_id());
    }
}