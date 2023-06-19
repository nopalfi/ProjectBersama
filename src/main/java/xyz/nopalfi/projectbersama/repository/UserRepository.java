package xyz.nopalfi.projectbersama.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import xyz.nopalfi.projectbersama.entity.User;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> getByUsername(String username);
    Optional<User> getByEmail(String username);
    Optional<User> getByUuid(UUID uuid);
}
