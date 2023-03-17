package xyz.nopalfi.projectbersama.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.repository.UserRepository;

import java.util.List;

@Component
public class PopulateData implements CommandLineRunner {

    private final UserRepository repository;

    @Autowired
    public PopulateData(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .username("user1")
                .password("pass1")
                .email("email@email.com")
                .firstName("name1")
                .build();
        User user2 = User.builder()
                .username("johndoe")
                .password("password")
                .firstName("John")
                .secondName("Doe")
                .email("johndoe@example.com")
                .build();

        repository.saveAll(List.of(user1, user2));
    }
}
