package xyz.nopalfi.projectbersama.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @OneToMany
    private List<Task> tasks;
    private String title;
    @Builder.Default
    private String description = "";
    private Instant projectCreated;
    private Instant projectModified;
    private Instant deadline;
    @Builder.Default
    private Boolean isDone = false;
    @OneToOne
    private User owner;

    @ManyToMany
    private Set<Team> teams;

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

}
