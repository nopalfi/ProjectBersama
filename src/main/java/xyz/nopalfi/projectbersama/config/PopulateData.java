package xyz.nopalfi.projectbersama.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Role;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.repository.ProjectRepository;
import xyz.nopalfi.projectbersama.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Component
public class PopulateData implements CommandLineRunner {

    private final UserRepository repository;
    private final ProjectRepository projectRepository;

    public PopulateData(UserRepository repository, ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.repository = repository;
    }
    @Override
    public void run(String... args) {
        User user1 = User.builder()
                .username("user1")
                .password("pass1")
                .email("email@email.com")
                .firstName("name1")
                .role(Role.USER)
                .build();
        User user2 = User.builder()
                .username("johndoe")
                .password("password")
                .firstName("John")
                .secondName("Doe")
                .email("johndoe@example.com")
                .role(Role.USER)
                .build();

        repository.saveAll(List.of(user1, user2));

        Instant now = Instant.now();
        Project project1 = Project.builder()
                .title("New Project")
                .projectCreated(now)
                .projectModified(now)
                .deadline(now.plusSeconds(86400)) // 1 Day after
                .build();

        projectRepository.save(project1);
    }
}
