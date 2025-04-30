package todoapp.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import todoapp.project.enums.Priority;
import todoapp.project.enums.Status;
import todoapp.project.models.dtos.TaskDto;
import todoapp.project.models.entities.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTitle(String title);

    Optional<Task> findByTaskIdAndIsDeletedFalse(Integer id);

    Page<Task> findByIsDeletedFalse(Pageable pageable);

    Page<Task> findByPriorityAndIsDeletedFalse(Priority priority, Pageable pageable);

    Page<Task> findByStatusAndIsDeletedFalse(Status status, Pageable pageable);

    Page<Task> findActiveTasksByTodoListId(@Param("todoListId") Integer todoListId, Pageable pageable);

//    List<Task> findAllByTodoListId(Integer listId);
}
