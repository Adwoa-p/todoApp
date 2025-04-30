package todoapp.project.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todoapp.project.enums.TodoListType;
import todoapp.project.models.entities.TodoList;

//This repo is responsible for data access
@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Integer> {

//    This will transform to a sql such as select * from todoList where email = ...
//    @Query
    Optional<TodoList> findByName(String name);

    Page<TodoList> findByIsDeletedFalse(Pageable pageable);

    TodoList findByTodoListIdAndIsDeletedFalse(Integer id);

    Page<TodoList> findByTodoListTypeIsDeletedFalse(TodoListType todoListType, Pageable pageable);
}
