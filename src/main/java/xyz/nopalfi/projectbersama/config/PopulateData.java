package xyz.nopalfi.projectbersama.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.enums.Role;
import xyz.nopalfi.projectbersama.repository.ProjectRepository;
import xyz.nopalfi.projectbersama.repository.TaskRepository;
import xyz.nopalfi.projectbersama.repository.TeamRepository;
import xyz.nopalfi.projectbersama.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Component
public class PopulateData implements CommandLineRunner {

    private final UserRepository repository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public PopulateData(UserRepository repository, ProjectRepository projectRepository, TaskRepository taskRepository, TeamRepository teamRepository, PasswordEncoder passwordEncoder) {
        this.projectRepository = projectRepository;
        this.repository = repository;
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) {
        User user1 = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("pass1"))
                .email("email@email.com")
                .firstName("name1")
                .role(Role.USER)
                .build();
        User user2 = User.builder()
                .username("johndoe")
                .password(passwordEncoder.encode("johndoe"))
                .firstName("John")
                .secondName("Doe")
                .email("johndoe@example.com")
                .role(Role.USER)
                .build();

        repository.saveAll(List.of(user1, user2));

        Team team1 = Team.builder()
                .users(List.of(user1, user2))
                .teamOwner(user1)
                .build();

        teamRepository.save(team1);

        Instant now = Instant.now();
        Task task1 = Task.builder()
                .task("Belajar PHP")
                .isDone(false)
                .taskCreated(now)
                .taskModified(now)
                .owner(user1)
                .build();
        now = Instant.now();
        Task task2 = Task.builder()
                .task("Belajar Python")
                .isDone(false)
                .taskCreated(now)
                .taskModified(now)
                .owner(user1)
                .build();
        now = Instant.now();
        Task task3 = Task.builder()
                .task("Belajar Java")
                .isDone(false)
                .taskCreated(now)
                .taskModified(now)
                .owner(user1)
                .build();

        taskRepository.saveAll(List.of(task1, task2, task3));

        now = Instant.now();
        Project project1 = Project.builder()
                .title("New Project")
                .projectCreated(now)
                .projectModified(now)
                .deadline(now.plusSeconds(86400)) // 1 Day after
                .tasks(List.of(task1, task2, task3))
                .teams(Set.of(team1))
                .owner(user1)
                .build();

        projectRepository.save(project1);
    }
}
