package xyz.nopalfi.projectbersama.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String task;
    @Builder.Default
    private Boolean isDone = false;
    private Instant taskCreated;
    private Instant taskModified;
    @ManyToOne
    private User user;
}
