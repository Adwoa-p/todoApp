package todoapp.project.todolist;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoListServiceTest {

    @Mock
    TodoListRepository todoListRepository;

    @InjectMocks
    TodoListService todoListService;

    @BeforeAll
    public static void init(){
        System.out.println("Before All");
    }

    @BeforeEach
    public void init1(){
        System.out.println("Before each");
    }


    @Test
    void addListSuccessfully() {
        todoList list = new todoList();

        list.setTodolist_id(3);
        list.setName("Works");
        list.setTodolist_type("Work");
        list.setTasks_completed(3);
        list.setNumber_of_tasks(4);

        Mockito.when(todoListRepository.save(list)).thenReturn(list);

        todoList todolist = todoListService.addList(list);

//       makes sure that testProduct == matchProduct
        Assertions.assertNotNull(todolist);
        Assertions.assertEquals(list.getName(), todolist.getName());
        Assertions.assertTrue(list.getTodolist_id()==3);
    }


    @Test
    void deleteSuccessfully(){
        Mockito.doNothing().when(todoListRepository).deleteById(1);
        todoListService.deleteList(1);
        Mockito.verify(todoListRepository, Mockito.times(1)).deleteById(1);

    }

    @AfterEach
    public void cleanup(){
        System.out.println("After each");
    }

    @AfterAll
    public static void destroy(){
        System.out.println("AFter All");
    }


}