package xyz.nopalfi.projectbersama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TeamDTO {
    private UUID uuid;
    private List<UserDTO> users;
    private UserDTO teamOwner;
}
