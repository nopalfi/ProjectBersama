package xyz.nopalfi.projectbersama;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectBersamaApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testBcryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String password = "1234password";
        System.out.println(encoder.encode(password));
    }

}
