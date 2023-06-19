package xyz.nopalfi.projectbersama.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nopalfi.projectbersama.entity.Team;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByUuid(UUID uuid);
}
