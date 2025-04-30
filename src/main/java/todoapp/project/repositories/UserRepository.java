package todoapp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import todoapp.project.models.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
