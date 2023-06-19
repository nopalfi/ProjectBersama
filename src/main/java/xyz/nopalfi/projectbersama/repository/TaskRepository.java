package xyz.nopalfi.projectbersama.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUser(User user);
    Optional<Task> findByUuid(UUID uuid);
}
