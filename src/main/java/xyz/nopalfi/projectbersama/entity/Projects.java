package xyz.nopalfi.projectbersama.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @OneToMany
    private List<Tasks> tasks;
    @NonNull
    private String title;
    private String description;
    private Instant projectCreated;
    private Instant projectModified;
    private Instant deadline;

    @ManyToMany
    private Set<Team> teams;
}
