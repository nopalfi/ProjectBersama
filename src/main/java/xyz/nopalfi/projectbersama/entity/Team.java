package xyz.nopalfi.projectbersama.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @OneToMany
    private Set<User> user;
    @NonNull
    private UUID projectUuid;
    @NonNull
    @OneToOne
    private User projectOwner;
}
