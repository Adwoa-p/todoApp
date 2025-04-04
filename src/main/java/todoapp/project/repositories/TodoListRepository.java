package todoapp.project.todolist;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//This repo is responsible for data access
@Repository
public interface TodoListRepository extends JpaRepository<todoList, Integer> {

//    This will transform to a sql such as select * from todoList where email = ...
//    @Query
    Optional<todoList> findByName(String name);
}
