package xyz.nopalfi.projectbersama.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nopalfi.projectbersama.entity.Project;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> getByUuid(UUID uuid);
}
