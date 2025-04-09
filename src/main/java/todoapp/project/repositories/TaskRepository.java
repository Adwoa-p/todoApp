package todoapp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todoapp.project.models.dtos.TaskDto;
import todoapp.project.models.entities.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTitle(String title);

//    Optional<Task> findByDescriptionA(String description);

//    List<Task> findByTodoListId(Integer listId);

//    Optional<TaskDto> findAllByTodoListId(Integer integer);

    Task findByTaskIdAndIsDeletedFalse(Integer id);


    List<Task> findByIsDeletedFalse();
}
