package xyz.nopalfi.projectbersama.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class CustomResponseBody<T> {
    private UUID uuid = UUID.randomUUID();
    private String message;
    private T data;
    private long timestamp;
    private String path;
    private String apiVersion;
}
