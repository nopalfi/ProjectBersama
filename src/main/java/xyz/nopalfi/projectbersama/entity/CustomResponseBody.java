package xyz.nopalfi.projectbersama.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponseBody<T> {
    private UUID uuid = UUID.randomUUID();
    private String message;
    private T data;
    private long timestamp;
}
