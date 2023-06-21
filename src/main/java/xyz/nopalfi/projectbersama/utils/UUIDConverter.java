package xyz.nopalfi.projectbersama.utils;

import org.springframework.http.HttpStatus;
import xyz.nopalfi.projectbersama.errorhandler.InvalidUUIDException;

import java.util.UUID;

public class UUIDConverter {
    /**
     * Converting uuid in String type to UUID and handling the conversion into custom exception type
     * @param uuid the UUID in string format
     * @return UUID Object
     */
    public static UUID convert(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException(uuid+" is an invalid UUID", HttpStatus.BAD_REQUEST);
        }
    }
}
