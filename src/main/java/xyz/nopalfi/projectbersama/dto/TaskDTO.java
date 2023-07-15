package xyz.nopalfi.projectbersama.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import xyz.nopalfi.projectbersama.entity.User;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskDTO {
    private UUID uuid;
    private String task;
    private Boolean isDone;
    private Instant taskCreated;
    private Instant taskModified;
    private Instant deadline;
    private UserDTO owner;
}
